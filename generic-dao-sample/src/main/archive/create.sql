create database cap;
create database darwin0;
create database darwin1;

use darwin0;
create table plan0(
  status int(10) not null comment '',
  modtime timestamp not null comment '',
  adduser int(10) not null comment '',
  userid int(10) not null comment '',
  id int(10) not null comment '',
  addtime timestamp not null comment '',
  name varchar(32) not null comment '',
  moduser int(10) not null comment '',
  primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '';
create table plan1(
  status int(10) not null comment '',
  modtime timestamp not null comment '',
  adduser int(10) not null comment '',
  userid int(10) not null comment '',
  id int(10) not null comment '',
  addtime timestamp not null comment '',
  name varchar(32) not null comment '',
  moduser int(10) not null comment '',
  primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '';

use darwin1;
create table plan0(
  status int(10) not null comment '',
  modtime timestamp not null comment '',
  adduser int(10) not null comment '',
  userid int(10) not null comment '',
  id int(10) not null comment '',
  addtime timestamp not null comment '',
  name varchar(32) not null comment '',
  moduser int(10) not null comment '',
  primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '';
create table plan1(
  status int(10) not null comment '',
  modtime timestamp not null comment '',
  adduser int(10) not null comment '',
  userid int(10) not null comment '',
  id int(10) not null comment '',
  addtime timestamp not null comment '',
  name varchar(32) not null comment '',
  moduser int(10) not null comment '',
  primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '';
use cap;
create table user(
  id int(10) not null comment '' auto_increment,
  name varchar(32) not null comment '',
  primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '';