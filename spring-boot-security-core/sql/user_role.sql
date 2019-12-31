/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.245
 Source Server Type    : MySQL
 Source Server Version : 50616
 Source Host           : 192.168.1.245:3306
 Source Schema         : oilparas

 Target Server Type    : MySQL
 Target Server Version : 50616
 File Encoding         : 65001

 Date: 20/12/2019 09:37:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(30) NULL DEFAULT NULL,
  `role_id` int(10) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1197792010405163009, 1, NULL, NULL);
INSERT INTO `user_role` VALUES (3, 1198082436295057409, 2, NULL, NULL);
INSERT INTO `user_role` VALUES (4, 1198083746243317762, 2, NULL, NULL);
INSERT INTO `user_role` VALUES (5, 1197787153178943490, 2, NULL, NULL);
INSERT INTO `user_role` VALUES (6, 1197787453193310210, 2, NULL, NULL);
INSERT INTO `user_role` VALUES (7, 119808374624331763, 1, NULL, NULL);
INSERT INTO `user_role` VALUES (8, 1200227606331412482, 1, NULL, '2019-12-08 17:00:18');
INSERT INTO `user_role` VALUES (12, 1203605858980687874, 2, '2019-12-08 17:22:53', NULL);

SET FOREIGN_KEY_CHECKS = 1;
