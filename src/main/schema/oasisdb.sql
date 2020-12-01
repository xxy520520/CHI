DROP DATABASE IF EXISTS oasisdb;
CREATE DATABASE oasisdb DEFAULT CHARACTER SET utf8;
USE oasisdb;

##创建用户表
CREATE TABLE t_user (
user_id   INT AUTO_INCREMENT PRIMARY KEY,
user_name VARCHAR(16),
password  VARCHAR(16)
)ENGINE=InnoDB;

##创建论文表
CREATE TABLE paper (
paper_id   INT AUTO_INCREMENT PRIMARY KEY,
paper_name VARCHAR(200),
authors VARCHAR(1000),
affiliations VARCHAR(2000),
publication_title VARCHAR(500),
publish_year VARCHAR(5),
start_page INT,
end_page INT,
abstra VARCHAR(5000),
volume VARCHAR(3),
doi VARCHAR(60),
link VARCHAR(100),
keywords VARCHAR(2000),
ieee_terms VARCHAR(800),
controlled_terms VARCHAR(800),
non_controlled_terms VARCHAR(800),
mesh_terms VARCHAR(200),
citation INT,
reference INT,
publisher VARCHAR(15),
identifier VARCHAR(20)
)ENGINE=InnoDB;

##创建作者表
CREATE TABLE author (
author_id   INT AUTO_INCREMENT PRIMARY KEY,
author_name VARCHAR(60),
author_liveness DOUBLE DEFAULT 0,
gIndex INT DEFAULT 0
)ENGINE=InnoDB;

##创建机构表
CREATE TABLE affiliation (
affiliation_id   INT AUTO_INCREMENT PRIMARY KEY,
affiliation_name VARCHAR(400),
affiliation_liveness DOUBLE DEFAULT 0,
gIndex INT DEFAULT 0
)ENGINE=InnoDB;

##创建研究方向表
CREATE TABLE field (
field_id INT AUTO_INCREMENT PRIMARY KEY,
field_name VARCHAR(200),
field_liveness DOUBLE DEFAULT 0
)ENGINE=InnoDB;

##创建研究关系表
CREATE TABLE field_relation (
field_id INT,
paper_id INT
)ENGINE=InnoDB;

##创建发表关系表
CREATE TABLE publish (
paper_id INT,
author_id INT,
affiliation_id INT
)ENGINE=InnoDB;

##插入初始化数据
INSERT INTO t_user (user_name,password)
VALUES('admin','123456');
COMMIT;