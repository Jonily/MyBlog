drop database if exists blog;
create database blog;

use blog;

drop table if exists user;
create table user(
   userId int primary key auto_increment,
   userName varchar (50) unique ,
   password varchar (50)
);

drop table if exists article;

 create table article(
   articleId int primary key auto_increment,
   articleTitle varchar (255),
   articleContent text,
   userId int,
   foreign key(userId) references user(userId)
);