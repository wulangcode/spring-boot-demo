/*
 Navicat Premium Data Transfer

 Source Server         : 154.8.216.45
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 154.8.216.45:3306
 Source Schema         : duo_shu_ju_yuan_two

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 10/12/2019 20:48:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for two
-- ----------------------------
DROP TABLE IF EXISTS `two`;
CREATE TABLE `two`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of two
-- ----------------------------
INSERT INTO `two` VALUES (1, '多数据源测试2', '多数据源测试2');

SET FOREIGN_KEY_CHECKS = 1;
