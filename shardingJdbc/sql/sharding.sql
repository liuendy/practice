
CREATE TABLE `t_order0` (
  `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `order_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_order1` (
  `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `order_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_order2` (
  `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `order_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


