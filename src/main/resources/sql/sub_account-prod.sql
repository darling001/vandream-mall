/*
Navicat MySQL Data Transfer
Source Server         : 本地连接
Source Server Version : 50620
Source Host           : localhost:3306
Source Database       : jrbac
Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001
Date: 2016-11-24 19:39:36
*/

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `ucm_menu`;
CREATE TABLE `ucm_menu` (
  `menu_id` varchar(32) NOT NULL COMMENT '主键id',
  `name` varchar(64) NOT NULL COMMENT '菜单名称',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父功能id',
  `url` varchar(500) DEFAULT NULL COMMENT '访问地址',
  `order` tinyint(4) DEFAULT 0  COMMENT '菜单顺序',
  `org_id`   VARCHAR(40)  DEFAULT 1021 COMMENT '组织机构ID',
  `book_id`  VARCHAR(40)  DEFAULT 1000 COMMENT '账套ID',
  `group_id` VARCHAR(40)  DEFAULT 1000 COMMENT '集团ID',
  `create_user_id` varchar(40) null comment '创建人',
	`create_user_name` varchar(40) null comment '创建人姓名',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
	`modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP comment '修改时间',
	`modify_user_id` varchar(40) null comment '修改人',
	`modify_user_name` varchar(40) null comment '修改人姓名',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';


-- ----------------------------
-- Table structure for ucm_role
-- ----------------------------
DROP TABLE IF EXISTS `ucm_role`;
CREATE TABLE `ucm_role` (
  `role_id` varchar(32) NOT NULL COMMENT '主键id',
  `role_name` varchar(64) NOT NULL COMMENT '角色名称',
  `role_type`  tinyint(4) DEFAULT 1 COMMENT '1-模板角色，2-自定义角色',
  `role_flag`   tinyint(4) DEFAULT 1 COMMENT '1-供方角色，2-需方角色',
  `org_id`   VARCHAR(40)  DEFAULT 1021 COMMENT '组织机构ID',
  `book_id`  VARCHAR(40)  DEFAULT 1000 COMMENT '账套ID',
  `group_id` VARCHAR(40)  DEFAULT 1000 COMMENT '集团ID',
  `create_user_id` varchar(40) null comment '创建人',
	`create_user_name` varchar(40) null comment '创建人姓名',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
	`modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP comment '修改时间',
	`modify_user_id` varchar(40) null comment '修改人',
	`modify_user_name` varchar(40) null comment '修改人姓名',
	PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';


-- ----------------------------
-- Table structure for ucm_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `ucm_role_menu`;
CREATE TABLE `ucm_role_menu` (
  `role_menu_id` VARCHAR(32),
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `menu_id` varchar(32) NOT NULL COMMENT '菜单id',
  `root_menu_id` VARCHAR(32) NOT NULL COMMENT  '一級菜单ID',
  `org_id`   VARCHAR(40)  DEFAULT 1021 COMMENT '组织机构ID',
  `book_id`  VARCHAR(40)  DEFAULT 1000 COMMENT '账套ID',
  `group_id` VARCHAR(40)  DEFAULT 1000 COMMENT '集团ID',
  `create_user_id` varchar(40) null comment '创建人',
	`create_user_name` varchar(40) null comment '创建人姓名',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
	`modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP comment '修改时间',
	`modify_user_id` varchar(40) null comment '修改人',
	`modify_user_name` varchar(40) null comment '修改人姓名',


  KEY `idx_role_menu` (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单表';


-- ----------------------------
-- Table structure for ucm_account_role
-- ----------------------------
DROP TABLE IF EXISTS `ucm_account_role`;
CREATE TABLE `ucm_account_role` (
  `account_role_id` VARCHAR(32),
  `account_id` varchar(32) NOT NULL COMMENT '账户id',
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `org_id`   VARCHAR(40)  DEFAULT 1021 COMMENT '组织机构ID',
  `book_id`  VARCHAR(40)  DEFAULT 1000 COMMENT '账套ID',
  `group_id` VARCHAR(40)  DEFAULT 1000 COMMENT '集团ID',
  `create_user_id` varchar(40) null comment '创建人',
	`create_user_name` varchar(40) null comment '创建人姓名',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
	`modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP comment '修改时间',
	`modify_user_id` varchar(40) null comment '修改人',
	`modify_user_name` varchar(40) null comment '修改人姓名',
   KEY `idx_account_role` (`account_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户角色表';

