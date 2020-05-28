/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : qiandao

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2020-05-26 23:20:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_config`;
CREATE TABLE `tb_config` (
  `id` int(11) NOT NULL,
  `current_week` int(11) NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modify` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_config
-- ----------------------------
INSERT INTO `tb_config` VALUES ('1', '2', '2020-05-22 17:10:28', '2020-05-22 17:10:31');

-- ----------------------------
-- Table structure for tb_course
-- ----------------------------
DROP TABLE IF EXISTS `tb_course`;
CREATE TABLE `tb_course` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tea_name` varchar(255) NOT NULL,
  `tea_id` varchar(255) NOT NULL,
  `course_name` varchar(255) NOT NULL,
  `course_id` varchar(255) NOT NULL,
  `course_time` varchar(255) NOT NULL,
  `course_addr` varchar(255) NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modify` datetime NOT NULL,
  `course_week` varchar(255) NOT NULL,
  `course_stanza` varchar(255) NOT NULL,
  `stu_class` varchar(255) NOT NULL,
  `stu_grade` varchar(255) NOT NULL,
  `stu_major` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_course
-- ----------------------------
INSERT INTO `tb_course` VALUES ('1', 'test', '1', '教育', '1', '20200506', '教一', '2020-05-22 11:10:24', '2020-05-22 11:10:27', '123456789', '34', '11111', '11111', '11111');
INSERT INTO `tb_course` VALUES ('2', 'kevin', '2', '上课', '2', '20200502', '教二', '2020-05-22 11:11:15', '2020-05-22 11:11:21', '123456789', '12', '1111', '1111', '1111');
INSERT INTO `tb_course` VALUES ('3', 'kevin', '2', '高数', '3', '20202020', '教三', '2020-05-22 11:17:29', '2020-05-22 11:17:32', '1234567', '56', '1111', '1111', '1111');
INSERT INTO `tb_course` VALUES ('4', 'KAIJIA', '111', '打码', '4', '2', '教一', '2020-05-24 00:33:07', '2020-05-24 00:33:09', '123456789', '56', '1111', '1111', '1111');

-- ----------------------------
-- Table structure for tb_school_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_school_info`;
CREATE TABLE `tb_school_info` (
  `id` bigint(20) NOT NULL,
  `type` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `school` varchar(255) NOT NULL,
  `accountId` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modify` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_school_info
-- ----------------------------
INSERT INTO `tb_school_info` VALUES ('1', 'teacher', 'test', 'test', '111', '111', '2020-05-19 00:58:51', '2020-05-19 00:58:54');
INSERT INTO `tb_school_info` VALUES ('2', 'teacher', 'kj', 'test', '11', '11', '2020-05-23 17:31:39', '2020-05-23 17:31:42');
INSERT INTO `tb_school_info` VALUES ('3', 'student', 'stu', 'test', '222', '222', '2020-05-23 17:59:37', '2020-05-23 17:59:40');

-- ----------------------------
-- Table structure for tb_signin
-- ----------------------------
DROP TABLE IF EXISTS `tb_signin`;
CREATE TABLE `tb_signin` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tea_name` varchar(255) DEFAULT NULL,
  `tea_id` varchar(255) DEFAULT NULL,
  `course_name` varchar(255) DEFAULT NULL,
  `course_addr` varchar(255) DEFAULT NULL,
  `course_week` varchar(255) DEFAULT NULL,
  `course_stanza` varchar(255) DEFAULT NULL,
  `course_id` varchar(255) DEFAULT NULL,
  `is_sign` varchar(255) DEFAULT NULL,
  `stu_id` varchar(255) DEFAULT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modify` datetime NOT NULL,
  `sign_addr` varchar(255) DEFAULT NULL,
  `sign_num` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_signin
