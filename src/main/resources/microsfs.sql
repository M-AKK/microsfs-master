/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : microsfs

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 25/12/2020 17:24:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_block_duplicate
-- ----------------------------
DROP TABLE IF EXISTS `tb_block_duplicate`;
CREATE TABLE `tb_block_duplicate` (
  `id` varchar(32) NOT NULL,
  `bid` varchar(32) NOT NULL,
  `machinecode` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_block_duplicate
-- ----------------------------
BEGIN;
INSERT INTO `tb_block_duplicate` VALUES ('2c2c33a0b4d64b8ea32efa7cafaa7a9a', '7a5d683f4738498daa3acc025035a8c3', '98dfd31a2c2cd58c2f44d4d872709e87');
INSERT INTO `tb_block_duplicate` VALUES ('36b06713dd1144c39911e2a6edb52778', '7b1ea47dc04f4e8c9e6107004c0e153e', NULL);
INSERT INTO `tb_block_duplicate` VALUES ('67c056e9cf9746eebbc2418005eef01d', '1be2993ae8ee4f59be7ad33a66d785e5', NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_datanode
-- ----------------------------
DROP TABLE IF EXISTS `tb_datanode`;
CREATE TABLE `tb_datanode` (
  `id` varchar(32) NOT NULL,
  `ip` varchar(16) NOT NULL,
  `port` int NOT NULL,
  `active_time` datetime NOT NULL,
  `register_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_datanode
-- ----------------------------
BEGIN;
INSERT INTO `tb_datanode` VALUES ('98dfd31a2c2cd58c2f44d4d872709e87', '192.168.30.1', 9096, '2020-10-17 17:57:01', '2020-10-09 21:43:56');
COMMIT;

-- ----------------------------
-- Table structure for tb_file_block
-- ----------------------------
DROP TABLE IF EXISTS `tb_file_block`;
CREATE TABLE `tb_file_block` (
  `id` varchar(32) NOT NULL,
  `fid` varchar(32) NOT NULL,
  `seq` int NOT NULL DEFAULT '0',
  `size` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_file_block
-- ----------------------------
BEGIN;
INSERT INTO `tb_file_block` VALUES ('1be2993ae8ee4f59be7ad33a66d785e5', '09e19229eea0439daa9e9c1336444326', 1, 1000);
INSERT INTO `tb_file_block` VALUES ('7a5d683f4738498daa3acc025035a8c3', '61a33731dcd2450392ac4769976ee778', 1, 1000);
INSERT INTO `tb_file_block` VALUES ('7b1ea47dc04f4e8c9e6107004c0e153e', '0b833975c6bd48ce9322dcf9e8fc6903', 1, 1000);
COMMIT;

-- ----------------------------
-- Table structure for tb_file_entity
-- ----------------------------
DROP TABLE IF EXISTS `tb_file_entity`;
CREATE TABLE `tb_file_entity` (
  `uuid` varchar(32) NOT NULL,
  `name` varchar(128) NOT NULL,
  `alias` varchar(128) NOT NULL,
  `own` varchar(32) DEFAULT NULL,
  `group` varchar(32) DEFAULT NULL,
  `type` int NOT NULL,
  `parent` varchar(32) NOT NULL,
  `create_time` datetime NOT NULL,
  `modify_time` datetime NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_file_entity
-- ----------------------------
BEGIN;
INSERT INTO `tb_file_entity` VALUES ('00000000000000001111111100000000', '/', '/', 'root', 'root', 2, '0', '2020-10-06 16:42:55', '2020-10-06 16:42:59');
INSERT INTO `tb_file_entity` VALUES ('09e19229eea0439daa9e9c1336444326', 'akk', 'akk', 'root', 'root', 1, '00000000000000001111111100000000', '2020-12-21 15:22:23', '2020-12-21 15:22:23');
INSERT INTO `tb_file_entity` VALUES ('313c1a2cde9b4e4c95944fec4a72b46f', 'wenz', 'wenz', 'root', 'root', 2, '00000000000000001111111100000000', '2020-10-07 23:30:11', '2020-10-07 23:30:11');
INSERT INTO `tb_file_entity` VALUES ('61a33731dcd2450392ac4769976ee778', 'test', 'test', 'root', 'root', 1, '313c1a2cde9b4e4c95944fec4a72b46f', '2020-10-17 13:10:26', '2020-10-17 13:10:26');
INSERT INTO `tb_file_entity` VALUES ('b67db059bfac462ea50bf20ca3af3430', 'akkson', 'akkson', NULL, NULL, 2, '09e19229eea0439daa9e9c1336444326', '2020-12-25 17:02:17', '2020-12-25 17:02:17');
INSERT INTO `tb_file_entity` VALUES ('bd906712226e450c99841e976948e928', 'test3', 'test3', NULL, NULL, 2, '313c1a2cde9b4e4c95944fec4a72b46f', '2020-12-25 16:37:04', '2020-12-25 16:37:04');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
