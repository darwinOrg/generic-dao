set names utf8;
CREATE DATABASE IF NOT EXISTS sequence DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
use sequence;

create table if not exists sequence(
	id bigint(20) not null auto_increment comment '唯一主键，无业务意义',
	seq_type varchar(64) not null comment '一个sequence的名字',
	cur_value bigint(20) not null comment '当前的值',
	step int(10) not null default 1 comment '该sequence的增长的step',
	last_modified timestamp comment '该sequence的更新时间',
	primary key(id),
	unique key unique_type(seq_type),
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment 'sequence';