-- ----------------------------
INSERT INTO `tb_signin` VALUES ('4', 'kj', '111', '高数', '教三', '123456', '56', '4', 'Y', '222', '2020-05-23 23:44:16', '2020-05-23 23:44:16', '维度：23.56606;经度：116.41211', '111111');
INSERT INTO `tb_signin` VALUES ('5', 'kj', '111', '高数', '教三', '123456', '56', '4', 'Y', '', '2020-05-23 23:53:05', '2020-05-23 23:53:05', '维度：23.56606;经度：116.41211', '111111');
INSERT INTO `tb_signin` VALUES ('6', 'kj', '111', '高数', '教三', '123456', '56', '4', 'Y', '', '2020-05-23 23:55:33', '2020-05-23 23:55:33', '维度：23.56606;经度：116.41211', '111111');
INSERT INTO `tb_signin` VALUES ('7', 'kj', '111', '高数', '教三', '123456', '56', '4', 'Y', '', '2020-05-23 23:56:31', '2020-05-23 23:56:31', '维度：23.56606;经度：116.41211', '111111');
INSERT INTO `tb_signin` VALUES ('8', 'kj', '111', '高数', '教三', '123456', '56', '4', 'Y', '', '2020-05-23 23:57:46', '2020-05-23 23:57:46', '维度：23.56606;经度：116.41211', '111111');

