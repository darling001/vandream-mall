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

insert  into `ucm_menu`(`menu_id`,`name`,`parent_id`,`url`,`order`,`org_id`,`book_id`,`group_id`,`create_user_id`,`create_user_name`,`create_time`,`modify_time`,`modify_user_id`,`modify_user_name`) values
('5924f9596f0011e896da525400c89a91','需求管理','5924fdf16f0011e896da525400c89a91','setting/buyer',1,'0','0','0',NULL,NULL,'2018-06-08 10:14:50','2018-06-08 10:14:50',NULL,NULL),
('5924fdf16f0011e896da525400c89a91','需方中心','0','setting/buyer',1,'0','0','0',NULL,NULL,'2018-06-13 16:14:16','2018-06-13 16:14:16',NULL,NULL),
('5924ff746f0011e896da525400c89a91','账户中心','0','setting/accountSetting',2,'0','0','0',NULL,NULL,'2018-06-13 16:14:53','2018-06-13 16:14:53',NULL,NULL),
('592500776f0011e896da525400c89a91','认证信息','5924ff746f0011e896da525400c89a91','setting/accountSetting',1,'0','0','0',NULL,NULL,'2018-06-13 16:15:22','2018-06-13 16:15:22',NULL,NULL),
('592502336f0011e896da525400c89a91','收货地址','5924ff746f0011e896da525400c89a91','setting/accountSetting/shippingAddress',2,'0','0','0',NULL,NULL,'2018-06-13 16:15:47','2018-06-13 16:15:47',NULL,NULL),
('5925057e6f0011e896da525400c89a91','供方中心','0','setting/supplier',1,'0','0','0',NULL,NULL,'2018-06-13 16:20:49','2018-06-13 16:20:49',NULL,NULL),
('5925066a6f0011e896da525400c89a91','方案管理','5925057e6f0011e896da525400c89a91','setting/supplier',1,'0','0','0',NULL,NULL,'2018-06-13 16:21:05','2018-06-13 16:21:05',NULL,NULL),
('592507606f0011e896da525400c89a91','锁货通知','5925057e6f0011e896da525400c89a91','setting/supplier/lockGood',2,'0','0','0',NULL,NULL,'2018-06-13 16:21:30','2018-06-13 16:21:30',NULL,NULL),
('592508456f0011e896da525400c89a91','订单执行','5925057e6f0011e896da525400c89a91','setting/supplier/orderExecute',3,'0','0','0',NULL,NULL,'2018-06-13 16:21:59','2018-06-13 16:21:59',NULL,NULL),
('592509356f0011e896da525400c89a91','发货管理','5925057e6f0011e896da525400c89a91','setting/supplier/deliveryManagement',4,'0','0','0',NULL,NULL,'2018-06-13 16:22:30','2018-06-13 16:22:30',NULL,NULL),
('59250ad56f0011e896da525400c89a91','子账号管理','5924ff746f0011e896da525400c89a91','setting/accountSetting/subaccount',3,'0','0','0',NULL,NULL,'2018-06-13 16:26:04','2018-06-13 16:26:04',NULL,NULL),
('59250bd76f0011e896da525400c89a91','发布需求','5924f9596f0011e896da525400c89a91','setting/buyer',1,'0','0','0',NULL,NULL,'2018-06-13 16:11:08','2018-06-13 16:11:08',NULL,NULL),
('59250cc06f0011e896da525400c89a91','需求记录','5924f9596f0011e896da525400c89a91','setting/buyer/requirement',2,'0','0','0',NULL,NULL,'2018-06-13 16:11:59','2018-06-13 16:11:59',NULL,NULL),
('59250da66f0011e896da525400c89a91','订单执行','5924fdf16f0011e896da525400c89a91','setting/buyer/contract',2,'0','0','0',NULL,NULL,'2018-06-13 16:12:33','2018-06-13 16:12:33',NULL,NULL);

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

