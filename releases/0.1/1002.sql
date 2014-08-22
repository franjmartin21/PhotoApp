drop table if exists Authority;
drop table if exists Account;

create table Account(
	id bigint(20) not null auto_increment,
	username varchar(20) not null,
	password varchar(32) not null,
	email varchar(100) not null,
	enabled tinyint not null,
	createDate datetime not null,
	updateDate datetime not null,
	primary key (id)
);


CREATE TABLE Authority (
  id bigint(20) not null auto_increment,
  account_id bigint(20) not null,
  authority varchar(20) not null,
  createDate datetime not null,
  updateDate datetime not null,
  primary key (id),
  FOREIGN KEY (account_id) REFERENCES Account(id)
);

insert into Account values(1,'user1', '1111', 'user1@user1.com', 1, now(),now());
insert into Account values(2,'user2', '2222', 'user2@user2.com', 1, now(),now());
insert into Account values(3,'admin1', 'admin1', 'admin1@admin1.com', 1, now(),now());

insert Authority values(1, 1, 'ROLE_USER', now(), now());
insert Authority values(2, 2, 'ROLE_USER', now(), now());
insert Authority values(3, 3, 'ROLE_USER', now(), now());
insert Authority values(4, 3, 'ROLE_ADMIN', now(), now());