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

DROP TABLE IF EXISTS Restrooms;
CREATE TABLE Restrooms (
  id varchar(255) NOT NULL ,
  clean double NOT NULL default 0,
  busy double NOT NULL default 0,
  rating double NOT NULL default 0,
  userTotalRatings int not NULL default 0,
  accessTlt bool NOT NULL default false,
  paper bool NOT NULL default false,
  soap bool NOT NULL default false,
  genInclus bool NOT NULL default false,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;