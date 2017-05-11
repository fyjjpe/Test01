create table post(
	post_id int not null auto_increment,
	post_content varchar(200),
	post_time date,
	person_id int,
	primary key(post_id)
	);

create table comment(
	comment_id int not null auto_increment,
	comment_content varchar(200),
	post_id int,
	primary key(comment_id)
	);
	
insert into post(post_content,person_id) values ('Tian qi bu cuo£¡',1);

insert into comment(comment_content,post_id) values ('quan kao chui£¡',1);

insert into comment(comment_content,post_id) values ('da mei nv£¡',1);

select * from post;
select * from comment;
drop table post;
drop table comment;

select
			post_id as id,
			post_content as content,
			post_time as time,
			person_id as personId
		from 
			post
		where
			post_id=1;


create table person(
	person_id int not null auto_increment,
	person_name varchar(10),
	person_age int,
	primary key (person_id)
);
insert into person (person_id,person_name,person_age) values (null,'tom',4);
insert into person (person_name,person_age) values ('jerry',4);

select * from person;



















