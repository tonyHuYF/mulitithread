/*
 Navicat Premium Data Transfer

 Source Server         : mysql--家里的数据库
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : 192.168.1.5:3306
 Source Schema         : company_space

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 07/08/2021 10:53:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_file
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file`  (
  `file_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `file_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件相对地址',
  `file_suffix` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件相对地址',
  `file_size` int NULL DEFAULT 0 COMMENT '文件大小，单位KB',
  `relation_type` tinyint NULL DEFAULT 0 COMMENT '文件类型，1：合同附件，2：合同依据材料，3：履约保障信息，4：发票，5：合同模板文件，6：合同审查意见汇总表，7：合同盖章电子版，8：通用审批导出',
  `relation_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联ID，例如合同ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` tinyint NULL DEFAULT 0 COMMENT '数据状态，0：正常，-1：删除',
  `source_status` tinyint NULL DEFAULT 0 COMMENT '数据来源，1：融通',
  PRIMARY KEY (`file_id`) USING BTREE,
  UNIQUE INDEX `file_id`(`file_id`) USING BTREE COMMENT '不允许重复',
  INDEX `relation_id`(`relation_id`, `relation_type`, `file_name`) USING BTREE COMMENT '关联Id的联合索引'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
