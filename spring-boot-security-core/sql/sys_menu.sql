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

 Date: 20/12/2019 09:36:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` int(30) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `parent_id` int(30) NULL DEFAULT NULL,
  `order_number` int(10) NULL DEFAULT NULL,
  `enabled` int(10) NULL DEFAULT NULL,
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `keepAlive` int(10) NULL DEFAULT NULL,
  `requireAuth` int(10) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (2, '采集管理', '/ship/gather/**', '/', -1, 1, 1, NULL, NULL, 1, '2019-11-25 14:06:54', '2019-11-26 15:30:43');
INSERT INTO `sys_menu` VALUES (3, '系统管理', '/ship/system/**', '/', -1, 2, 1, NULL, NULL, 1, '2019-11-25 14:06:56', '2019-11-26 15:30:46');
INSERT INTO `sys_menu` VALUES (4, '水分', '/ship/waterMonitorPara/**', '/', 2, 1, 1, NULL, NULL, 1, '2019-11-25 14:06:59', '2019-11-27 13:59:58');
INSERT INTO `sys_menu` VALUES (5, '颗粒污染度', '/ship/granMonitorPara/**', '/grain_pollution', 2, 2, 1, NULL, NULL, 1, '2019-11-25 14:07:02', '2019-11-26 15:29:03');
INSERT INTO `sys_menu` VALUES (6, '四合一传感器', '/ship/viscosityMonitorPara/**', '/sensor', 2, 3, 1, NULL, NULL, 1, '2019-11-25 14:07:04', '2019-11-26 15:29:37');
INSERT INTO `sys_menu` VALUES (7, '铁含量', '/ship/ferroPara/**', '/iron_content', 2, 4, 1, NULL, NULL, 1, '2019-11-25 14:07:05', '2019-11-26 15:29:13');
INSERT INTO `sys_menu` VALUES (8, '传感器实时数据1', '/ship/sensor/waterMonitorPara1', '/sensor_realtime_one', 2, 5, 1, NULL, NULL, 1, '2019-11-25 14:07:06', '2019-11-26 15:29:21');
INSERT INTO `sys_menu` VALUES (9, '传感器实时数据2', '/ship/sensor/waterMonitorPara2', '/sensor_realtime_two', 2, 6, 1, NULL, NULL, 1, '2019-11-25 14:07:07', '2019-11-26 15:29:27');
INSERT INTO `sys_menu` VALUES (10, '传感器实时数据3', '/ship/sensor/waterMonitorPara3', '/sensor_realtime_three', 2, 7, 1, NULL, NULL, 1, '2019-11-25 14:07:08', '2019-11-26 15:29:44');
INSERT INTO `sys_menu` VALUES (11, '参数配置', '/ship/reference/**', '/reference', 2, 8, 1, NULL, NULL, 1, '2019-11-25 14:07:09', '2019-12-06 14:23:13');
INSERT INTO `sys_menu` VALUES (12, '数据查询及导出', '/ship/data/**', '/data_query_export', 2, 9, 1, NULL, NULL, 1, '2019-11-25 14:07:10', '2019-11-26 15:30:02');
INSERT INTO `sys_menu` VALUES (13, '用户管理', '/ship/system/user/**', '/user_manage', 3, 1, 1, NULL, NULL, 1, '2019-11-25 14:07:11', '2019-11-27 15:38:47');
INSERT INTO `sys_menu` VALUES (14, '角色管理', '/ship/system/role/**', '/role_manage', 3, 2, 1, NULL, NULL, 1, '2019-11-25 14:07:12', '2019-11-27 15:38:43');
INSERT INTO `sys_menu` VALUES (15, '菜单管理', '/ship/system/menu/**', '/menu_manage', 3, 3, 1, NULL, NULL, 1, '2019-11-25 14:07:12', '2019-11-27 15:38:39');
INSERT INTO `sys_menu` VALUES (16, '登录日志', '/ship/system/loginRecord/**', '/log_manage', 3, 4, 1, NULL, NULL, 1, '2019-11-25 14:07:13', '2019-11-27 15:38:33');

SET FOREIGN_KEY_CHECKS = 1;
