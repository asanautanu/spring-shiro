/*
Navicat MySQL Data Transfer

Source Server         : mpms
Source Server Version : 50536
Source Host           : localhost:3306
Source Database       : mpms

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2016-05-12 16:08:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mpms_resource
-- ----------------------------
DROP TABLE IF EXISTS `mpms_resource`;
CREATE TABLE `mpms_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `delete_flag` bit(1) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `href` varchar(200) DEFAULT NULL,
  `icon` varchar(55) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `order_no` int(11) DEFAULT NULL,
  `pId` int(11) DEFAULT NULL,
  `permission` varchar(64) DEFAULT NULL,
  `resource_type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dtr4asnso9iq9dhkv6jc1w7xn` (`permission`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mpms_resource
-- ----------------------------
INSERT INTO `mpms_resource` VALUES ('1', null, '\0', '', '', null, '系统组织管理', '1', null, null, 'MENU');
INSERT INTO `mpms_resource` VALUES ('3', null, '\0', '', 'tpl/SystemOrgManage/permissionManage.html', null, '权限管理', '2', '1', 'sys:permission:list', 'MENU');
INSERT INTO `mpms_resource` VALUES ('5', null, '\0', '', 'tpl/SystemOrgManage/personManage.html', null, '人员管理', '4', '1', 'sys:user:list', 'MENU');
INSERT INTO `mpms_resource` VALUES ('6', null, '\0', '', '', null, '用户添加', '1', '5', 'sys:user:add', 'BUTTON');
INSERT INTO `mpms_resource` VALUES ('7', null, '\0', '', '', null, '用户更新', '2', '5', 'sys:user:update', 'BUTTON');
INSERT INTO `mpms_resource` VALUES ('8', null, '\0', '', '', null, '用户详情', '3', '5', 'sys:user:info', 'BUTTON');
INSERT INTO `mpms_resource` VALUES ('9', null, '\0', '', '', null, '用户删除', '4', '5', 'sys:user:del', 'BUTTON');
INSERT INTO `mpms_resource` VALUES ('10', null, '\0', '', '', null, '用户密码重置', '5', '5', 'sys:user:resetpassword', 'BUTTON');
INSERT INTO `mpms_resource` VALUES ('11', null, '\0', '', '', null, '权限添加', '1', '3', 'sys:permission:add', 'BUTTON');
INSERT INTO `mpms_resource` VALUES ('12', null, '\0', '', '', null, '权限修改', '2', '3', 'sys:permission:update', 'BUTTON');
INSERT INTO `mpms_resource` VALUES ('13', null, '\0', '', '', null, '权限删除', '3', '3', 'sys:permission:del', 'BUTTON');
INSERT INTO `mpms_resource` VALUES ('14', null, '\0', '', '', null, '权限详情', '4', '3', 'sys:permission:info', 'BUTTON');

-- ----------------------------
-- Table structure for mpms_role
-- ----------------------------
DROP TABLE IF EXISTS `mpms_role`;
CREATE TABLE `mpms_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT NULL,
  `creater` int(11) DEFAULT NULL,
  `creater_name` varchar(55) DEFAULT NULL,
  `delete_flag` bit(1) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mpms_role
-- ----------------------------
INSERT INTO `mpms_role` VALUES ('1', '2016-05-12 12:53:32', '1', 'admin', '\0', null, '管理员', '2016-05-12 13:38:50');

-- ----------------------------
-- Table structure for mpms_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `mpms_role_resource`;
CREATE TABLE `mpms_role_resource` (
  `role_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`resource_id`),
  KEY `FK_19wxmhvr1g53bw5gdiwosnvnc` (`resource_id`),
  CONSTRAINT `FK_9pb78w4v8ncjj0nlefi3i4cia` FOREIGN KEY (`role_id`) REFERENCES `mpms_role` (`id`),
  CONSTRAINT `FK_19wxmhvr1g53bw5gdiwosnvnc` FOREIGN KEY (`resource_id`) REFERENCES `mpms_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mpms_role_resource
-- ----------------------------
INSERT INTO `mpms_role_resource` VALUES ('1', '1');
INSERT INTO `mpms_role_resource` VALUES ('1', '3');
INSERT INTO `mpms_role_resource` VALUES ('1', '5');
INSERT INTO `mpms_role_resource` VALUES ('1', '6');
INSERT INTO `mpms_role_resource` VALUES ('1', '7');
INSERT INTO `mpms_role_resource` VALUES ('1', '8');
INSERT INTO `mpms_role_resource` VALUES ('1', '9');
INSERT INTO `mpms_role_resource` VALUES ('1', '11');
INSERT INTO `mpms_role_resource` VALUES ('1', '12');
INSERT INTO `mpms_role_resource` VALUES ('1', '13');
INSERT INTO `mpms_role_resource` VALUES ('1', '14');

-- ----------------------------
-- Table structure for mpms_user
-- ----------------------------
DROP TABLE IF EXISTS `mpms_user`;
CREATE TABLE `mpms_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `audit_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `creater_name` varchar(55) DEFAULT NULL,
  `delete_flag` bit(1) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `is_login` bit(1) NOT NULL,
  `name` varchar(10) DEFAULT NULL,
  `number` varchar(50) DEFAULT NULL,
  `password` varchar(64) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `salt` varchar(64) NOT NULL,
  `state` bit(1) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mpms_user
-- ----------------------------
INSERT INTO `mpms_user` VALUES ('1', '2016-05-12 12:39:52', '2016-05-12 12:39:50', 'admin', '\0', null, '', 'admin', '00001', '978c3abcbc20a5a1056bbb31970e6e87baacabdf', null, '6c12f2cc7a606bb3', '\0', '2016-05-12 12:39:48', '1', 'admin');
INSERT INTO `mpms_user` VALUES ('2', null, '2016-05-12 15:29:22', 'admin', '\0', null, '', '111', '1111', '555694f10c58c1066f2f9ac0ee8d85fde903b41a', null, '65214a81c0281ac8', '', '2016-05-12 15:29:22', '1', '1111');
INSERT INTO `mpms_user` VALUES ('3', null, '2016-05-12 15:32:39', 'admin', '\0', null, '', '11111', '111111', '888217c73d1aadc2a9f00cb5bca241e914026b3f', null, 'd1712a53f10e6a31', '', '2016-05-12 15:32:39', '1', '11111');
INSERT INTO `mpms_user` VALUES ('4', null, '2016-05-12 15:32:46', 'admin', '\0', null, '', '111qqq', '111qq', '1cd279282bbd7a02ba22c39e0af4a99556f42ada', null, '835e9f868b72589f', '', '2016-05-12 15:32:46', '1', '111111');

-- ----------------------------
-- Table structure for mpms_user_role
-- ----------------------------
DROP TABLE IF EXISTS `mpms_user_role`;
CREATE TABLE `mpms_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_9m9sp8l1o3sv8x84ce8r76f6c` (`role_id`),
  CONSTRAINT `FK_pqsow4a2atmvy7gu2jathajtu` FOREIGN KEY (`user_id`) REFERENCES `mpms_user` (`id`),
  CONSTRAINT `FK_9m9sp8l1o3sv8x84ce8r76f6c` FOREIGN KEY (`role_id`) REFERENCES `mpms_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mpms_user_role
-- ----------------------------
INSERT INTO `mpms_user_role` VALUES ('1', '1');
