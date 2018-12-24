/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : jm_application

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-12-24 17:18:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin_resource
-- ----------------------------
DROP TABLE IF EXISTS `admin_resource`;
CREATE TABLE `admin_resource` (
  `id` bigint(20) NOT NULL,
  `r_click` varchar(255) DEFAULT NULL,
  `r_icon` varchar(255) DEFAULT NULL,
  `r_intro` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `r_sort` int(11) DEFAULT NULL,
  `r_style` varchar(255) DEFAULT NULL,
  `r_text` varchar(255) DEFAULT NULL,
  `r_type` int(11) DEFAULT NULL,
  `r_url` varchar(255) DEFAULT NULL,
  `r_is_admin` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_resource
-- ----------------------------
INSERT INTO `admin_resource` VALUES ('1', '', 'menu-icon fa fa-asterisk', '系统管理', '0', '1', '', '系统管理', '0', '', null);
INSERT INTO `admin_resource` VALUES ('3', '', 'menu-icon fa fa-users', '用户管理', '1', '3', '', '操作员管理', '0', '/webuser', '0');
INSERT INTO `admin_resource` VALUES ('4', 'updateInfo', 'fa fa-pencil bigger-120', '修改', '2', '2', 'info', '修改', '1', '/company/update', '0');
INSERT INTO `admin_resource` VALUES ('5', 'deleteInfo', 'fa fa-trash-o bigger-120', '删除', '2', '6', 'danger', '删除', '1', '/company/delete', '0');
INSERT INTO `admin_resource` VALUES ('6', 'blockInfo', 'fa fa-info-circle bigger-120', '停用', '2', '5', 'warning', '停用', '1', '/company/block', '0');
INSERT INTO `admin_resource` VALUES ('7', 'success', 'fa fa-check bigger-120', '启用', '2', '3', 'success', '启用', '1', '/company/success', '0');
INSERT INTO `admin_resource` VALUES ('8', 'saveInfo', 'fa fa-plus', '新增', '2', '1', 'success', '新增', '1', '/company/save', '0');
INSERT INTO `admin_resource` VALUES ('9', '', 'fa fa-coffee', '资源管理', '69', '3', '', '资源管理', '0', '/resource', '0');
INSERT INTO `admin_resource` VALUES ('10', 'addInfo', 'fa fa-plus', '新增', '9', '2', 'success', '新增', '1', '/resource/save', '0');
INSERT INTO `admin_resource` VALUES ('11', 'editInfo', 'fa fa-pencil bigger-120', '修改', '9', '3', 'info', '修改', '1', '/resource/update', '0');
INSERT INTO `admin_resource` VALUES ('67', 'deleteInfo', 'fa fa-trash-o bigger-120', '删除资源信息', '9', '4', 'danger', '删除', '1', '/resource/delete', '0');
INSERT INTO `admin_resource` VALUES ('69', '', 'fa fa-tag', '系统维护', '1', '1', '', '系统维护', '0', '', '0');
INSERT INTO `admin_resource` VALUES ('70', '', 'fa fa-list-alt', '字典管理', '69', '1', '', '字典管理', '0', '/dictionary', '0');
INSERT INTO `admin_resource` VALUES ('74', 'addInfo', 'fa fa-plus', '新增', '70', '1', 'success', '新增', '1', '/dictionary/save', '0');
INSERT INTO `admin_resource` VALUES ('75', 'editInfo', 'fa fa-pencil bigger-120', '修改', '70', '2', 'info', '修改', '1', '/dictionary/update', '0');
INSERT INTO `admin_resource` VALUES ('76', 'deleteInfo', 'fa fa-trash-o bigger-120', '删除', '70', '3', 'danger', '删除', '1', '/dictionary/delete', '0');
INSERT INTO `admin_resource` VALUES ('77', 'query', 'fa fa-search', '角色查询', '103', '0', 'primary', '查询', '1', '/role/list', null);
INSERT INTO `admin_resource` VALUES ('103', '', 'fa fa-cogs', '角色管理', '1', '3', '', '角色管理', '0', '/role', '0');
INSERT INTO `admin_resource` VALUES ('105', 'addInfo', 'fa fa-plus', '新增', '103', '1', 'success', '新增', '1', '/role/save', '0');
INSERT INTO `admin_resource` VALUES ('106', 'editInfo', 'fa fa-pencil bigger-120', '修改', '103', '2', 'info', '修改', '1', '/role/update', '0');
INSERT INTO `admin_resource` VALUES ('107', 'deleteInfo', 'fa fa-trash-o bigger-120', '删除角色', '103', '4', 'danger', '删除', '1', '/role/delete', '0');
INSERT INTO `admin_resource` VALUES ('108', 'resourceClick', 'fa fa-cogs bigger-120', '资源配置', '103', '3', 'warning', '资源配置', '1', '/role/resource', '0');
INSERT INTO `admin_resource` VALUES ('112', 'resourceInfo', 'fa fa-cogs bigger-120', '资源配置', '2', '4', 'warning', '资源配置', '1', '/company/resource', '0');
INSERT INTO `admin_resource` VALUES ('394', 'saveInfo', 'fa fa-plus', '新增', '3', '2', 'success', '新增', '1', '/webuser/save', null);
INSERT INTO `admin_resource` VALUES ('395', 'updateInfo', 'fa fa-pencil bigger-120', '编辑', '3', '2', 'info', '编辑', '1', '/webuser/update', '0');
INSERT INTO `admin_resource` VALUES ('396', 'success', 'fa fa-check bigger-120', '启用', '3', '3', 'success', '启用', '1', '/webuser/success', '0');
INSERT INTO `admin_resource` VALUES ('397', 'blockInfo', 'fa fa-info-circle bigger-120', '停用', '3', '4', 'warning', '停用', '1', '/webuser/block', '0');
INSERT INTO `admin_resource` VALUES ('398', 'deleteInfo', 'fa fa-trash-o bigger-120', '删除', '3', '5', 'danger', '删除', '1', '/webuser/delete', '0');
INSERT INTO `admin_resource` VALUES ('617', 'saveInfo', 'fa fa-user-plus', '新增客户', '615', '1', 'success', '新增客户', '1', '/customer/save', '0');
INSERT INTO `admin_resource` VALUES ('618', 'editInfo', 'fa fa-pencil bigger-120', '编辑', '615', '2', 'info', '编辑', '1', '/customer/update', '0');
INSERT INTO `admin_resource` VALUES ('619', 'query', 'fa fa-search', '查询', '3', '1', 'primary', '查询', '1', '/webuser/list', '0');
INSERT INTO `admin_resource` VALUES ('1115', 'success', 'fa fa-check bigger-120', '启用', '103', '3', 'success', '启用', '1', '/role/success', null);
INSERT INTO `admin_resource` VALUES ('1116', 'blockInfo', 'fa fa-info-circle bigger-120', '停用', '103', '3', 'warning', '停用', '1', '/role/block', null);
INSERT INTO `admin_resource` VALUES ('1258', 'resource', 'fa fa-cogs bigger-120', '用户资源配置', '3', '4', 'warning', '资源配置', '1', '/webuser/resource', null);
INSERT INTO `admin_resource` VALUES ('1585', '', 'fa fa-user-circle', '客户信息管理', '0', '3', '', '用户管理', '0', '/loginuser', null);
INSERT INTO `admin_resource` VALUES ('1586', 'query', 'fa fa-search', '查询', '1585', '1', 'primary', '查询', '1', '/loginuser/list', null);
INSERT INTO `admin_resource` VALUES ('1587', 'updateInfo', 'fa fa-pencil bigger-120', '编辑', '1585', '2', 'info', '编辑', '1', '/loginuser/update', null);
INSERT INTO `admin_resource` VALUES ('1588', 'success', 'fa fa-check bigger-120', '启用', '1585', '3', 'success', '启用', '1', '/loginuser/success', null);
INSERT INTO `admin_resource` VALUES ('1589', 'blockInfo', 'fa fa-info-circle bigger-120', '停用', '1585', '4', 'warning', '停用', '1', '/loginuser/block', null);
INSERT INTO `admin_resource` VALUES ('1590', 'deleteInfo', 'fa fa-trash-o bigger-120', '删除', '1585', '5', 'danger', '删除', '1', '/loginuser/delete', null);
INSERT INTO `admin_resource` VALUES ('1598', 'addInfo', 'fa fa-plus', '新增', '1585', '1', 'success', '新增', '1', '/loginuser/save', null);
INSERT INTO `admin_resource` VALUES ('1601', '', 'fa fa-cubes', '设备管理', '0', '3', '', '设备管理', '0', '/equipment', null);
INSERT INTO `admin_resource` VALUES ('1602', 'query', 'fa fa-search', '查询', '1601', '1', 'primary', '查询', '1', '/equipment/list', null);
INSERT INTO `admin_resource` VALUES ('1603', 'addInfo', 'fa fa-plus', '新增', '1601', '2', 'success', '新增', '1', '/equipment/save', null);
INSERT INTO `admin_resource` VALUES ('1604', 'updateInfo', 'fa fa-pencil bigger-120', '编辑', '1601', '3', 'info', '编辑', '1', '/equipment/update', null);
INSERT INTO `admin_resource` VALUES ('1605', 'success', 'fa fa-check bigger-120', '启用', '1601', '4', 'success', '启用', '1', '/equipment/success', null);
INSERT INTO `admin_resource` VALUES ('1606', 'blockInfo', 'fa fa-info-circle bigger-120', '停用', '1601', '5', 'warning', '停用', '1', '/equipment/block', null);
INSERT INTO `admin_resource` VALUES ('1607', 'resetInfo', 'fa fa-cogs', '重置', '1601', '6', 'warning', '重置', '1', '/equipment/reset', null);
INSERT INTO `admin_resource` VALUES ('1608', 'deleteInfo', 'fa fa-trash-o bigger-120', '删除', '1601', '7', 'danger', '删除', '1', '/equipment/delete', null);

-- ----------------------------
-- Table structure for admin_resource_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_resource_role`;
CREATE TABLE `admin_resource_role` (
  `id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `resource_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_resource_role
-- ----------------------------
INSERT INTO `admin_resource_role` VALUES ('1232', '2018-11-21 10:29:42', '1', '1', '1208');
INSERT INTO `admin_resource_role` VALUES ('1233', '2018-11-21 10:29:42', '1', '69', '1208');
INSERT INTO `admin_resource_role` VALUES ('1234', '2018-11-21 10:29:42', '1', '70', '1208');
INSERT INTO `admin_resource_role` VALUES ('1235', '2018-11-21 10:29:42', '1', '74', '1208');
INSERT INTO `admin_resource_role` VALUES ('1236', '2018-11-21 10:29:42', '1', '75', '1208');
INSERT INTO `admin_resource_role` VALUES ('1237', '2018-11-21 10:29:42', '1', '76', '1208');
INSERT INTO `admin_resource_role` VALUES ('1238', '2018-11-21 10:29:42', '1', '103', '1208');
INSERT INTO `admin_resource_role` VALUES ('1239', '2018-11-21 10:29:42', '1', '77', '1208');
INSERT INTO `admin_resource_role` VALUES ('1240', '2018-11-21 10:29:42', '1', '105', '1208');
INSERT INTO `admin_resource_role` VALUES ('1478', '2018-12-19 14:55:07', '1', '1', '1244');
INSERT INTO `admin_resource_role` VALUES ('1479', '2018-12-19 14:55:07', '1', '69', '1244');
INSERT INTO `admin_resource_role` VALUES ('1480', '2018-12-19 14:55:07', '1', '70', '1244');
INSERT INTO `admin_resource_role` VALUES ('1481', '2018-12-19 14:55:07', '1', '74', '1244');
INSERT INTO `admin_resource_role` VALUES ('1482', '2018-12-19 14:55:07', '1', '75', '1244');
INSERT INTO `admin_resource_role` VALUES ('1483', '2018-12-19 14:55:07', '1', '76', '1244');
INSERT INTO `admin_resource_role` VALUES ('1484', '2018-12-19 14:55:07', '1', '9', '1244');
INSERT INTO `admin_resource_role` VALUES ('1485', '2018-12-19 14:55:07', '1', '10', '1244');
INSERT INTO `admin_resource_role` VALUES ('1486', '2018-12-19 14:55:07', '1', '11', '1244');
INSERT INTO `admin_resource_role` VALUES ('1487', '2018-12-19 14:55:07', '1', '67', '1244');
INSERT INTO `admin_resource_role` VALUES ('1488', '2018-12-19 14:55:07', '1', '3', '1244');
INSERT INTO `admin_resource_role` VALUES ('1489', '2018-12-19 14:55:07', '1', '619', '1244');
INSERT INTO `admin_resource_role` VALUES ('1490', '2018-12-19 14:55:07', '1', '394', '1244');
INSERT INTO `admin_resource_role` VALUES ('1491', '2018-12-19 14:55:07', '1', '395', '1244');
INSERT INTO `admin_resource_role` VALUES ('1492', '2018-12-19 14:55:07', '1', '396', '1244');
INSERT INTO `admin_resource_role` VALUES ('1493', '2018-12-19 14:55:07', '1', '397', '1244');
INSERT INTO `admin_resource_role` VALUES ('1494', '2018-12-19 14:55:07', '1', '1258', '1244');
INSERT INTO `admin_resource_role` VALUES ('1495', '2018-12-19 14:55:07', '1', '398', '1244');
INSERT INTO `admin_resource_role` VALUES ('1496', '2018-12-19 14:55:07', '1', '103', '1244');
INSERT INTO `admin_resource_role` VALUES ('1497', '2018-12-19 14:55:07', '1', '77', '1244');
INSERT INTO `admin_resource_role` VALUES ('1498', '2018-12-19 14:55:07', '1', '105', '1244');
INSERT INTO `admin_resource_role` VALUES ('1499', '2018-12-19 14:55:07', '1', '106', '1244');
INSERT INTO `admin_resource_role` VALUES ('1500', '2018-12-19 14:55:07', '1', '1115', '1244');
INSERT INTO `admin_resource_role` VALUES ('1501', '2018-12-19 14:55:07', '1', '1116', '1244');
INSERT INTO `admin_resource_role` VALUES ('1502', '2018-12-19 14:55:07', '1', '108', '1244');
INSERT INTO `admin_resource_role` VALUES ('1503', '2018-12-19 14:55:07', '1', '107', '1244');
INSERT INTO `admin_resource_role` VALUES ('1545', '2018-12-21 11:25:57', '1', '1', '1296');
INSERT INTO `admin_resource_role` VALUES ('1546', '2018-12-21 11:25:57', '1', '3', '1296');
INSERT INTO `admin_resource_role` VALUES ('1547', '2018-12-21 11:25:57', '1', '619', '1296');
INSERT INTO `admin_resource_role` VALUES ('1548', '2018-12-21 11:25:57', '1', '394', '1296');
INSERT INTO `admin_resource_role` VALUES ('1549', '2018-12-21 11:25:57', '1', '395', '1296');
INSERT INTO `admin_resource_role` VALUES ('1550', '2018-12-21 11:25:57', '1', '396', '1296');
INSERT INTO `admin_resource_role` VALUES ('1551', '2018-12-21 11:25:57', '1', '397', '1296');
INSERT INTO `admin_resource_role` VALUES ('1552', '2018-12-21 11:25:57', '1', '1258', '1296');
INSERT INTO `admin_resource_role` VALUES ('1553', '2018-12-21 11:25:57', '1', '398', '1296');
INSERT INTO `admin_resource_role` VALUES ('1554', '2018-12-21 11:25:57', '1', '103', '1296');
INSERT INTO `admin_resource_role` VALUES ('1555', '2018-12-21 11:25:57', '1', '77', '1296');
INSERT INTO `admin_resource_role` VALUES ('1556', '2018-12-21 11:25:57', '1', '105', '1296');
INSERT INTO `admin_resource_role` VALUES ('1557', '2018-12-21 11:25:57', '1', '106', '1296');
INSERT INTO `admin_resource_role` VALUES ('1558', '2018-12-21 11:25:57', '1', '1115', '1296');
INSERT INTO `admin_resource_role` VALUES ('1559', '2018-12-21 11:25:57', '1', '1116', '1296');
INSERT INTO `admin_resource_role` VALUES ('1560', '2018-12-21 11:25:57', '1', '108', '1296');
INSERT INTO `admin_resource_role` VALUES ('1561', '2018-12-21 11:25:57', '1', '107', '1296');

-- ----------------------------
-- Table structure for admin_resource_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_resource_user`;
CREATE TABLE `admin_resource_user` (
  `id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `resource_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_resource_user
-- ----------------------------
INSERT INTO `admin_resource_user` VALUES ('1516', '2018-12-19 15:39:10', '1', '1', '1229');
INSERT INTO `admin_resource_user` VALUES ('1517', '2018-12-19 15:39:10', '1', '103', '1229');
INSERT INTO `admin_resource_user` VALUES ('1518', '2018-12-19 15:39:10', '1', '77', '1229');
INSERT INTO `admin_resource_user` VALUES ('1519', '2018-12-19 15:39:10', '1', '105', '1229');
INSERT INTO `admin_resource_user` VALUES ('1537', '2018-12-20 13:41:05', '1', '1', '1245');
INSERT INTO `admin_resource_user` VALUES ('1538', '2018-12-20 13:41:05', '1', '103', '1245');
INSERT INTO `admin_resource_user` VALUES ('1539', '2018-12-20 13:41:05', '1', '77', '1245');
INSERT INTO `admin_resource_user` VALUES ('1540', '2018-12-20 13:41:05', '1', '105', '1245');
INSERT INTO `admin_resource_user` VALUES ('1564', '2018-12-21 11:27:05', '1', '1', '1297');
INSERT INTO `admin_resource_user` VALUES ('1565', '2018-12-21 11:27:05', '1', '103', '1297');
INSERT INTO `admin_resource_user` VALUES ('1566', '2018-12-21 11:27:05', '1', '77', '1297');
INSERT INTO `admin_resource_user` VALUES ('1567', '2018-12-21 11:27:05', '1', '105', '1297');
INSERT INTO `admin_resource_user` VALUES ('1568', '2018-12-21 11:27:05', '1', '106', '1297');
INSERT INTO `admin_resource_user` VALUES ('1569', '2018-12-21 11:27:05', '1', '1116', '1297');
INSERT INTO `admin_resource_user` VALUES ('1570', '2018-12-21 11:27:05', '1', '108', '1297');
INSERT INTO `admin_resource_user` VALUES ('1571', '2018-12-21 11:27:05', '1', '1115', '1297');
INSERT INTO `admin_resource_user` VALUES ('1572', '2018-12-21 11:27:05', '1', '107', '1297');
INSERT INTO `admin_resource_user` VALUES ('1573', '2018-12-21 11:27:05', '1', '3', '1297');
INSERT INTO `admin_resource_user` VALUES ('1574', '2018-12-21 11:27:05', '1', '619', '1297');
INSERT INTO `admin_resource_user` VALUES ('1575', '2018-12-21 11:27:05', '1', '394', '1297');
INSERT INTO `admin_resource_user` VALUES ('1576', '2018-12-21 11:27:05', '1', '395', '1297');
INSERT INTO `admin_resource_user` VALUES ('1577', '2018-12-21 11:27:05', '1', '396', '1297');
INSERT INTO `admin_resource_user` VALUES ('1578', '2018-12-21 11:27:05', '1', '397', '1297');
INSERT INTO `admin_resource_user` VALUES ('1579', '2018-12-21 11:27:05', '1', '1258', '1297');
INSERT INTO `admin_resource_user` VALUES ('1580', '2018-12-21 11:27:05', '1', '398', '1297');

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` bigint(20) DEFAULT NULL,
  `r_name` varchar(255) DEFAULT NULL,
  `r_remark` varchar(255) DEFAULT NULL,
  `r_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES ('1207', '2018-11-21 10:11:34', '1', 'crj', '系统用户', '2');
INSERT INTO `admin_role` VALUES ('1208', '2018-11-21 10:12:41', '1', '医院', '医院角色', '0');
INSERT INTO `admin_role` VALUES ('1244', '2018-11-21 10:34:33', '1', '医院1', '医院1', '0');
INSERT INTO `admin_role` VALUES ('1296', '2018-11-22 10:10:06', '1', 'test', 'test', '0');
INSERT INTO `admin_role` VALUES ('1391', '2018-12-17 16:41:59', '1', '34', '34435', '2');
INSERT INTO `admin_role` VALUES ('1392', '2018-12-17 16:49:47', '1', 'et', 'ert', '2');
INSERT INTO `admin_role` VALUES ('1393', '2018-12-17 16:50:46', '1', '23', '34234', '2');

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `id` bigint(20) NOT NULL,
  `admin_desc` varchar(255) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `head_portrait` varchar(255) DEFAULT NULL,
  `is_admin` bit(1) DEFAULT NULL,
  `last_bind_time` datetime DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `last_wechat_token` varchar(255) DEFAULT NULL,
  `login_encry` varchar(255) DEFAULT NULL,
  `login_name` varchar(255) DEFAULT NULL,
  `login_pwd` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `open_id` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role_id` bigint(11) DEFAULT NULL,
  `u_status` int(11) DEFAULT '0',
  `create_user` bigint(20) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `delete_user_id` bigint(20) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `last_update_user_id` bigint(20) DEFAULT NULL,
  `u_address` varchar(255) DEFAULT NULL,
  `u_email` varchar(255) DEFAULT NULL,
  `u_intro` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES ('1', null, '2018-11-13 16:36:42', 'http://114.115.179.255/static_images/F52662D3217291DBBB95287F508ED0BA.png', '', null, '2018-12-24 15:43:05', null, 'jack', 'admin', '7F3DB2B6B5B2C52601CE771FCAAA2249', '管理员111', null, '18200195551', null, '0', '0', null, null, null, '0', '成都高新区', 'jack@foxmail.com', '超级管理员');
INSERT INTO `admin_user` VALUES ('1229', '医院角色', '2018-11-21 10:26:06', null, '\0', null, '2018-11-21 18:39:58', null, 'ripduj', 'crj', '5EF49A613D5F3E6511543CDAC3B01C8A', 'chenrenjie', null, '13648018271', '1208', '2', '1', '2018-12-19 15:39:36', '1', null, null, null, null, null);
INSERT INTO `admin_user` VALUES ('1245', '', '2018-11-21 10:35:08', 'http://114.115.179.255/static_images/13978AA98A75D8B4DA9CFC04CD1B35D4.png', '\0', null, '2018-11-24 14:37:10', null, 'rorwdh', 'test', 'A38BE969A9167BEBA3075BCAE172B8DF', 'test1', null, '', '1244', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `admin_user` VALUES ('1297', '测试用户', '2018-11-22 10:12:00', null, '\0', null, '2018-12-21 11:27:30', null, 'ronuuu', 'test1', 'A1EB18C9678020A11D83D1D21E726191', 'test1', null, '13553444444', '1296', '0', '1', null, null, null, null, null, null, null);
INSERT INTO `admin_user` VALUES ('1505', '', '2018-12-19 15:32:21', '', '\0', null, null, null, 'psxugo', '11', 'B88B78450F60F9BF88D82C9DAF8CBA53', '11111111111', null, '11111111111111', '1244', '0', '1', null, null, '2018-12-19 15:35:00', '1', null, null, null);

-- ----------------------------
-- Table structure for admin_user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_login_log`;
CREATE TABLE `admin_user_login_log` (
  `id` bigint(20) NOT NULL,
  `login_ip` varchar(255) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_user_login_log
-- ----------------------------
INSERT INTO `admin_user_login_log` VALUES ('1203', '192.168.16.1', '2018-11-20 17:00:22', '1');
INSERT INTO `admin_user_login_log` VALUES ('1204', '127.0.0.1', '2018-11-20 20:22:32', '1');
INSERT INTO `admin_user_login_log` VALUES ('1205', '127.0.0.1', '2018-11-21 09:54:58', '1');
INSERT INTO `admin_user_login_log` VALUES ('1206', '127.0.0.1', '2018-11-21 09:59:37', '1');
INSERT INTO `admin_user_login_log` VALUES ('1225', '127.0.0.1', '2018-11-21 10:20:56', '1');
INSERT INTO `admin_user_login_log` VALUES ('1230', '127.0.0.1', '2018-11-21 10:28:05', '1229');
INSERT INTO `admin_user_login_log` VALUES ('1231', '127.0.0.1', '2018-11-21 10:29:18', '1');
INSERT INTO `admin_user_login_log` VALUES ('1241', '127.0.0.1', '2018-11-21 10:30:11', '1229');
INSERT INTO `admin_user_login_log` VALUES ('1242', '127.0.0.1', '2018-11-21 10:33:30', '1');
INSERT INTO `admin_user_login_log` VALUES ('1243', '127.0.0.1', '2018-11-21 10:34:08', '1');
INSERT INTO `admin_user_login_log` VALUES ('1255', '127.0.0.1', '2018-11-21 10:36:32', '1245');
INSERT INTO `admin_user_login_log` VALUES ('1256', '127.0.0.1', '2018-11-21 16:04:24', '1');
INSERT INTO `admin_user_login_log` VALUES ('1257', '127.0.0.1', '2018-11-21 16:34:30', '1');
INSERT INTO `admin_user_login_log` VALUES ('1259', '127.0.0.1', '2018-11-21 16:37:06', '1');
INSERT INTO `admin_user_login_log` VALUES ('1260', '127.0.0.1', '2018-11-21 16:45:40', '1245');
INSERT INTO `admin_user_login_log` VALUES ('1261', '127.0.0.1', '2018-11-21 17:10:44', '1');
INSERT INTO `admin_user_login_log` VALUES ('1279', '127.0.0.1', '2018-11-21 18:33:09', '1');
INSERT INTO `admin_user_login_log` VALUES ('1289', '127.0.0.1', '2018-11-21 18:39:58', '1229');
INSERT INTO `admin_user_login_log` VALUES ('1290', '127.0.0.1', '2018-11-21 18:40:36', '1');
INSERT INTO `admin_user_login_log` VALUES ('1291', '127.0.0.1', '2018-11-21 18:42:04', '1245');
INSERT INTO `admin_user_login_log` VALUES ('1292', '127.0.0.1', '2018-11-22 08:44:48', '1');
INSERT INTO `admin_user_login_log` VALUES ('1293', '127.0.0.1', '2018-11-22 08:59:02', '1');
INSERT INTO `admin_user_login_log` VALUES ('1294', '127.0.0.1', '2018-11-22 09:00:47', '1');
INSERT INTO `admin_user_login_log` VALUES ('1295', '127.0.0.1', '2018-11-22 09:51:21', '1');
INSERT INTO `admin_user_login_log` VALUES ('1317', '127.0.0.1', '2018-11-22 10:31:52', '1');
INSERT INTO `admin_user_login_log` VALUES ('1318', '127.0.0.1', '2018-11-22 13:12:59', '1');
INSERT INTO `admin_user_login_log` VALUES ('1319', '127.0.0.1', '2018-11-22 13:35:38', '1');
INSERT INTO `admin_user_login_log` VALUES ('1320', '127.0.0.1', '2018-11-22 13:36:39', '1');
INSERT INTO `admin_user_login_log` VALUES ('1321', '127.0.0.1', '2018-11-22 17:02:34', '1');
INSERT INTO `admin_user_login_log` VALUES ('1322', '127.0.0.1', '2018-11-22 19:13:11', '1');
INSERT INTO `admin_user_login_log` VALUES ('1323', '127.0.0.1', '2018-11-22 19:24:47', '1');
INSERT INTO `admin_user_login_log` VALUES ('1324', '127.0.0.1', '2018-11-22 19:25:32', '1');
INSERT INTO `admin_user_login_log` VALUES ('1325', '127.0.0.1', '2018-11-22 19:26:03', '1');
INSERT INTO `admin_user_login_log` VALUES ('1336', '127.0.0.1', '2018-11-22 20:00:20', '1');
INSERT INTO `admin_user_login_log` VALUES ('1337', '127.0.0.1', '2018-11-22 21:28:49', '1');
INSERT INTO `admin_user_login_log` VALUES ('1338', '127.0.0.1', '2018-11-23 08:46:45', '1');
INSERT INTO `admin_user_login_log` VALUES ('1339', '127.0.0.1', '2018-11-23 10:05:21', '1');
INSERT INTO `admin_user_login_log` VALUES ('1340', '127.0.0.1', '2018-11-23 10:34:31', '1');
INSERT INTO `admin_user_login_log` VALUES ('1341', '127.0.0.1', '2018-11-23 13:01:02', '1');
INSERT INTO `admin_user_login_log` VALUES ('1342', '127.0.0.1', '2018-11-23 13:29:08', '1');
INSERT INTO `admin_user_login_log` VALUES ('1343', '127.0.0.1', '2018-11-23 13:30:23', '1');
INSERT INTO `admin_user_login_log` VALUES ('1344', '127.0.0.1', '2018-11-23 13:52:18', '1');
INSERT INTO `admin_user_login_log` VALUES ('1345', '127.0.0.1', '2018-11-23 15:59:54', '1');
INSERT INTO `admin_user_login_log` VALUES ('1346', '127.0.0.1', '2018-11-23 19:57:54', '1');
INSERT INTO `admin_user_login_log` VALUES ('1347', '127.0.0.1', '2018-11-24 14:34:03', '1');
INSERT INTO `admin_user_login_log` VALUES ('1348', '127.0.0.1', '2018-11-24 14:37:10', '1245');
INSERT INTO `admin_user_login_log` VALUES ('1349', '127.0.0.1', '2018-11-24 15:12:48', '1');
INSERT INTO `admin_user_login_log` VALUES ('1350', '127.0.0.1', '2018-11-27 10:54:30', '1');
INSERT INTO `admin_user_login_log` VALUES ('1351', '127.0.0.1', '2018-11-27 13:07:02', '1');
INSERT INTO `admin_user_login_log` VALUES ('1352', '127.0.0.1', '2018-11-27 15:58:07', '1');
INSERT INTO `admin_user_login_log` VALUES ('1353', '127.0.0.1', '2018-11-27 15:59:11', '1');
INSERT INTO `admin_user_login_log` VALUES ('1354', '127.0.0.1', '2018-11-27 15:59:26', '1');
INSERT INTO `admin_user_login_log` VALUES ('1355', '127.0.0.1', '2018-11-27 16:05:26', '1');
INSERT INTO `admin_user_login_log` VALUES ('1356', '127.0.0.1', '2018-11-27 16:05:44', '1');
INSERT INTO `admin_user_login_log` VALUES ('1357', '127.0.0.1', '2018-11-27 16:06:34', '1');
INSERT INTO `admin_user_login_log` VALUES ('1358', '127.0.0.1', '2018-11-27 16:06:46', '1');
INSERT INTO `admin_user_login_log` VALUES ('1359', '127.0.0.1', '2018-11-27 16:07:14', '1');
INSERT INTO `admin_user_login_log` VALUES ('1360', '127.0.0.1', '2018-11-27 17:02:05', '1');
INSERT INTO `admin_user_login_log` VALUES ('1361', '127.0.0.1', '2018-12-13 13:46:23', '1');
INSERT INTO `admin_user_login_log` VALUES ('1362', '127.0.0.1', '2018-12-13 13:55:30', '1');
INSERT INTO `admin_user_login_log` VALUES ('1363', '127.0.0.1', '2018-12-13 14:31:36', '1');
INSERT INTO `admin_user_login_log` VALUES ('1364', '127.0.0.1', '2018-12-13 14:35:11', '1');
INSERT INTO `admin_user_login_log` VALUES ('1365', '127.0.0.1', '2018-12-13 14:37:02', '1');
INSERT INTO `admin_user_login_log` VALUES ('1366', '127.0.0.1', '2018-12-13 14:48:41', '1');
INSERT INTO `admin_user_login_log` VALUES ('1367', '127.0.0.1', '2018-12-13 15:01:22', '1');
INSERT INTO `admin_user_login_log` VALUES ('1368', '127.0.0.1', '2018-12-13 15:06:38', '1');
INSERT INTO `admin_user_login_log` VALUES ('1369', '127.0.0.1', '2018-12-13 15:08:32', '1');
INSERT INTO `admin_user_login_log` VALUES ('1370', '127.0.0.1', '2018-12-13 15:12:19', '1');
INSERT INTO `admin_user_login_log` VALUES ('1371', '127.0.0.1', '2018-12-13 15:38:23', '1');
INSERT INTO `admin_user_login_log` VALUES ('1372', '127.0.0.1', '2018-12-13 15:42:15', '1');
INSERT INTO `admin_user_login_log` VALUES ('1373', '127.0.0.1', '2018-12-13 15:43:42', '1');
INSERT INTO `admin_user_login_log` VALUES ('1374', '127.0.0.1', '2018-12-13 15:44:21', '1');
INSERT INTO `admin_user_login_log` VALUES ('1375', '127.0.0.1', '2018-12-13 15:46:57', '1');
INSERT INTO `admin_user_login_log` VALUES ('1376', '127.0.0.1', '2018-12-13 15:48:02', '1');
INSERT INTO `admin_user_login_log` VALUES ('1377', '127.0.0.1', '2018-12-13 15:51:36', '1');
INSERT INTO `admin_user_login_log` VALUES ('1378', '127.0.0.1', '2018-12-13 15:55:52', '1');
INSERT INTO `admin_user_login_log` VALUES ('1379', '127.0.0.1', '2018-12-13 16:26:47', '1');
INSERT INTO `admin_user_login_log` VALUES ('1380', '127.0.0.1', '2018-12-14 09:38:31', '1');
INSERT INTO `admin_user_login_log` VALUES ('1381', '127.0.0.1', '2018-12-14 14:14:02', '1');
INSERT INTO `admin_user_login_log` VALUES ('1382', '127.0.0.1', '2018-12-14 14:24:49', '1');
INSERT INTO `admin_user_login_log` VALUES ('1383', '127.0.0.1', '2018-12-14 14:34:54', '1');
INSERT INTO `admin_user_login_log` VALUES ('1384', '127.0.0.1', '2018-12-14 15:56:11', '1');
INSERT INTO `admin_user_login_log` VALUES ('1385', '127.0.0.1', '2018-12-16 15:17:44', '1');
INSERT INTO `admin_user_login_log` VALUES ('1386', '127.0.0.1', '2018-12-17 11:22:17', '1');
INSERT INTO `admin_user_login_log` VALUES ('1387', '127.0.0.1', '2018-12-17 12:42:46', '1');
INSERT INTO `admin_user_login_log` VALUES ('1388', '127.0.0.1', '2018-12-17 14:22:44', '1');
INSERT INTO `admin_user_login_log` VALUES ('1389', '127.0.0.1', '2018-12-17 14:55:23', '1');
INSERT INTO `admin_user_login_log` VALUES ('1390', '127.0.0.1', '2018-12-17 16:24:47', '1');
INSERT INTO `admin_user_login_log` VALUES ('1394', '127.0.0.1', '2018-12-18 15:28:42', '1');
INSERT INTO `admin_user_login_log` VALUES ('1395', '127.0.0.1', '2018-12-19 09:14:33', '1');
INSERT INTO `admin_user_login_log` VALUES ('1396', '127.0.0.1', '2018-12-19 13:12:44', '1');
INSERT INTO `admin_user_login_log` VALUES ('1504', '127.0.0.1', '2018-12-19 15:07:05', '1');
INSERT INTO `admin_user_login_log` VALUES ('1522', '127.0.0.1', '2018-12-20 09:07:18', '1');
INSERT INTO `admin_user_login_log` VALUES ('1526', '127.0.0.1', '2018-12-20 11:09:27', '1');
INSERT INTO `admin_user_login_log` VALUES ('1530', '127.0.0.1', '2018-12-20 12:40:40', '1');
INSERT INTO `admin_user_login_log` VALUES ('1533', '127.0.0.1', '2018-12-20 12:57:26', '1');
INSERT INTO `admin_user_login_log` VALUES ('1534', '127.0.0.1', '2018-12-20 13:08:30', '1');
INSERT INTO `admin_user_login_log` VALUES ('1535', '127.0.0.1', '2018-12-20 13:09:01', '1');
INSERT INTO `admin_user_login_log` VALUES ('1536', '127.0.0.1', '2018-12-20 13:09:51', '1');
INSERT INTO `admin_user_login_log` VALUES ('1541', '127.0.0.1', '2018-12-20 13:42:17', '1');
INSERT INTO `admin_user_login_log` VALUES ('1542', '127.0.0.1', '2018-12-20 13:51:58', '1');
INSERT INTO `admin_user_login_log` VALUES ('1543', '127.0.0.1', '2018-12-21 10:11:49', '1');
INSERT INTO `admin_user_login_log` VALUES ('1544', '127.0.0.1', '2018-12-21 11:25:20', '1');
INSERT INTO `admin_user_login_log` VALUES ('1562', '127.0.0.1', '2018-12-21 11:26:31', '1297');
INSERT INTO `admin_user_login_log` VALUES ('1563', '127.0.0.1', '2018-12-21 11:26:47', '1');
INSERT INTO `admin_user_login_log` VALUES ('1581', '127.0.0.1', '2018-12-21 11:27:30', '1297');
INSERT INTO `admin_user_login_log` VALUES ('1582', '127.0.0.1', '2018-12-21 11:28:18', '1');
INSERT INTO `admin_user_login_log` VALUES ('1583', '127.0.0.1', '2018-12-21 13:29:54', '1');
INSERT INTO `admin_user_login_log` VALUES ('1584', '127.0.0.1', '2018-12-21 13:56:22', '1');
INSERT INTO `admin_user_login_log` VALUES ('1591', '127.0.0.1', '2018-12-21 14:57:39', '1');
INSERT INTO `admin_user_login_log` VALUES ('1597', '127.0.0.1', '2018-12-24 09:18:00', '1');
INSERT INTO `admin_user_login_log` VALUES ('1600', '127.0.0.1', '2018-12-24 15:43:05', '1');

-- ----------------------------
-- Table structure for admin_user_status_record
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_status_record`;
CREATE TABLE `admin_user_status_record` (
  `id` bigint(20) NOT NULL,
  `admin_user_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` bigint(20) DEFAULT NULL,
  `new_status` int(11) DEFAULT NULL,
  `old_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_user_status_record
-- ----------------------------
INSERT INTO `admin_user_status_record` VALUES ('1227', '1226', '2018-11-21 10:23:44', '1', '2', '0');
INSERT INTO `admin_user_status_record` VALUES ('1228', '1209', '2018-11-21 10:23:54', '1', '2', '0');
INSERT INTO `admin_user_status_record` VALUES ('1506', '1505', '2018-12-19 15:36:01', '1', '1', '0');
INSERT INTO `admin_user_status_record` VALUES ('1507', '1505', '2018-12-19 15:36:10', '1', '0', '1');
INSERT INTO `admin_user_status_record` VALUES ('1520', '1229', '2018-12-19 15:39:35', '1', '2', '0');

-- ----------------------------
-- Table structure for biz_equipment
-- ----------------------------
DROP TABLE IF EXISTS `biz_equipment`;
CREATE TABLE `biz_equipment` (
  `id` bigint(20) NOT NULL,
  `e_address` varchar(255) DEFAULT NULL,
  `e_city_money` decimal(19,2) DEFAULT NULL,
  `e_city_user_id` bigint(20) DEFAULT NULL,
  `e_county_money` decimal(19,2) DEFAULT NULL,
  `e_county_user_id` bigint(20) DEFAULT NULL,
  `e_create_time` datetime DEFAULT NULL,
  `e_create_user_id` bigint(20) DEFAULT NULL,
  `e_delete_time` datetime DEFAULT NULL,
  `e_delete_user_id` bigint(20) DEFAULT NULL,
  `e_latitude` decimal(19,2) DEFAULT NULL,
  `e_least_money` decimal(19,2) DEFAULT NULL,
  `e_login_user_id` bigint(20) DEFAULT NULL,
  `e_longitude` decimal(19,2) DEFAULT NULL,
  `e_max_electric` decimal(19,2) DEFAULT NULL,
  `e_name` varchar(255) DEFAULT NULL,
  `e_no` varchar(255) DEFAULT NULL,
  `e_price` decimal(19,2) DEFAULT NULL,
  `e_remark` varchar(255) DEFAULT NULL,
  `e_scan_qr_code` varchar(255) DEFAULT NULL,
  `e_status` int(11) DEFAULT NULL,
  `e_terrace_money` decimal(19,2) DEFAULT NULL,
  `e_under_money` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_equipment
-- ----------------------------

-- ----------------------------
-- Table structure for biz_equipment_charge_record
-- ----------------------------
DROP TABLE IF EXISTS `biz_equipment_charge_record`;
CREATE TABLE `biz_equipment_charge_record` (
  `id` bigint(20) NOT NULL,
  `e_city_money` decimal(19,2) DEFAULT NULL,
  `e_city_user_id` bigint(20) DEFAULT NULL,
  `e_county_money` decimal(19,2) DEFAULT NULL,
  `e_county_user_id` bigint(20) DEFAULT NULL,
  `e_end_number` decimal(19,2) DEFAULT NULL,
  `e_end_time` datetime DEFAULT NULL,
  `e_equipment_id` bigint(20) DEFAULT NULL,
  `e_money` varchar(255) DEFAULT NULL,
  `e_number` decimal(19,2) DEFAULT NULL,
  `e_price` decimal(19,2) DEFAULT NULL,
  `e_start_number` decimal(19,2) DEFAULT NULL,
  `e_start_time` datetime DEFAULT NULL,
  `e_status` int(11) DEFAULT NULL,
  `e_terrace_money` decimal(19,2) DEFAULT NULL,
  `e_under_money` decimal(19,2) DEFAULT NULL,
  `e_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_equipment_charge_record
-- ----------------------------

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES ('1611');
INSERT INTO `hibernate_sequence` VALUES ('1611');
INSERT INTO `hibernate_sequence` VALUES ('1611');
INSERT INTO `hibernate_sequence` VALUES ('1611');
INSERT INTO `hibernate_sequence` VALUES ('1611');
INSERT INTO `hibernate_sequence` VALUES ('1611');
INSERT INTO `hibernate_sequence` VALUES ('1611');
INSERT INTO `hibernate_sequence` VALUES ('1611');

-- ----------------------------
-- Table structure for login_user
-- ----------------------------
DROP TABLE IF EXISTS `login_user`;
CREATE TABLE `login_user` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `alipay_open_id` varchar(255) DEFAULT NULL,
  `certificate_no` varchar(255) DEFAULT NULL,
  `certificate_type` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `l_name` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `l_phone` varchar(255) DEFAULT NULL,
  `profile_img` varchar(255) DEFAULT NULL,
  `l_sex` varchar(255) DEFAULT NULL,
  `l_status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `wx_open_id` varchar(255) DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `user_type` int(11) DEFAULT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `bank_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login_user
-- ----------------------------
INSERT INTO `login_user` VALUES ('1', '成都武侯区', '451', '556151', '1', '2018-12-21 15:44:36', 'test', '测试', '18200000000', 'http://t2.hddhhn.com/uploads/tu/20150700/v45jx3rpefz.jpg', '1', '0', '2018-12-24 10:27:28', '5561', null, '0', null, null);
INSERT INTO `login_user` VALUES ('1599', '234', null, '234', '1', '2018-12-24 10:49:50', '34', '234', '18300000000', null, '0', '0', '2018-12-24 15:54:13', null, null, '2', '34', '234');

-- ----------------------------
-- Table structure for login_user_status_record
-- ----------------------------
DROP TABLE IF EXISTS `login_user_status_record`;
CREATE TABLE `login_user_status_record` (
  `id` bigint(20) NOT NULL,
  `admin_user_id` bigint(20) DEFAULT NULL,
  `login_user_id` bigint(20) DEFAULT NULL,
  `l_old_status` int(11) DEFAULT NULL,
  `l_status` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login_user_status_record
-- ----------------------------
INSERT INTO `login_user_status_record` VALUES ('1609', '1', '1599', '0', '1', '2018-12-24 16:20:10');
INSERT INTO `login_user_status_record` VALUES ('1610', '1', '1599', '1', '0', '2018-12-24 16:20:17');

-- ----------------------------
-- Table structure for sys_address
-- ----------------------------
DROP TABLE IF EXISTS `sys_address`;
CREATE TABLE `sys_address` (
  `a_code` varchar(255) NOT NULL,
  `a_code_acronym` varchar(255) DEFAULT NULL,
  `a_countryside` varchar(255) DEFAULT NULL,
  `a_level` int(11) DEFAULT NULL,
  `a_name` varchar(255) DEFAULT NULL,
  `parent_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`a_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_address
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `id` bigint(20) NOT NULL,
  `d_en_text` varchar(255) DEFAULT NULL,
  `d_parent_token` varchar(255) DEFAULT NULL,
  `d_sort` int(11) DEFAULT NULL,
  `d_status` int(11) DEFAULT NULL,
  `d_text` varchar(255) DEFAULT NULL,
  `d_token` varchar(255) DEFAULT NULL,
  `d_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------
INSERT INTO `sys_dictionary` VALUES ('70', 'sex', '0', '1', '0', '性别', 'DX001', '0');
INSERT INTO `sys_dictionary` VALUES ('71', 'man', 'DX001', '1', '0', '男', 'DX0011', '1');
INSERT INTO `sys_dictionary` VALUES ('72', 'woman', 'DX001', '2', '0', '女', 'DX0012', '0');
INSERT INTO `sys_dictionary` VALUES ('1592', 'certificate type', '0', '2', '0', '证件类型', 'DX002', '0');
INSERT INTO `sys_dictionary` VALUES ('1593', 'identity card', 'DX002', '1', '0', '身份证', 'DX0021', '1');

-- ----------------------------
-- Table structure for sys_serial_number
-- ----------------------------
DROP TABLE IF EXISTS `sys_serial_number`;
CREATE TABLE `sys_serial_number` (
  `id` bigint(20) NOT NULL,
  `f_column_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_serial_number
-- ----------------------------
