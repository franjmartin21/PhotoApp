package com.photoapp.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.NumberUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class DBMigrator {
    private static final Pattern MIGRATE_SCRIPT_PATTERN = Pattern.compile("[0-9]+\\.sql");
    private static final Logger log = Logger.getLogger(DBMigrator.class.getName());
    private static final String GET_CURRENT_VERSION_SQL = "select version from SchemaVersion";

    private DataSource dataSource;
    private String migrateScriptDir;

    public DBMigrator(String userName, String password, String host, String dbName, int port, String migrateScriptDir) {
        MysqlDataSource dataSource = new MysqlDataSource();

        dataSource.setUser(userName);
        dataSource.setPassword(password);
        dataSource.setServerName(host);
        dataSource.setPort(port);
        dataSource.setDatabaseName(dbName);

        this.dataSource = dataSource;
        this.migrateScriptDir = migrateScriptDir;
    }

    public DBMigrator(DataSource dataSource, String migrateScriptDir) {
        this.dataSource = dataSource;
        this.migrateScriptDir = migrateScriptDir;
    }

    public void migrate() throws IOException, SQLException {
        String currentVersionStr = getCurrentDbVersion();
        int currentVersion = !NumberUtils.isDigits(currentVersionStr) ? 0 : NumberUtils.createInteger(currentVersionStr);

        log.info("Current DB version: " + currentVersion);
        log.info("Searching dir \"" + migrateScriptDir + "\" for migrate scripts.");

        List<ScriptHolder> scriptHolders = findScriptContentsToRun(currentVersion, null);
        ScriptHolder currentHolder = null;
        Connection conn = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            for (ScriptHolder holder : scriptHolders) {
                currentHolder = holder;
                runScript(holder, conn);
            }

            log.info("Final commit...");

            conn.commit();
        }
        catch (SQLException ex) {
            try {
                conn.rollback();
            }
            catch (SQLException e) {
                log.log(Level.SEVERE, "", e);
            }

            throw new SQLException("Failed to execute script " + currentHolder.scriptVersion + ".sql", ex);
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                }
                catch (SQLException e) {
                    log.log(Level.SEVERE, "", e);
                }
            }
        }
    }

    public static void main(String[] args) {
        DBMigrator app = new DBMigrator("photoapp", "photoapp", "localhost", "photoapp", 3306, "./releases/");
        try {
            app.migrate();
        } catch (Exception e) {
            log.log(Level.SEVERE, "DB Migrator error.", e);
        }
    }

    private void runScript(ScriptHolder holder, Connection conn) throws SQLException {
        log.info("Running script " + holder.scriptVersion + ".sql");

        List<String> extractedSql = new SqlStatementParser(holder.scriptContents).extractStatements();

        for (String line : extractedSql)
            executeMigrateSql(line, conn);
    }

    private void executeMigrateSql(String sql, Connection conn) throws SQLException {
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
        }
        catch (SQLException ex) {
            throw new SQLException("Failed to execute SQL: " + sql, ex);
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException e) {
                    log.log(Level.SEVERE, "Failed to execture SQL: " + sql, e);
                }
            }
        }
    }

    private List<ScriptHolder> findScriptContentsToRun(int currentVersion, File dirToSearch) throws IOException {
        List<ScriptHolder> ret = new ArrayList<ScriptHolder>();
        dirToSearch = dirToSearch == null ? new File(migrateScriptDir) : dirToSearch;
        File[] files = dirToSearch.listFiles(new FileFilter(){
            @Override
            public boolean accept(File file) {
                return file.isDirectory() || MIGRATE_SCRIPT_PATTERN.matcher(file.getName()).matches();
            }}
        );

        for (File f : files) {
            if (f.isDirectory())
                ret.addAll(findScriptContentsToRun(currentVersion, f));
            else {
                int scriptVersion = NumberUtils.createInteger(f.getName().substring(0, f.getName().indexOf(".sql")));

                if (scriptVersion > currentVersion)
                    ret.add(new ScriptHolder(FileUtils.readFileToString(f), scriptVersion));
            }
        }

        Collections.sort(ret, new Comparator<ScriptHolder>(){
            public int compare(ScriptHolder o1, ScriptHolder o2) {
                return o1.scriptVersion - o2.scriptVersion;
            }}
        );

        return ret;
    }

    private String getCurrentDbVersion() throws SQLException {
        return executeSql(GET_CURRENT_VERSION_SQL);
    }

    private String executeSql(String sql) throws SQLException {
        Connection conn = null;

        try {
            conn = getConnection();

            ResultSet rs = conn.prepareCall(sql).executeQuery();

            if (rs.next())
                return rs.getObject(1).toString();
        }
        catch (Exception ex) {
            throw new SQLException("Failed to execture SQL: " + sql, ex);
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                }
                catch (SQLException e) {
                    log.log(Level.SEVERE, "Failed to execture SQL: " + sql, e);
                }
            }
        }

        return null;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private static class ScriptHolder {
        private String scriptContents;
        private int scriptVersion;

        public ScriptHolder(String scriptContents, int scriptVersion) {
            super();
            this.scriptContents = scriptContents;
            this.scriptVersion = scriptVersion;
        }
    }
}
