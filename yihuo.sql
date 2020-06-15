/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : yihuo

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 03/06/2020 12:51:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_address
-- ----------------------------
DROP TABLE IF EXISTS `tb_address`;
CREATE TABLE `tb_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '地址id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '收货人姓名',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '收货人电话',
  `zip_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮编',
  `state` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '省份',
  `city` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '市',
  `district` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '区/县',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '详细地址',
  `default_address` tinyint(1) DEFAULT NULL COMMENT '1：默认地址  0：非默认地址',
  `label` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '地址标签',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `userId` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_address
-- ----------------------------
BEGIN;
INSERT INTO `tb_address` VALUES (1, 1, '起名字真难', '18377898557', '547600', '广西壮族自治区', '河池市', '凤山县', '广西河池市凤山县', 0, '学校');
COMMIT;

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `content` varchar(1024) NOT NULL,
  `reply_to` varchar(64) DEFAULT NULL,
  `goods_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `username` varchar(55) DEFAULT NULL,
  `head` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd01gnchhuj06oumqxrikccc3r` (`goods_id`),
  KEY `FKswirics8hhydki5ff0emtmbii` (`user_id`),
  CONSTRAINT `FKd01gnchhuj06oumqxrikccc3r` FOREIGN KEY (`goods_id`) REFERENCES `tb_goods` (`id`),
  CONSTRAINT `FKswirics8hhydki5ff0emtmbii` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------
BEGIN;
INSERT INTO `tb_comment` VALUES (7, '2020-06-02 23:07:20', '2020-06-02 23:07:20', '你好', NULL, 24, 1, '起名字真难', 'http://image.yihuo.com/group1/M00/00/03/wKhQqF7WUUKAd1DmAAITIhER6XU69.jpeg');
INSERT INTO `tb_comment` VALUES (8, '2020-06-02 23:10:01', '2020-06-02 23:10:01', '回复：“你好”<br>回复：起名字真难:在的', '起名字真难', 24, 1, '起名字真难', 'http://image.yihuo.com/group1/M00/00/03/wKhQqF7WUUKAd1DmAAITIhER6XU69.jpeg');
COMMIT;

