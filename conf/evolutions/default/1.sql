# --- !Ups

CREATE TABLE `group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_group` (`name`)
);

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_user` (`email`),
  KEY `IX_user_1` (`group_id`),
  CONSTRAINT `FK_user_TO_group` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

# --- !Downs

DROP TABLE `user`;

DROP TABLE `group`;