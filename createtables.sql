create database if not exists RestroomFinder;
use RestroomFinder;

DROP TABLE IF EXISTS Users;
CREATE TABLE Users (
  id bigint(50) NOT NULL auto_increment,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS User_Prefer;
CREATE TABLE User_Prefer (
  id bigint(50) NOT NULL,
  clean smallint(2) default 0,
  busy smallint(2) default 0,
  accessTlt smallint(2) default 0,
  paper smallint(2) default 0,
  soap smallint(2) default 0,
  genInclus smallint(2) default 0,
  PRIMARY KEY  (id),
  FOREIGN KEY (id) REFERENCES Users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;