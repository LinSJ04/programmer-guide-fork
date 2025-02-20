-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
                         `user_name` varchar(32) DEFAULT NULL COMMENT '用户名',
                         `password` varchar(32) DEFAULT NULL COMMENT '密码',
                         `user_sex` varchar(32) DEFAULT NULL,
                         `nick_name` varchar(32) DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;