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

 Date: 20/12/2019 09:36:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_role`;
CREATE TABLE `sys_menu_role`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `menu_id` int(10) NULL DEFAULT NULL,
  `role_id` int(10) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 208 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu_role
-- ----------------------------
INSERT INTO `sys_menu_role` VALUES (3, 3, 1);
INSERT INTO `sys_menu_role` VALUES (4, 4, 1);
INSERT INTO `sys_menu_role` VALUES (5, 5, 1);
INSERT INTO `sys_menu_role` VALUES (6, 6, 1);
INSERT INTO `sys_menu_role` VALUES (7, 7, 1);
INSERT INTO `sys_menu_role` VALUES (8, 8, 1);
INSERT INTO `sys_menu_role` VALUES (9, 9, 1);
INSERT INTO `sys_menu_role` VALUES (10, 10, 1);
INSERT INTO `sys_menu_role` VALUES (11, 11, 1);
INSERT INTO `sys_menu_role` VALUES (12, 12, 1);
INSERT INTO `sys_menu_role` VALUES (13, 13, 1);
INSERT INTO `sys_menu_role` VALUES (14, 14, 1);
INSERT INTO `sys_menu_role` VALUES (15, 15, 1);
INSERT INTO `sys_menu_role` VALUES (16, 16, 1);
INSERT INTO `sys_menu_role` VALUES (36, 2, 1);
INSERT INTO `sys_menu_role` VALUES (193, 2, 2);
INSERT INTO `sys_menu_role` VALUES (194, 4, 2);
INSERT INTO `sys_menu_role` VALUES (195, 5, 2);
INSERT INTO `sys_menu_role` VALUES (196, 6, 2);
INSERT INTO `sys_menu_role` VALUES (197, 7, 2);
INSERT INTO `sys_menu_role` VALUES (198, 8, 2);
INSERT INTO `sys_menu_role` VALUES (199, 9, 2);
INSERT INTO `sys_menu_role` VALUES (200, 10, 2);
INSERT INTO `sys_menu_role` VALUES (201, 11, 2);
INSERT INTO `sys_menu_role` VALUES (202, 12, 2);
INSERT INTO `sys_menu_role` VALUES (203, 3, 2);
INSERT INTO `sys_menu_role` VALUES (204, 13, 2);
INSERT INTO `sys_menu_role` VALUES (205, 14, 2);
INSERT INTO `sys_menu_role` VALUES (206, 15, 2);
INSERT INTO `sys_menu_role` VALUES (207, 16, 2);

SET FOREIGN_KEY_CHECKS = 1;