-- ----------------------------
-- Table structure for tb_stu
-- ----------------------------
DROP TABLE IF EXISTS `tb_stu`;
CREATE TABLE `tb_stu` (
  `id` bigint(255) unsigned NOT NULL AUTO_INCREMENT,
  `stu_name` varchar(255) DEFAULT NULL,
  `stu_id` varchar(255) DEFAULT NULL,
  `stu_collage` varchar(255) DEFAULT NULL,
  `stu_school` varchar(255) DEFAULT NULL,
  `stu_phone` varchar(255) DEFAULT NULL,
  `stu_major` varchar(255) DEFAULT NULL,
  `stu_openid` varchar(255) DEFAULT NULL,
  `stu_grade` varchar(255) DEFAULT NULL,
  `stu_class` varchar(255) DEFAULT NULL,
  `stu_email` varchar(255) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modify` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `stuid_index` (`stu_id`),
  UNIQUE KEY `openid_index` (`stu_openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_stu
-- ----------------------------

-- ----------------------------
-- Table structure for tb_stu_course
-- ----------------------------
DROP TABLE IF EXISTS `tb_stu_course`;
CREATE TABLE `tb_stu_course` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tea_name` varchar(255) NOT NULL,
  `tea_id` varchar(255) NOT NULL,
  `course_name` varchar(255) NOT NULL,
  `course_id` varchar(255) NOT NULL,
  `course_time` varchar(255) NOT NULL,
  `course_addr` varchar(255) NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modify` datetime NOT NULL,
  `course_week` varchar(255) NOT NULL,
  `course_stanza` varchar(255) NOT NULL,
  `stu_class` varchar(255) NOT NULL,
  `stu_grade` varchar(255) NOT NULL,
  `stu_major` varchar(255) NOT NULL,
  `stu_id` varchar(255) NOT NULL,
  `stu_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_stu_course
-- ----------------------------
INSERT INTO `tb_stu_course` VALUES ('9', 'kevin', '2', '上课', '2', '1', '教二', '2020-05-22 15:27:06', '2020-05-22 15:27:06', '123456', '12', '1111', '1111', '1111', '112', 'kevin');
INSERT INTO `tb_stu_course` VALUES ('10', 'kevin', '2', '高数', '3', '2', '教三', '2020-05-22 15:27:06', '2020-05-22 15:27:06', '123456', '56', '1111', '1111', '1111', '112', 'kevin');

-- ----------------------------
-- Table structure for tb_stu_signin
-- ----------------------------
DROP TABLE IF EXISTS `tb_stu_signin`;
CREATE TABLE `tb_stu_signin` (
  `id` bigint(255) unsigned NOT NULL AUTO_INCREMENT,
  `stu_openid` varchar(255) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modify` datetime DEFAULT NULL,
  `signin_num` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_stu_signin
-- ----------------------------
INSERT INTO `tb_stu_signin` VALUES ('1', '123456789', '2020-05-23 16:25:22', '2020-05-23 16:25:22', '9999');
INSERT INTO `tb_stu_signin` VALUES ('2', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 23:44:16', '2020-05-23 23:44:16', '111111');
INSERT INTO `tb_stu_signin` VALUES ('3', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 23:53:05', '2020-05-23 23:53:05', '111111');
INSERT INTO `tb_stu_signin` VALUES ('4', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 23:55:33', '2020-05-23 23:55:33', '111111');
INSERT INTO `tb_stu_signin` VALUES ('5', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 23:56:31', '2020-05-23 23:56:31', '111111');
INSERT INTO `tb_stu_signin` VALUES ('6', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 23:57:46', '2020-05-23 23:57:46', '111111');

-- ----------------------------
-- Table structure for tb_teacher
-- ----------------------------
DROP TABLE IF EXISTS `tb_teacher`;
CREATE TABLE `tb_teacher` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tea_name` varchar(255) DEFAULT NULL,
  `tea_id` varchar(255) DEFAULT NULL,
  `tea_school` varchar(255) DEFAULT NULL,
  `tea_collage` varchar(255) DEFAULT NULL,
  `tea_phone` varchar(255) DEFAULT NULL,
  `tea_email` varchar(255) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modify` datetime DEFAULT NULL,
  `tea_openid` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `openid_index` (`tea_openid`) USING BTREE,
  UNIQUE KEY `teaid_index` (`tea_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_teacher
-- ----------------------------
INSERT INTO `tb_teacher` VALUES ('1', '0', '0', '0', '0', '0', '0', '2020-05-19 01:52:17', '2020-05-19 01:52:21', '0');
INSERT INTO `tb_teacher` VALUES ('9', '1', '2', 'test', '1', '1', '1', '2020-05-20 01:12:16', '2020-05-20 01:12:16', '32434234');
INSERT INTO `tb_teacher` VALUES ('30', 'KJ', '111', 'test', '自动化', '1278700340@qq.com', '1278700340@qq.com', '2020-05-26 21:34:27', '2020-05-26 22:33:22', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko');

-- ----------------------------
-- Table structure for tb_tea_course
-- ----------------------------
DROP TABLE IF EXISTS `tb_tea_course`;
CREATE TABLE `tb_tea_course` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `course_id` varchar(255) NOT NULL,
  `tea_name` varchar(255) NOT NULL,
  `tea_id` varchar(255) NOT NULL,
  `course_name` varchar(255) NOT NULL,
  `course_addr` varchar(255) NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modify` datetime NOT NULL,
  `course_week` varchar(255) NOT NULL,
  `course_stanza` varchar(255) NOT NULL,
  `stu_class` varchar(255) NOT NULL,
  `stu_grade` varchar(255) NOT NULL,
  `stu_major` varchar(255) NOT NULL,
  `course_time` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_tea_course
-- ----------------------------
INSERT INTO `tb_tea_course` VALUES ('8', '2', 'kevin', '2', '上课', '教二', '2020-05-22 15:25:43', '2020-05-22 15:25:43', '123456', '12', '1111', '1111', '1111', '1');
INSERT INTO `tb_tea_course` VALUES ('9', '3', 'kevin', '2', '高数', '教三', '2020-05-22 15:25:43', '2020-05-22 15:25:43', '123456', '56', '1111', '1111', '1111', '2');
INSERT INTO `tb_tea_course` VALUES ('10', '4', 'kj', '111', '高数', '教三', '2020-05-23 23:15:46', '2020-05-23 23:15:49', '123456', '56', '1111', '1111', '1111', '2');
INSERT INTO `tb_tea_course` VALUES ('11', '4', 'KAIJIA', '111', '打码', '教一', '2020-05-24 00:33:21', '2020-05-24 00:33:21', '123456789', '56', '1111', '1111', '1111', '2');
INSERT INTO `tb_tea_course` VALUES ('12', '4', 'KAIJIA', '111', '打码', '教一', '2020-05-24 00:34:52', '2020-05-24 00:34:52', '123456789', '56', '1111', '1111', '1111', '2');
INSERT INTO `tb_tea_course` VALUES ('14', '6', 'KAIJIA', '111', '英语', '教三', '2020-05-25 22:35:04', '2020-05-25 22:35:04', '2', '2', '1', '17', '物联网', '4');
INSERT INTO `tb_tea_course` VALUES ('15', '1111', 'KAIJIA', '111', 'test', '实验楼', '2020-05-25 22:43:38', '2020-05-25 22:43:38', '2', '89', '1', '17', 'test', '1');
INSERT INTO `tb_tea_course` VALUES ('16', '4', 'KAIJIA', '111', '打码', '教一', '2020-05-26 21:36:09', '2020-05-26 21:36:09', '123456789', '56', '1111', '1111', '1111', '2');

-- ----------------------------
-- Table structure for tb_tea_signin
-- ----------------------------
DROP TABLE IF EXISTS `tb_tea_signin`;
CREATE TABLE `tb_tea_signin` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tea_openid` varchar(255) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modify` datetime DEFAULT NULL,
  `signin_num` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_tea_signin
-- ----------------------------
INSERT INTO `tb_tea_signin` VALUES ('6', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 23:32:19', '2020-05-23 23:32:19', '111111');
INSERT INTO `tb_tea_signin` VALUES ('7', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-26 21:40:39', '2020-05-26 21:40:39', '555555');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `type` varchar(255) DEFAULT NULL,
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `openid` varchar(255) NOT NULL,
  `gmt_modify` datetime NOT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (null, '1', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 16:52:02', '2020-05-23 16:52:02');
INSERT INTO `tb_user` VALUES (null, '2', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 16:55:19', '2020-05-23 16:55:19');
INSERT INTO `tb_user` VALUES (null, '3', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 17:09:30', '2020-05-23 17:09:30');
INSERT INTO `tb_user` VALUES (null, '4', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 17:22:38', '2020-05-23 17:22:38');
INSERT INTO `tb_user` VALUES (null, '5', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 17:26:00', '2020-05-23 17:26:00');
INSERT INTO `tb_user` VALUES (null, '6', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 17:30:13', '2020-05-23 17:30:13');
INSERT INTO `tb_user` VALUES (null, '7', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 17:34:57', '2020-05-23 17:34:57');
INSERT INTO `tb_user` VALUES (null, '8', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 17:41:26', '2020-05-23 17:41:26');
INSERT INTO `tb_user` VALUES (null, '9', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 17:44:04', '2020-05-23 17:44:04');
INSERT INTO `tb_user` VALUES (null, '10', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 17:45:55', '2020-05-23 17:45:55');
INSERT INTO `tb_user` VALUES (null, '11', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 17:57:41', '2020-05-23 17:57:41');
INSERT INTO `tb_user` VALUES (null, '12', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 17:59:33', '2020-05-23 17:59:33');
INSERT INTO `tb_user` VALUES (null, '13', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 18:01:33', '2020-05-23 18:01:33');
INSERT INTO `tb_user` VALUES (null, '14', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 18:13:58', '2020-05-23 18:13:58');
INSERT INTO `tb_user` VALUES (null, '15', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 18:14:22', '2020-05-23 18:14:22');
INSERT INTO `tb_user` VALUES (null, '16', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 22:39:37', '2020-05-23 22:39:37');
INSERT INTO `tb_user` VALUES (null, '17', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 23:36:32', '2020-05-23 23:36:32');
INSERT INTO `tb_user` VALUES (null, '18', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 23:40:05', '2020-05-23 23:40:05');
INSERT INTO `tb_user` VALUES (null, '19', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-23 23:48:45', '2020-05-23 23:48:45');
INSERT INTO `tb_user` VALUES (null, '20', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-24 00:16:37', '2020-05-24 00:16:37');
INSERT INTO `tb_user` VALUES (null, '21', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-24 00:21:53', '2020-05-24 00:21:53');
INSERT INTO `tb_user` VALUES (null, '22', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-25 23:05:34', '2020-05-25 23:05:34');
INSERT INTO `tb_user` VALUES (null, '23', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-26 21:27:28', '2020-05-26 21:27:28');
INSERT INTO `tb_user` VALUES (null, '24', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-26 21:32:42', '2020-05-26 21:32:42');
INSERT INTO `tb_user` VALUES (null, '25', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-26 21:34:07', '2020-05-26 21:34:07');
INSERT INTO `tb_user` VALUES (null, '26', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-26 22:05:23', '2020-05-26 22:05:23');
INSERT INTO `tb_user` VALUES (null, '27', 'ow7ka44IIqcB50h9mgTS6Gbfm8Ko', '2020-05-26 22:05:30', '2020-05-26 22:05:30');
