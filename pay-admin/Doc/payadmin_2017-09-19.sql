# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.19)
# Database: payadmin
# Generation Time: 2017-09-19 07:58:48 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table Group
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Group`;

CREATE TABLE `Group` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户组ID',
  `name` varchar(16) NOT NULL DEFAULT '' COMMENT '用户组名',
  `details` varchar(64) DEFAULT '' COMMENT '用户组详情',
  `parentId` int(11) DEFAULT NULL COMMENT '父用户组ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Group` WRITE;
/*!40000 ALTER TABLE `Group` DISABLE KEYS */;

INSERT INTO `Group` (`id`, `name`, `details`, `parentId`)
VALUES
	(0,'root','根组织',-1),
	(1,'sand','杉德集团',0),
	(2,'sandjk','杉德金卡公司',1),
	(3,'jkxh','金卡迅汇公司',2),
	(4,'xhxtrjb','迅汇系统软件部',3);

/*!40000 ALTER TABLE `Group` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Permission`;

CREATE TABLE `Permission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `name` varchar(16) DEFAULT NULL COMMENT '权限名',
  `details` varchar(64) DEFAULT NULL COMMENT '权限描述',
  `parentId` int(11) DEFAULT NULL COMMENT '父权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Permission` WRITE;
/*!40000 ALTER TABLE `Permission` DISABLE KEYS */;

INSERT INTO `Permission` (`id`, `name`, `details`, `parentId`)
VALUES
	(0,'root','根目录',-1);

/*!40000 ALTER TABLE `Permission` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table PermissionRole
# ------------------------------------------------------------

DROP TABLE IF EXISTS `PermissionRole`;

CREATE TABLE `PermissionRole` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `permissionId` int(11) DEFAULT NULL COMMENT '权限ID',
  `roleId` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table Role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Role`;

CREATE TABLE `Role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT ' 角色ID',
  `name` varchar(16) DEFAULT NULL COMMENT '角色名',
  `details` varchar(16) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table RoleGroup
# ------------------------------------------------------------

DROP TABLE IF EXISTS `RoleGroup`;

CREATE TABLE `RoleGroup` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `roleId` int(11) DEFAULT NULL COMMENT '角色ID',
  `groupId` int(11) DEFAULT NULL COMMENT '用户组ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table User
# ------------------------------------------------------------

DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `name` varchar(16) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `salt` varchar(16) DEFAULT NULL COMMENT '盐',
  `status` varchar(8) DEFAULT NULL COMMENT '状态',
  `last_login_time` varchar(32) DEFAULT NULL COMMENT '上次登录时间',
  `create_time` varchar(32) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;

/* admin 123456*/
INSERT INTO `User` (`id`, `name`, `password`, `salt`, `status`, `last_login_time`, `create_time`)
VALUES
	(0,'admin','a572058e39c5cff214519c636d2d4302','wrsa15khr8','0','2017-09-19 15:57:53','2017-09-19 15:57:00');

/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table UserGroup
# ------------------------------------------------------------

DROP TABLE IF EXISTS `UserGroup`;

CREATE TABLE `UserGroup` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL COMMENT '用户ID',
  `groupId` int(11) DEFAULT NULL COMMENT '用户组ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
