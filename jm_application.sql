/*
Navicat MySQL Data Transfer

Source Server         : 114.115.179.255
Source Server Version : 50528
Source Host           : 114.115.179.255:3306
Source Database       : jm_application

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-11-20 17:01:01
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
INSERT INTO `admin_resource` VALUES ('3', '', 'menu-icon fa fa-users', '用户管理', '1', '3', '', '用户管理', '0', '/webuser', '0');
INSERT INTO `admin_resource` VALUES ('4', 'updateInfo', 'ace-icon fa fa-pencil bigger-120', '修改', '2', '2', 'btn  btn-info', '修改', '1', '/company/update', '0');
INSERT INTO `admin_resource` VALUES ('5', 'deleteInfo', 'ace-icon fa fa-trash-o bigger-120', '删除', '2', '6', 'btn  btn-danger', '删除', '1', '/company/delete', '0');
INSERT INTO `admin_resource` VALUES ('6', 'blockInfo', 'ace-icon fa fa-info-circle bigger-120', '停用', '2', '5', 'btn btn-warning', '停用', '1', '/company/block', '0');
INSERT INTO `admin_resource` VALUES ('7', 'success', 'ace-icon fa fa-check bigger-120', '启用', '2', '3', 'btn btn-success', '启用', '1', '/company/success', '0');
INSERT INTO `admin_resource` VALUES ('8', 'saveInfo', 'ace-icon glyphicon glyphicon-plus bigger-120', '新增', '2', '1', 'btn btn-success', '新增', '1', '/company/save', '0');
INSERT INTO `admin_resource` VALUES ('9', '', 'menu-icon fa fa-coffee', '资源管理', '69', '3', '', '资源管理', '0', '/resource', '0');
INSERT INTO `admin_resource` VALUES ('10', 'addInfo', 'ace-icon glyphicon glyphicon-plus bigger-120', '新增', '9', '2', 'btn btn-success', '新增', '1', '/resource/save', '0');
INSERT INTO `admin_resource` VALUES ('11', 'editInfo', 'ace-icon fa fa-pencil bigger-120', '修改', '9', '3', 'btn btn-info', '修改', '1', '/resource/update', '0');
INSERT INTO `admin_resource` VALUES ('67', 'deleteInfo', 'ace-icon fa fa-trash-o bigger-120', '删除资源信息', '9', '4', 'btn btn-danger', '删除', '1', '/resource/delete', '0');
INSERT INTO `admin_resource` VALUES ('69', '', 'menu-icon fa fa-tag', '系统维护', '1', '1', '', '系统维护', '0', '', '0');
INSERT INTO `admin_resource` VALUES ('70', '', 'menu-icon fa fa-list-alt', '字典管理', '69', '1', '', '字典管理', '0', '/dictionary', '0');
INSERT INTO `admin_resource` VALUES ('74', 'addInfo', 'ace-icon glyphicon glyphicon-plus bigger-120', '新增', '70', '1', 'btn btn-success', '新增', '1', '/dictionary/save', '0');
INSERT INTO `admin_resource` VALUES ('75', 'editInfo', 'ace-icon fa fa-pencil bigger-120', '修改', '70', '2', 'btn btn-info', '修改', '1', '/dictionary/update', '0');
INSERT INTO `admin_resource` VALUES ('76', 'deleteInfo', 'ace-icon fa fa-trash-o bigger-120', '删除', '70', '3', 'btn btn-danger', '删除', '1', '/dictionary/delete', '0');
INSERT INTO `admin_resource` VALUES ('77', 'query', 'glyphicon glyphicon-search', '角色查询', '103', '0', 'btn btn-primary', '查询', '1', '/role/list', null);
INSERT INTO `admin_resource` VALUES ('103', '', 'menu-icon fa fa-cogs', '角色管理', '1', '3', '', '角色管理', '0', '/role', '0');
INSERT INTO `admin_resource` VALUES ('105', 'addInfo', 'ace-icon glyphicon glyphicon-plus bigger-120', '新增', '103', '1', 'btn btn-success', '新增', '1', '/role/save', '0');
INSERT INTO `admin_resource` VALUES ('106', 'editInfo', 'ace-icon fa fa-pencil bigger-120', '修改', '103', '2', 'btn btn-info', '修改', '1', '/role/update', '0');
INSERT INTO `admin_resource` VALUES ('107', 'deleteInfo', 'ace-icon fa fa-trash-o bigger-120', '删除角色', '103', '4', 'btn btn-danger', '删除', '1', '/role/delete', '0');
INSERT INTO `admin_resource` VALUES ('108', 'resourceClick', 'ace-icon fa fa-cogs bigger-120', '资源配置', '103', '3', 'btn btn-warning', '资源配置', '1', '/role/resource', '0');
INSERT INTO `admin_resource` VALUES ('112', 'resourceInfo', 'ace-icon fa fa-cogs bigger-120', '资源配置', '2', '4', 'btn btn-warning', '资源配置', '1', '/company/resource', '0');
INSERT INTO `admin_resource` VALUES ('394', 'saveInfo', 'ace-icon glyphicon glyphicon-plus bigger-120', '新增', '3', '2', 'btn btn-success', '新增', '1', '/webuser/save', null);
INSERT INTO `admin_resource` VALUES ('395', 'updateInfo', 'ace-icon fa fa-pencil bigger-120', '编辑', '3', '2', 'btn btn-info', '编辑', '1', '/webuser/update', '0');
INSERT INTO `admin_resource` VALUES ('396', 'success', 'ace-icon fa fa-check bigger-120', '启用', '3', '3', 'btn  btn-success', '启用', '1', '/webuser/success', '0');
INSERT INTO `admin_resource` VALUES ('397', 'blockInfo', 'ace-icon fa fa-info-circle bigger-120', '停用', '3', '4', 'btn btn-warning', '停用', '1', '/webuser/block', '0');
INSERT INTO `admin_resource` VALUES ('398', 'deleteInfo', 'ace-icon fa fa-trash-o bigger-120', '删除', '3', '5', 'btn btn-danger', '删除', '1', '/webuser/delete', '0');
INSERT INTO `admin_resource` VALUES ('617', 'saveInfo', 'ace-icon glyphicon glyphicon-plus bigger-120', '新增客户', '615', '1', 'btn btn-success', '新增客户', '1', '/customer/save', '0');
INSERT INTO `admin_resource` VALUES ('618', 'editInfo', 'ace-icon fa fa-pencil bigger-120', '编辑', '615', '2', 'btn btn-info', '编辑', '1', '/customer/update', '0');
INSERT INTO `admin_resource` VALUES ('619', 'query', 'glyphicon glyphicon-search', '查询', '3', '1', 'btn btn-primary', '查询', '1', '/webuser/list', '0');
INSERT INTO `admin_resource` VALUES ('1115', 'success', 'fa fa-check bigger-120', '启用', '103', '3', 'btn  btn-success', '启用', '1', '/role/success', null);
INSERT INTO `admin_resource` VALUES ('1116', 'blockInfo', 'fa fa-info-circle bigger-120', '停用', '103', '3', 'btn btn-warning', '停用', '1', '/role/block', null);

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES ('1', null, '2018-11-13 16:36:42', null, '', null, '2018-11-20 17:00:22', null, 'jack', 'admin', 'F75C16C62166E263E5F8D84881C15DE9', '管理员', null, null, null, '0', '0', null, null, null, '0');

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
INSERT INTO `hibernate_sequence` VALUES ('1204');
INSERT INTO `hibernate_sequence` VALUES ('1204');
INSERT INTO `hibernate_sequence` VALUES ('1204');
INSERT INTO `hibernate_sequence` VALUES ('1204');
INSERT INTO `hibernate_sequence` VALUES ('1204');
INSERT INTO `hibernate_sequence` VALUES ('1204');
INSERT INTO `hibernate_sequence` VALUES ('1204');
INSERT INTO `hibernate_sequence` VALUES ('1204');

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
