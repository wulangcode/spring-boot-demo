/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.245
 Source Server Type    : MySQL
 Source Server Version : 50616
 Source Host           : 192.168.1.245:3306
 Source Schema         : tbl_quartz_job

 Target Server Type    : MySQL
 Target Server Version : 50616
 File Encoding         : 65001

 Date: 21/12/2019 10:02:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_quartz_job
-- ----------------------------
DROP TABLE IF EXISTS `tbl_quartz_job`;
CREATE TABLE `tbl_quartz_job`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `job_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务分组',
  `job_class_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '执行类',
  `cron_expression` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'cron表达式',
  `trigger_state` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务状态',
  `old_job_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '修改之前的任务名称',
  `old_job_group` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '修改之前的任务分组',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述',
  `job_parameter` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '执行类参数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `un_group_name`(`job_group`, `job_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tbl_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_user`;
CREATE TABLE `tbl_sys_user`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `age` tinyint(3) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3784 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
