/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : music-website

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 30/03/2024 14:59:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xs_album
-- ----------------------------
DROP TABLE IF EXISTS `xs_album`;
CREATE TABLE `xs_album`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '专辑名称',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专辑图片url',
  `source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源',
  `source_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `source`(`source`, `source_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1047 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xs_album_music
-- ----------------------------
DROP TABLE IF EXISTS `xs_album_music`;
CREATE TABLE `xs_album_music`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `album` int(0) NULL DEFAULT NULL COMMENT '专辑id',
  `music` int(0) NULL DEFAULT NULL COMMENT '歌曲id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_album_music`(`album`, `music`) USING BTREE,
  INDEX `musics`(`music`) USING BTREE,
  CONSTRAINT `albums` FOREIGN KEY (`album`) REFERENCES `xs_album` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `musics` FOREIGN KEY (`music`) REFERENCES `xs_music` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1254 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xs_album_singer
-- ----------------------------
DROP TABLE IF EXISTS `xs_album_singer`;
CREATE TABLE `xs_album_singer`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `album` int(0) NULL DEFAULT NULL,
  `singer` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `album`(`album`, `singer`) USING BTREE,
  INDEX `singer`(`singer`) USING BTREE,
  CONSTRAINT `xs_album_singer_ibfk_1` FOREIGN KEY (`album`) REFERENCES `xs_album` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `xs_album_singer_ibfk_2` FOREIGN KEY (`singer`) REFERENCES `xs_singer` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1564 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xs_favorites
-- ----------------------------
DROP TABLE IF EXISTS `xs_favorites`;
CREATE TABLE `xs_favorites`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL,
  `fav_type` enum('album','sheet','singer') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `fav_id` int(0) NOT NULL,
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `userid`(`user_id`, `fav_type`, `fav_id`) USING BTREE,
  CONSTRAINT `userid` FOREIGN KEY (`user_id`) REFERENCES `xs_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xs_history
-- ----------------------------
DROP TABLE IF EXISTS `xs_history`;
CREATE TABLE `xs_history`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL,
  `his_type` enum('album','sheet','singer','music') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `his_id` int(0) NOT NULL,
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `userid`(`user_id`, `his_type`, `his_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xs_music
-- ----------------------------
DROP TABLE IF EXISTS `xs_music`;
CREATE TABLE `xs_music`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '歌曲名',
  `album` int(0) NULL DEFAULT NULL COMMENT '专辑ID',
  `lyric` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '歌词',
  `source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源，此歌曲的来源，比如链接，还是其他网站的ID',
  `source_data` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源数据，里面的内容和source相关。',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `source`(`source`, `source_data`) USING BTREE,
  INDEX `album`(`album`) USING BTREE,
  CONSTRAINT `album` FOREIGN KEY (`album`) REFERENCES `xs_album` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1256 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xs_sheet
-- ----------------------------
DROP TABLE IF EXISTS `xs_sheet`;
CREATE TABLE `xs_sheet`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '歌单名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '歌单详情(描述)',
  `user` int(0) NULL DEFAULT NULL COMMENT '创建者',
  `img_url` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片url',
  `source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '歌曲来源',
  `source_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '歌曲来源id',
  `is_public` tinyint(0) NULL DEFAULT NULL COMMENT '是否公开',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `source`(`source`, `source_id`) USING BTREE,
  INDEX `user`(`user`) USING BTREE,
  CONSTRAINT `xs_sheet_ibfk_1` FOREIGN KEY (`user`) REFERENCES `xs_user` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xs_sheet_music
-- ----------------------------
DROP TABLE IF EXISTS `xs_sheet_music`;
CREATE TABLE `xs_sheet_music`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `sheet` int(0) NULL DEFAULT NULL COMMENT '歌单ID',
  `music` int(0) NULL DEFAULT NULL COMMENT '歌单所关联的歌曲。',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_sheet_music`(`sheet`, `music`) USING BTREE,
  INDEX `music`(`music`) USING BTREE,
  CONSTRAINT `music` FOREIGN KEY (`music`) REFERENCES `xs_music` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `sheet` FOREIGN KEY (`sheet`) REFERENCES `xs_sheet` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1248 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xs_singer
-- ----------------------------
DROP TABLE IF EXISTS `xs_singer`;
CREATE TABLE `xs_singer`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '歌手名字，设置索引限制不允许重复的目的是防止采集重复（忽略掉重复歌手名字这种情况。）',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '歌手信息(描述)',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '歌手图片',
  `img1v1_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '歌手1比1图片',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_name`(`name`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 810 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xs_singer_music
-- ----------------------------
DROP TABLE IF EXISTS `xs_singer_music`;
CREATE TABLE `xs_singer_music`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `singer` int(0) NULL DEFAULT NULL COMMENT '歌手',
  `music` int(0) NULL DEFAULT NULL COMMENT '音乐',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `singer`(`singer`, `music`) USING BTREE,
  INDEX `music`(`music`) USING BTREE,
  CONSTRAINT `xs_singer_music_ibfk_1` FOREIGN KEY (`singer`) REFERENCES `xs_singer` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `xs_singer_music_ibfk_2` FOREIGN KEY (`music`) REFERENCES `xs_music` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1564 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xs_user
-- ----------------------------
DROP TABLE IF EXISTS `xs_user`;
CREATE TABLE `xs_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名，用于显示',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码(md5算法)',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像链接',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '用户信息',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮件',
  `state` enum('NO_VERIFICATION_EMAIL','NORMAL_ACCOUNT','ACCOUNT_BANNED') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'NO_VERIFICATION_EMAIL' COMMENT '邮件是否验证状态：\r\n0、未验证\r\n1、已验证\r\n2、被禁用',
  `liked` int(0) NULL DEFAULT NULL COMMENT '已点赞歌曲',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE COMMENT 'email不能重复',
  UNIQUE INDEX `username`(`username`) USING BTREE COMMENT '账号不能重复',
  INDEX `liked`(`liked`) USING BTREE,
  CONSTRAINT `liked` FOREIGN KEY (`liked`) REFERENCES `xs_sheet` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