-- ----------------------------
-- Table structure for tb_goods
-- ----------------------------
DROP TABLE IF EXISTS `tb_goods`;
CREATE TABLE `tb_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `buy_price` bigint(11) DEFAULT NULL,
  `detail` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `title` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `image` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `recommend` int(11) DEFAULT NULL,
  `sell_price` bigint(11) DEFAULT NULL,
  `saleable` tinyint(11) DEFAULT NULL,
  `cid` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `view_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjtyl6pmb9j4aj64sm54xi1hbi` (`cid`),
  KEY `FKf68a0a9u8u8hqckg0ycnjarv6` (`user_id`),
  CONSTRAINT `FKf68a0a9u8u8hqckg0ycnjarv6` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FKjtyl6pmb9j4aj64sm54xi1hbi` FOREIGN KEY (`cid`) REFERENCES `tb_goods_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_goods
-- ----------------------------
BEGIN;
INSERT INTO `tb_goods` VALUES (2, '2020-06-02 08:20:42', '2020-06-02 08:20:42', 600000, '99新 不错哦', 1, '三星s10', 'http://image.yihuo.com/group1/M00/00/03/wKhQqF7WUhKAPROHAAFuRq80bVs692.jpg', 0, 400000, 1, 4, 1, 0);
INSERT INTO `tb_goods` VALUES (3, '2020-06-02 08:30:17', '2020-06-02 08:30:17', 600000, '物美价廉 值得拥有', 1, '11pro', 'http://image.yihuo.com/group1/M00/00/03/wKhQqF7WVEqAI0ZRAAH-Q9kw2As633.jpg', 0, 450000, 1, 4, 1, 0);
INSERT INTO `tb_goods` VALUES (4, '2020-06-02 08:31:45', '2020-06-02 08:31:45', 300000, '95新 电池有点low了', 1, '华为荣耀8', 'http://image.yihuo.com/group1/M00/00/03/wKhQqF7WVIyAJPzIAAJo2cN-fqw327.jpg', 0, 200000, 1, 4, 1, 0);
INSERT INTO `tb_goods` VALUES (5, '2020-06-02 08:32:29', '2020-06-02 08:32:29', 1500000, '98新 电池有点low了', 1, 'macbook pro', 'http://image.yihuo.com/group1/M00/00/03/wKhQqF7WVNGAO0kjAADZ_uDjm3o296.jpg', 0, 1000000, 1, 9, 1, 0);
INSERT INTO `tb_goods` VALUES (6, '2020-06-02 08:33:54', '2020-06-02 08:33:54', 600000, '98新 轻微划痕 正常使用痕迹', 1, 'xs 256G', 'http://image.yihuo.com/group1/M00/00/03/wKhQqF7WVRuAF1sZAAEaVNncKBU572.jpg', 0, 500000, 1, 4, 1, 0);
INSERT INTO `tb_goods` VALUES (7, '2020-06-02 08:36:12', '2020-06-02 08:36:12', 8000, '只试穿 便宜出了', 1, '牛仔裤 九分裤', 'http://image.yihuo.com/group1/M00/00/03/wKhQqF7WVZ6ASk32AAEIqwDoe_s882.jpg', 0, 5000, 1, 32, 1, 0);
INSERT INTO `tb_goods` VALUES (8, '2020-06-02 08:37:23', '2020-06-02 08:37:23', 5000, '很好看的小说 值得一看', 1, '福尔摩斯探案全集', 'http://image.yihuo.com/group1/M00/00/03/wKhQqF7WVeyAKyqpAABh0ArRh1g616.jpg', 0, 4000, 1, 45, 1, 0);
INSERT INTO `tb_goods` VALUES (9, '2020-06-02 08:38:28', '2020-06-02 08:38:28', 12000, '半途而废 便宜出了', 1, '考研数学资料', 'http://image.yihuo.com/group1/M00/00/03/wKhQqF7WVjeAdyBkAACVDYF0ix8110.jpg', 0, 4000, 1, 44, 1, 0);
INSERT INTO `tb_goods` VALUES (10, '2020-06-02 08:39:49', '2020-06-02 08:39:49', 20000, '工作忙无暇打球 篮球爱好者福利到了', 1, '篮球', 'http://image.yihuo.com/group1/M00/00/03/wKhQqF7WVmeADFJWAACHuf8AiGo866.jpg', 0, 10000, 1, 26, 1, 0);
INSERT INTO `tb_goods` VALUES (11, '2020-06-02 08:40:36', '2020-06-02 08:40:36', 50000, '毕业了 留给学弟学妹了', 1, '二手自行车', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WVq2ACHQwAACmwCD2rM8049.jpg', 0, 20000, 1, 47, 1, 0);
INSERT INTO `tb_goods` VALUES (12, '2020-06-02 08:41:44', '2020-06-02 08:41:44', 20000, '只穿过几次 ', 1, '粉红的鞋', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WVuGAIp3PAACdnvgIVYE599.jpg', 0, 10000, 1, 35, 1, 0);
INSERT INTO `tb_goods` VALUES (13, '2020-06-02 08:42:57', '2020-06-02 08:42:57', 4000, '卖给废品觉得可惜了 有学弟学妹买么', 1, '高数教材', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WVzOAd25xAAC3l0grwHY175.jpg', 0, 1000, 1, 43, 1, 0);
INSERT INTO `tb_goods` VALUES (14, '2020-06-02 08:45:12', '2020-06-02 08:45:12', 4000, '只用过几次', 1, '加湿器', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WV7qAGqZ5AAAYYMOCPCI694.jpg', 0, 1000, 1, 55, 1, 0);
INSERT INTO `tb_goods` VALUES (15, '2020-06-02 08:46:25', '2020-06-02 08:46:25', 300000, '屏下指纹 666', 1, '手机', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WV--Ac1feAAAetxLvn4A463.jpg', 0, 200000, 1, 6, 1, 0);
INSERT INTO `tb_goods` VALUES (16, '2020-06-02 08:47:06', '2020-06-02 08:47:06', 300000, '可以当作备用机', 1, 'MI手机', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WWDeAQeGTAAATpwH64vg207.jpg', 0, 200000, 1, 4, 1, 0);
INSERT INTO `tb_goods` VALUES (17, '2020-06-02 08:47:59', '2020-06-02 08:47:59', 200000, '休闲娱乐 90新', 1, 'IQ手机', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WWGaAaq4yAAAe_pKhTFU288.jpg', 0, 100000, 1, 4, 1, 0);
INSERT INTO `tb_goods` VALUES (18, '2020-06-02 08:48:38', '2020-06-02 08:48:38', 500000, '性能强悍 王者荣耀流畅 90新', 1, '8p', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WWJiAbtEKAAAeUbZhiPk587.jpg', 0, 400000, 1, 4, 1, 0);
INSERT INTO `tb_goods` VALUES (19, '2020-06-02 08:49:53', '2020-06-02 08:49:53', 3000, '闲暇看书也不错 99新 ', 1, '小说/课外书', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WWMGAcpuAAAAf1VXi3NI436.jpg', 0, 2000, 1, 45, 1, 0);
INSERT INTO `tb_goods` VALUES (20, '2020-06-02 08:50:58', '2020-06-02 08:50:58', 10000, '便宜出了 没翻过几页', 1, '张宇数学', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WWRmAfDYcAAApFN3fR9I292.jpg', 0, 5000, 1, 44, 1, 0);
INSERT INTO `tb_goods` VALUES (21, '2020-06-02 08:51:25', '2020-06-02 08:51:25', 10000, '便宜出了 没翻过几页', 1, '李永乐数学', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WWU6AZ1NNAAA6-qpQK3w833.jpg', 0, 5000, 1, 44, 1, 0);
INSERT INTO `tb_goods` VALUES (22, '2020-06-02 08:51:49', '2020-06-02 08:51:49', 10000, '便宜出了 没翻过几页', 1, '高等数学教材', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WWWOAXuX7AAATmI0ymT0519.jpg', 0, 5000, 1, 43, 1, 0);
INSERT INTO `tb_goods` VALUES (23, '2020-06-02 08:52:29', '2020-06-02 08:52:29', 30000, '便宜出了 没打几次', 1, '质量刚刚的篮球', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WWX2AXm2CAAAjffanWVY970.jpg', 0, 20000, 1, 26, 1, 0);
INSERT INTO `tb_goods` VALUES (24, '2020-06-02 08:53:06', '2020-06-02 08:53:06', 30000, '便宜出了 没打几次', 1, '一副乒乓球', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WWaGAE6TEAAASvQBstlI016.jpg', 0, 20000, 1, 29, 1, 0);
INSERT INTO `tb_goods` VALUES (25, '2020-06-02 08:54:04', '2020-06-02 08:54:04', 10000, '便宜出了 没用过几次', 1, '保温杯', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WWcqAHLE3AAAQhQ8UaIU243.jpg', 0, 6000, 1, 55, 1, 0);
INSERT INTO `tb_goods` VALUES (26, '2020-06-02 08:56:04', '2020-06-02 08:56:04', 4000, '照过几次面 没有裂痕', 1, '镜子', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WWkyAN4MsAAAQRb3Oklo545.jpg', 0, 3000, 1, 39, 1, 0);
INSERT INTO `tb_goods` VALUES (27, '2020-06-02 08:57:17', '2020-06-02 08:57:17', 8000, '照过几次面 没有裂痕', 1, '全身镜子', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WWq-AZRxlAAAR_OCFEFU176.jpg', 0, 5000, 1, 39, 1, 0);
INSERT INTO `tb_goods` VALUES (28, '2020-06-02 08:58:11', '2020-06-02 08:58:11', 8000, '用过几次 90新', 1, '茶壶', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WWteAQh9HAAAi7DoTJ6Y234.jpg', 0, 5000, 1, 39, 1, 0);
INSERT INTO `tb_goods` VALUES (29, '2020-06-02 08:59:08', '2020-06-02 08:59:08', 8000, '用过几次 90新', 1, '好伞', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WWv2AUWJzAAAYx2SUGCA370.jpg', 0, 5000, 1, 34, 1, 0);
INSERT INTO `tb_goods` VALUES (30, '2020-06-02 09:00:22', '2020-06-02 09:00:22', 8000, '用过几次 95新', 1, '吉他', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WWzOAepOFAAARhkHSa6s036.jpg', 0, 5000, 1, 38, 1, 0);
INSERT INTO `tb_goods` VALUES (31, '2020-06-02 09:01:10', '2020-06-02 09:01:10', 8000, '吹过几次 95新', 1, '葫芦丝', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WW4qAfp5XAAAes9FnB2s239.jpg', 0, 5000, 1, 38, 1, 0);
INSERT INTO `tb_goods` VALUES (32, '2020-06-02 09:01:40', '2020-06-02 09:01:40', 10000, '吹过几次 99新', 1, '口琴', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WW7WAbwvgAAAZHSgJRXU794.jpg', 0, 5000, 1, 38, 1, 0);
INSERT INTO `tb_goods` VALUES (33, '2020-06-02 09:02:28', '2020-06-02 09:02:28', 10000, '用过几次 99新', 1, '挂架', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WW86Afh_8AAAmKt7Q2iM630.jpg', 0, 5000, 1, 39, 1, 0);
INSERT INTO `tb_goods` VALUES (34, '2020-06-02 09:03:22', '2020-06-02 09:03:22', 20000, '用过几次 99新', 1, '书桌', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WW_2ANArbAAAalc5Bdi0762.jpg', 0, 10000, 1, 39, 1, 0);
INSERT INTO `tb_goods` VALUES (35, '2020-06-02 09:04:28', '2020-06-02 09:04:28', 20000, '用过几次 95新', 1, 'type_c数据线', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WXECAM_xFAAezk1o9Msc237.png', 0, 10000, 1, 5, 1, 0);
INSERT INTO `tb_goods` VALUES (36, '2020-06-02 09:04:52', '2020-06-02 09:04:52', 20000, '只用过几次 95新', 1, '耳机', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WXHKALKI8AAMRqp4cRnE358.jpg', 0, 10000, 1, 16, 1, 0);
INSERT INTO `tb_goods` VALUES (37, '2020-06-02 09:05:14', '2020-06-02 09:05:14', 100000, '只用过几次 98新', 1, '蓝牙耳机', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WXIqAUTHkAACsayHY1_k915.jpg', 0, 50000, 1, 16, 1, 0);
INSERT INTO `tb_goods` VALUES (38, '2020-06-02 09:08:15', '2020-06-02 09:08:15', 30000, '耐用 功能多 只用过几次', 1, '充电宝', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WXR6AH5BxAAF9rq07Iho618.jpg', 0, 20000, 1, 6, 1, 0);
INSERT INTO `tb_goods` VALUES (39, '2020-06-02 09:09:00', '2020-06-02 09:09:00', 20000, '耐用 功能多 只用过几次', 1, '充电宝', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WXVeAZE5MAAD5gKH12U4314.jpg', 0, 10000, 1, 5, 1, 0);
INSERT INTO `tb_goods` VALUES (40, '2020-06-02 09:21:40', '2020-06-02 09:21:40', 600000, '耐用 功能多 性能强 只用过几次', 1, '游戏本', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WYFCAatxzAAKnX01t09M732.jpg', 0, 500000, 1, 9, 1, 0);
INSERT INTO `tb_goods` VALUES (41, '2020-06-02 09:21:58', '2020-06-02 09:21:58', 600000, '耐用 功能多 性能强 只用过几次', 1, '台式机', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WYHqAXjpfAAfE4Hqknq4268.jpg', 0, 500000, 1, 10, 1, 0);
INSERT INTO `tb_goods` VALUES (42, '2020-06-02 09:25:25', '2020-06-02 09:25:25', 200000, '耐用 彰显风度 95新', 1, '手表', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WYTqADrdsAAaP8O9pxUw782.jpg', 0, 180000, 1, 54, 1, 0);
INSERT INTO `tb_goods` VALUES (43, '2020-06-02 09:25:53', '2020-06-02 09:25:53', 20000, '耐用 好看 95新', 1, '背包', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WYVqARJG1AANRcldHyuU702.jpg', 0, 15000, 1, 33, 1, 0);
INSERT INTO `tb_goods` VALUES (44, '2020-06-02 09:26:33', '2020-06-02 09:26:33', 20000, '时尚潮流 95新 穿过几次', 1, '帆布鞋', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WYXaANTZTAAWz65hG2ws995.jpg', 0, 15000, 1, 35, 1, 0);
INSERT INTO `tb_goods` VALUES (45, '2020-06-02 09:27:12', '2020-06-02 09:27:12', 30000, '时尚潮流 轻盈舒适 95新 穿过几次', 1, 'NB运动鞋', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WYaKAPFNoAAJurI9ikyI788.jpg', 0, 25000, 1, 35, 1, 0);
INSERT INTO `tb_goods` VALUES (46, '2020-06-02 09:27:44', '2020-06-02 09:27:44', 20000, ' 轻盈舒适 95新 穿过几次', 1, '休闲高帮鞋', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WYcSAZAhjAANCzogsqbY118.jpg', 0, 16000, 1, 35, 1, 0);
INSERT INTO `tb_goods` VALUES (47, '2020-06-02 09:28:23', '2020-06-02 09:28:23', 20000, ' 轻盈舒适 95新背过几次', 1, 'mini背包', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WYeiAUHTuAAQ3aij_JM0038.jpg', 0, 16000, 1, 33, 1, 0);
INSERT INTO `tb_goods` VALUES (48, '2020-06-02 09:29:33', '2020-06-02 09:29:33', 50000, '装x必备 95新戴过几次', 1, '手表', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WYi2AMlA_AAR_Bogiu0Y465.jpg', 0, 40000, 1, 54, 1, 0);
INSERT INTO `tb_goods` VALUES (49, '2020-06-02 09:30:28', '2020-06-02 09:30:28', 50000, '游戏必备 助你5杀 95新用过几次', 1, '罗技鼠标', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WYlGAEOz8AAIRUh6hvME878.png', 0, 40000, 1, 20, 1, 0);
INSERT INTO `tb_goods` VALUES (50, '2020-06-02 09:32:49', '2020-06-02 09:32:49', 40000, '游戏必备 好用哦 只用过几次', 1, '机械键盘', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WYu2APvcRAAGqxFTcADc772.jpg', 0, 30000, 1, 19, 1, 0);
INSERT INTO `tb_goods` VALUES (51, '2020-06-02 09:34:01', '2020-06-02 09:34:01', 40000, '分辨率高 护眼', 1, '24寸显示器', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WYy6AW0vVAAK-RYnjzzs341.jpg', 0, 30000, 1, 24, 1, 0);
INSERT INTO `tb_goods` VALUES (52, '2020-06-02 09:34:43', '2020-06-02 09:34:43', 10000, '穿过几次 95新', 1, '凉鞋', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WY2qAaue0AAFWjrJv41A231.jpg', 0, 5000, 1, 35, 1, 0);
INSERT INTO `tb_goods` VALUES (53, '2020-06-02 09:35:27', '2020-06-02 09:35:27', 30000, '穿用过几次 95新', 1, '罗技鼠标', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WY4yAYi_YAAMSMN0bW_U507.jpg', 0, 20000, 1, 20, 1, 0);
INSERT INTO `tb_goods` VALUES (54, '2020-06-02 09:36:01', '2020-06-02 09:36:01', 30000, '用过几次 95新 体验很好哦', 1, '很漂亮的显示器', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WY7WADVntAAO2vlJ_wgk064.jpg', 0, 20000, 1, 24, 1, 0);
INSERT INTO `tb_goods` VALUES (55, '2020-06-02 09:36:36', '2020-06-02 09:36:36', 15000, '用过几次 95新 体验很好哦', 1, '普通键盘', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WY9qAbbBrAAafg8Z6fPU281.jpg', 0, 10000, 1, 19, 1, 0);
INSERT INTO `tb_goods` VALUES (56, '2020-06-02 09:36:54', '2020-06-02 09:36:54', 15000, '用过几次 95新 体验很好哦', 1, '背包', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WY_mAc2bZAAClN9XozQ8489.jpg', 0, 10000, 1, 33, 1, 0);
INSERT INTO `tb_goods` VALUES (57, '2020-06-02 09:37:50', '2020-06-02 09:37:50', 15000, '舒适保暖 95新 体验很好哦', 1, '精品上衣', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WZCiABYzJAAjK_srrqbw327.png', 0, 10000, 1, 31, 1, 0);
INSERT INTO `tb_goods` VALUES (58, '2020-06-02 09:38:19', '2020-06-02 09:38:19', 15000, '透气清凉 95新 体验很好哦', 1, '精品下装', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WZEOAG0-_AAJxAAQyW1Y594.jpg', 0, 10000, 1, 32, 1, 0);
INSERT INTO `tb_goods` VALUES (59, '2020-06-02 09:38:50', '2020-06-02 09:38:50', 15000, '透气清凉 95新 ', 1, 'T恤', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WZF-AaBxVAALoBHGsiws017.jpg', 0, 12000, 1, 31, 1, 0);
INSERT INTO `tb_goods` VALUES (60, '2020-06-02 09:39:13', '2020-06-02 09:39:13', 15000, '透气清凉 95新 ', 1, '情侣T恤', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WZISAMvpAAAIxkfcO31o837.jpg', 0, 13000, 1, 31, 1, 0);
INSERT INTO `tb_goods` VALUES (61, '2020-06-02 09:39:32', '2020-06-02 09:39:32', 15000, '透气清凉 95新 ', 1, '蓝色短袖', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WZJmAP1Z2AARY7ecBr7E420.jpg', 0, 13000, 1, 31, 1, 0);
INSERT INTO `tb_goods` VALUES (62, '2020-06-02 09:39:46', '2020-06-02 09:39:46', 15000, '透气清凉 95新 ', 1, '黑色短袖', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WZKiAXCoUAAF2RJ0-Ja0813.jpg', 0, 11000, 1, 31, 1, 0);
INSERT INTO `tb_goods` VALUES (63, '2020-06-02 09:40:13', '2020-06-02 09:40:13', 15000, '透气清凉 95新 ', 1, '夏季7分裤', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WZLaAaE5PAAOVhbG6nZY816.jpg', 0, 11000, 1, 32, 1, 0);
INSERT INTO `tb_goods` VALUES (64, '2020-06-02 09:40:54', '2020-06-02 09:40:54', 15000, '弹性十足 95新 ', 1, '运动裤', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WZOSATbAdAANA4LBCwGQ183.jpg', 0, 11000, 1, 32, 1, 0);
INSERT INTO `tb_goods` VALUES (65, '2020-06-02 09:43:34', '2020-06-02 09:43:34', 20000, '容量大 速度快 95新 ', 1, '机械硬盘', 'http://image.yihuo.com/group1/M00/00/04/wKhQqF7WZXmATBX-AACAAPcqr6I162.jpg', 0, 15000, 1, 22, 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for tb_goods_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_goods_category`;
CREATE TABLE `tb_goods_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `name` varchar(18) NOT NULL,
  `sort` int(11) NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `key_parent_id` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_goods_category
-- ----------------------------
BEGIN;
INSERT INTO `tb_goods_category` VALUES (1, '2020-04-02 20:20:05', '2020-04-02 21:35:15', '手机', 0, 0);
INSERT INTO `tb_goods_category` VALUES (4, '2020-04-02 20:26:45', '2020-04-02 21:35:54', '智能机', 1, 1);
INSERT INTO `tb_goods_category` VALUES (5, '2020-04-02 21:13:04', '2020-04-02 21:36:24', '手机配件', 2, 1);
INSERT INTO `tb_goods_category` VALUES (6, '2020-04-02 21:37:05', '2020-04-02 21:37:05', '充电宝', 3, 1);
INSERT INTO `tb_goods_category` VALUES (7, '2020-04-02 21:37:23', '2020-04-02 21:37:23', '翻盖机', 4, 1);
INSERT INTO `tb_goods_category` VALUES (8, '2020-04-02 21:37:50', '2020-04-02 21:37:50', '电脑', 5, 0);
INSERT INTO `tb_goods_category` VALUES (9, '2020-04-02 21:38:08', '2020-04-02 21:38:08', '笔记本', 6, 8);
INSERT INTO `tb_goods_category` VALUES (10, '2020-04-02 21:38:26', '2020-04-02 21:38:26', '台式机', 7, 8);
INSERT INTO `tb_goods_category` VALUES (11, '2020-04-02 21:38:45', '2020-04-02 21:38:45', '平板', 8, 8);
INSERT INTO `tb_goods_category` VALUES (15, '2020-04-05 18:46:54', '2020-04-05 19:07:07', '影音娱乐', 9, 0);
INSERT INTO `tb_goods_category` VALUES (16, '2020-04-05 19:06:56', '2020-04-05 19:07:20', '耳机', 10, 15);
INSERT INTO `tb_goods_category` VALUES (17, '2020-04-05 19:07:45', '2020-04-05 19:07:45', 'MP3/MP4', 11, 15);
INSERT INTO `tb_goods_category` VALUES (18, '2020-04-05 19:08:15', '2020-04-05 19:08:15', '游戏机', 12, 15);
INSERT INTO `tb_goods_category` VALUES (19, '2020-04-05 19:08:44', '2020-04-05 19:08:44', '键盘', 13, 15);
INSERT INTO `tb_goods_category` VALUES (20, '2020-04-05 19:09:04', '2020-04-05 19:09:04', '鼠标', 14, 15);
INSERT INTO `tb_goods_category` VALUES (21, '2020-04-05 19:10:14', '2020-04-05 19:10:14', '数码配件', 15, 0);
INSERT INTO `tb_goods_category` VALUES (22, '2020-04-05 19:10:45', '2020-04-05 19:10:45', '移动硬盘', 16, 21);
INSERT INTO `tb_goods_category` VALUES (23, '2020-04-05 19:11:08', '2020-04-05 19:11:08', '相机', 17, 21);
INSERT INTO `tb_goods_category` VALUES (24, '2020-04-05 19:11:25', '2020-04-05 19:11:25', '显示器', 18, 21);
INSERT INTO `tb_goods_category` VALUES (25, '2020-04-05 19:11:50', '2020-04-05 19:11:50', '运动健身', 19, 0);
INSERT INTO `tb_goods_category` VALUES (26, '2020-04-05 19:12:13', '2020-04-05 19:12:13', '篮球', 20, 25);
INSERT INTO `tb_goods_category` VALUES (27, '2020-04-05 19:12:34', '2020-04-05 19:12:34', '足球', 21, 25);
INSERT INTO `tb_goods_category` VALUES (28, '2020-04-05 19:12:53', '2020-04-05 19:12:53', '羽毛球', 22, 25);
INSERT INTO `tb_goods_category` VALUES (29, '2020-04-05 19:13:11', '2020-04-05 19:13:11', '球拍', 23, 25);
INSERT INTO `tb_goods_category` VALUES (30, '2020-04-05 19:13:50', '2020-04-05 19:13:50', '衣物鞋帽', 24, 0);
INSERT INTO `tb_goods_category` VALUES (31, '2020-04-05 19:14:15', '2020-04-05 19:14:15', '上衣', 25, 30);
INSERT INTO `tb_goods_category` VALUES (32, '2020-04-05 19:14:30', '2020-04-05 19:14:30', '裤子', 26, 30);
INSERT INTO `tb_goods_category` VALUES (33, '2020-04-05 19:14:48', '2020-04-05 19:14:48', '背包', 27, 30);
INSERT INTO `tb_goods_category` VALUES (34, '2020-04-05 19:15:12', '2020-04-05 19:15:12', '雨伞', 28, 30);
INSERT INTO `tb_goods_category` VALUES (35, '2020-04-05 19:15:29', '2020-04-05 19:15:29', '鞋子', 29, 30);
INSERT INTO `tb_goods_category` VALUES (36, '2020-04-05 19:15:55', '2020-04-05 19:15:55', '配饰', 30, 30);
INSERT INTO `tb_goods_category` VALUES (37, '2020-04-05 19:16:38', '2020-04-05 19:16:38', '生活娱乐', 31, 0);
INSERT INTO `tb_goods_category` VALUES (38, '2020-04-05 19:17:05', '2020-04-05 19:17:05', '乐器', 32, 37);
INSERT INTO `tb_goods_category` VALUES (39, '2020-04-05 19:17:22', '2020-04-05 19:17:22', '生活用品', 33, 37);
INSERT INTO `tb_goods_category` VALUES (40, '2020-04-05 19:17:39', '2020-04-05 19:17:39', '会员卡', 34, 37);
INSERT INTO `tb_goods_category` VALUES (41, '2020-04-05 19:18:00', '2020-04-05 19:18:00', '化妆品', 35, 37);
INSERT INTO `tb_goods_category` VALUES (42, '2020-04-05 19:18:31', '2020-04-05 19:18:31', '图书教材', 36, 0);
INSERT INTO `tb_goods_category` VALUES (43, '2020-04-05 19:18:49', '2020-04-05 19:18:49', '教材', 37, 42);
INSERT INTO `tb_goods_category` VALUES (44, '2020-04-05 19:19:05', '2020-04-05 19:19:05', '考研资料', 38, 42);
INSERT INTO `tb_goods_category` VALUES (45, '2020-04-05 19:19:24', '2020-04-05 19:19:24', '课外书', 39, 42);
INSERT INTO `tb_goods_category` VALUES (46, '2020-04-05 19:19:54', '2020-04-05 19:19:54', '交通出行', 40, 0);
INSERT INTO `tb_goods_category` VALUES (47, '2020-04-05 19:20:18', '2020-04-05 19:20:18', '自行车', 41, 46);
INSERT INTO `tb_goods_category` VALUES (48, '2020-04-05 19:20:35', '2020-04-05 19:20:35', '电动车', 42, 46);
INSERT INTO `tb_goods_category` VALUES (49, '2020-04-05 19:21:01', '2020-04-05 19:21:01', '交通卡', 43, 46);
INSERT INTO `tb_goods_category` VALUES (50, '2020-04-05 19:21:29', '2020-04-05 19:21:29', '个人技能', 44, 0);
INSERT INTO `tb_goods_category` VALUES (51, '2020-04-05 19:21:47', '2020-04-05 19:21:47', '摄影', 45, 50);
INSERT INTO `tb_goods_category` VALUES (52, '2020-04-05 19:22:05', '2020-04-05 19:22:05', '绘画', 46, 50);
INSERT INTO `tb_goods_category` VALUES (53, '2020-04-05 19:22:29', '2020-04-05 19:22:29', '其他', 46, 0);
INSERT INTO `tb_goods_category` VALUES (54, '2020-04-11 21:19:25', '2020-04-11 21:19:40', '手表', 47, 53);
INSERT INTO `tb_goods_category` VALUES (55, '2020-04-12 22:31:58', '2020-04-12 22:32:08', '其他小类', 48, 53);
COMMIT;

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `total_pay` bigint(20) NOT NULL COMMENT '总金额，单位为分',
  `actual_pay` bigint(20) NOT NULL COMMENT '实付金额。单位:分。如:20007，表示:200元7分',
  `promotion_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '',
  `payment_type` tinyint(1) unsigned zerofill NOT NULL COMMENT '支付类型，1、在线支付，2、货到付款',
  `post_fee` bigint(20) NOT NULL COMMENT '邮费。单位:分。如:20007，表示:200元7分',
  `create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `shipping_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '物流名称',
  `shipping_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '物流单号',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `buyer_message` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '买家留言',
  `buyer_nick` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '买家昵称',
  `buyer_rate` tinyint(1) DEFAULT NULL COMMENT '买家是否已经评价,0未评价，1已评价',
  `receiver_state` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '收获地址（省）',
  `receiver_city` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '收获地址（市）',
  `receiver_district` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '收获地址（区/县）',
  `receiver_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '收获地址（街道、住址等详细地址）',
  `receiver_mobile` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '收货人手机',
  `receiver_zip` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '收货人邮编',
  `receiver` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '收货人',
  `invoice_type` int(1) DEFAULT '0' COMMENT '发票类型(0无发票1普通发票，2电子发票，3增值税发票)',
  `source_type` int(1) DEFAULT '2' COMMENT '订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端',
  PRIMARY KEY (`order_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `buyer_nick` (`buyer_nick`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_order
-- ----------------------------
BEGIN;
INSERT INTO `tb_order` VALUES (1267809750557528064, 400000, 400000, '', 1, 0, '2020-06-02 08:26:14', NULL, NULL, '1', NULL, '起名字真难', 0, '广西壮族自治区', '河池市', '凤山县', '广西河池市凤山县', '18377898557', '547600', '起名字真难', 0, 2);
INSERT INTO `tb_order` VALUES (1268040849770549248, 1000000, 1000000, '', 1, 0, '2020-06-02 23:44:32', NULL, NULL, '1', NULL, '起名字真难', 0, '广西壮族自治区', '河池市', '凤山县', '广西河池市凤山县', '18377898557', '547600', '起名字真难', 0, 2);
COMMIT;

-- ----------------------------
-- Table structure for tb_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_detail`;
CREATE TABLE `tb_order_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单详情id ',
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `sku_id` bigint(20) NOT NULL COMMENT 'sku商品id',
  `num` int(11) NOT NULL COMMENT '购买数量',
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品标题',
  `own_spec` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '商品动态属性键值集',
  `price` bigint(20) NOT NULL COMMENT '价格,单位：分',
  `image` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '商品图片',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `key_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='订单详情表';

-- ----------------------------
-- Records of tb_order_detail
-- ----------------------------
BEGIN;
INSERT INTO `tb_order_detail` VALUES (1, 1267809750557528064, 2, 1, '三星s10', '\"99新 不错哦\"', 400000, 'http://image.yihuo.com/group1/M00/00/03/wKhQqF7WUhKAPROHAAFuRq80bVs692.jpg');
INSERT INTO `tb_order_detail` VALUES (2, 1268040849770549248, 5, 1, 'macbook pro', '\"98新 电池有点low了\"', 1000000, 'http://image.yihuo.com/group1/M00/00/03/wKhQqF7WVNGAO0kjAADZ_uDjm3o296.jpg');
COMMIT;

-- ----------------------------
-- Table structure for tb_order_status
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_status`;
CREATE TABLE `tb_order_status` (
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `status` int(1) DEFAULT NULL COMMENT '状态：1、未付款 2、已付款,未发货 3、已发货,未确认 4、交易成功 5、交易关闭 6、已评价',
  `create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `payment_time` datetime DEFAULT NULL COMMENT '付款时间',
  `consign_time` datetime DEFAULT NULL COMMENT '发货时间',
  `end_time` datetime DEFAULT NULL COMMENT '交易完成时间',
  `close_time` datetime DEFAULT NULL COMMENT '交易关闭时间',
  `comment_time` datetime DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`order_id`) USING BTREE,
  KEY `status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='订单状态表';

-- ----------------------------
-- Records of tb_order_status
-- ----------------------------
BEGIN;
INSERT INTO `tb_order_status` VALUES (1267809750557528064, 1, '2020-06-02 08:26:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_order_status` VALUES (1268040849770549248, 1, '2020-06-02 23:44:32', NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码，加密存储',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '注册手机号',
  `created` date NOT NULL COMMENT '创建时间',
  `head` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
BEGIN;
INSERT INTO `tb_user` VALUES (1, '起名字真难', '$2a$10$9RHGm8XLBrYEbAZwYjC5Cu3yqLY2T1pefwfjCokDMKY.Q5RBzgnnG', '18377898557', '2020-05-24', 'http://image.yihuo.com/group1/M00/00/03/wKhQqF7WUUKAd1DmAAITIhER6XU69.jpeg');
INSERT INTO `tb_user` VALUES (2, '易货1', '$2a$10$ExlsZV8fneVPjeaBwtbpM.Hlj/jbxZT0tsQ7XvwLGcc1HwcY7PIKi', '18377898558', '2020-05-24', 'http://image.yihuo.com/group1/M00/00/03/wKhQqF7WUVCAGXp7AABOlwC3o_M369.png');
INSERT INTO `tb_user` VALUES (3, '易货2', '$2a$10$fAst/kGO8tLQch9pIFElheo5DJxZEbSMjZDZEoTvG//gbNS1jRZnq', '18377898559', '2020-05-24', 'http://image.yihuo.com/group1/M00/00/03/wKhQqF7WUVyAdbRsAABh0ArRh1g361.jpg');
COMMIT;

-- ----------------------------
-- Table structure for tb_wanted_goods
-- ----------------------------
DROP TABLE IF EXISTS `tb_wanted_goods`;
CREATE TABLE `tb_wanted_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `content` varchar(1024) NOT NULL,
  `title` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sell_price` float NOT NULL,
  `trade_place` varchar(128) NOT NULL,
  `view_number` int(11) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqvrfd43yhp11er38hkfcxi103` (`user_id`),
  CONSTRAINT `FKqvrfd43yhp11er38hkfcxi103` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_wanted_goods
-- ----------------------------
BEGIN;
INSERT INTO `tb_wanted_goods` VALUES (1, '2020-05-30 00:09:55', '2020-05-30 11:28:48', '98新 屏幕无划痕', 'iPhonexs', 4000, '清华大学校门', 0, 1);
INSERT INTO `tb_wanted_goods` VALUES (2, '2020-05-30 00:09:55', '2020-05-30 11:28:48', '98新 屏幕无划痕', '三星', 4000, '清华大学校门', 0, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
