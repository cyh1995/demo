create table USER
(
	ID INT auto_increment,
	ACCESS_ID VARCHAR,
	NAME VARCHAR,
	TOKEN CHAR(36),
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	constraint USER_PK
		primary key (ID)
);

create unique index USER_ID_UINDEX
	on USER (ID);

