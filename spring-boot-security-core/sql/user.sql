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

 Date: 20/12/2019 09:37:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(50) NOT NULL COMMENT 'id',
  `username` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(90) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `state` int(1) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (119808374624331763, 'superadmin', '$2a$10$XkiBrfu8pBFeAGcadQoNwe.XOeVsWgex0EU1GOmC.Z0JWZx4mKMGu', 1, '2019-11-23 15:23:58', '2019-11-23 17:17:32');
INSERT INTO `user` VALUES (1197787153178943490, 'user1', '$2a$10$uiAZ8LbpcOAVqzvkOMPZB.H8b6W1i6vfLMGE.WTjeggZBzYMKQgz.', 1, '2019-11-23 14:58:13', '2019-11-23 14:58:19');
INSERT INTO `user` VALUES (1197787453193310210, 'user2', '$2a$10$4Gaunx9YE4HgH51AAeKyzu/D3JYnQ6I/wLjCPKZW4.cy0npDQuK02', 1, '2019-11-23 14:58:15', '2019-11-23 14:58:21');
INSERT INTO `user` VALUES (1197792010405163009, 'admin', '$2a$10$XkiBrfu8pBFeAGcadQoNwe.XOeVsWgex0EU1GOmC.Z0JWZx4mKMGu', 1, '2019-11-23 14:58:19', '2019-11-23 14:58:24');
INSERT INTO `user` VALUES (1198082436295057409, 'test', '$2a$10$MaukUouaGylrv30x0/A4ZeKbTsC8O0d1YfnXVmuXK8dxXczMIOwi6', 1, '2019-11-23 14:58:22', '2019-11-27 09:48:59');
INSERT INTO `user` VALUES (1198083746243317762, 'test1', '$2a$10$qc/MYDg8K1kGUx5UnuFVS.chxRAK64B5ATvkGZMO2zN/F5iGNoxuy', 1, '2019-11-23 14:58:25', '2019-11-23 14:58:32');
INSERT INTO `user` VALUES (1200227606331412482, '66666', '$2a$10$ozOrcBcS99dPZ4ToCiM3q.SQ.NmcWX5HW7tmSUXuH8GncpqA0HDWq', 1, '2019-11-29 09:38:55', '2019-12-08 17:10:49');
INSERT INTO `user` VALUES (1203605858980687874, '555', '$2a$10$pEihbQgzebrsQBlXX2asMe3CA4NByJLdWqouOVaL/WMS951Jj98Vq', 1, '2019-12-08 17:22:53', NULL);

SET FOREIGN_KEY_CHECKS = 1;
