package com.photoapp.util;

import java.util.ArrayList;
import java.util.List;

public class SqlStatementParser {
    private static final char STATEMENT_TERMINATOR = ';';
    private static final char SINGLE_QUOTE = '\'';
	private static final char ESCAPE_CHAR = '\\';

	private final String unparsedContents;
	
	public SqlStatementParser(String unparsed) {
		this.unparsedContents = unparsed;
	}
	
	public List<String> extractStatements() {
		List<String> statements = new ArrayList<String>();
		
		int parsedIndex = -1;
		boolean inQuotes = false;
        for (int i = 0; i != unparsedContents.length(); ++i)
        {
        	char current = unparsedContents.charAt(i);
        	if (current == SINGLE_QUOTE) {
        		char previous = unparsedContents.charAt(i-1);
        		if (previous != ESCAPE_CHAR) {
        			inQuotes = !inQuotes;
        		}
        	}
        	else if (current == STATEMENT_TERMINATOR) {
        		if (!inQuotes) {
        			String sql = unparsedContents.substring(parsedIndex + 1, i);
        			statements.add(sql.trim());
        			parsedIndex = i;
        		}
        	}
        }
		
		return statements;
	}
}
