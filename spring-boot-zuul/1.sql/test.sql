/*
 Navicat Premium Data Transfer

 Source Server         : 154.8.216.45
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : 154.8.216.45:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 02/01/2020 12:27:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gateway_api_route
-- ----------------------------
DROP TABLE IF EXISTS `gateway_api_route`;
CREATE TABLE `gateway_api_route`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `service_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `retryable` tinyint(1) NULL DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL,
  `strip_prefix` int(11) NULL DEFAULT NULL,
  `api_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gateway_api_route
-- ----------------------------
INSERT INTO `gateway_api_route` VALUES ('order-service', '/order/**', 'order-service', NULL, 0, 1, 1, NULL);

-- ----------------------------
-- Table structure for gray_release_config
-- ----------------------------
DROP TABLE IF EXISTS `gray_release_config`;
CREATE TABLE `gray_release_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `enable_gray_release` int(11) NULL DEFAULT NULL COMMENT '是否启用灰度发布 0：不开启 1：开启',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gray_release_config
-- ----------------------------
INSERT INTO `gray_release_config` VALUES (1, 'order-service', '/order', 1);

SET FOREIGN_KEY_CHECKS = 1;
