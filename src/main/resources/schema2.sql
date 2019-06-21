
drop schema IF EXISTS pocketfb;
CREATE SCHEMA IF NOT EXISTS `pocketfb` DEFAULT CHARACTER SET utf8;
USE `pocketfb`;
CREATE TABLE IF NOT EXISTS  `users` (
  `name` varchar(128) NOT NULL,
  `userid` varchar(64) NOT NULL,
  `email` varchar(45) NOT NULL,
  `pswd` varchar(256) NOT NULL,
  `dob` int(128) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`,`userid`),
  UNIQUE KEY `userid_UNIQUE` (`userid`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 0 requested 1 accepted 2 rejected


CREATE TABLE IF NOT EXISTS `friends` (
  `from_user` int(11) NOT NULL,
  `to_user` int(11) NOT NULL,
  `accepted` tinyint(3) NOT NULL DEFAULT '0',
  `action` tinyint(3) unsigned NOT NULL,
  PRIMARY KEY (`from_user`,`to_user`),
  CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`from_user`) REFERENCES `users` (`id`),
  CONSTRAINT `friends_ibfk_2` FOREIGN KEY (`to_user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;