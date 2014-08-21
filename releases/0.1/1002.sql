CREATE TABLE Account (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  createDate datetime NOT NULL,
  updateDate datetime NOT NULL,
  nombre varchar(100) DEFAULT NULL,
  PRIMARY KEY (id)
);


