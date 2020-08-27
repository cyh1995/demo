create table question
(
	id int auto_increment,
	title varchar,
	description TEXT,
	gmt_create bigint,
	gmt_modified int,
	creator int,
	comment_count int default 0,
	view_count int default 0,
	like_count int default 0,
	tag varchar,
	constraint QUESTION_PK
		primary key (id)
);

