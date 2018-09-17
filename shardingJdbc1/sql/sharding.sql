/*
SQLyog 
MySQL - 5.6.39 : Database - sharding_jdbc
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sharding_jdbc` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `sharding_jdbc`;

/*Table structure for table `t_order0` */

CREATE TABLE `t_order0` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `order_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=228620604381593601 DEFAULT CHARSET=utf8;

/*Data for the table `t_order0` */

insert  into `t_order0`(`order_id`,`user_id`,`order_time`) values 
(228620604381593600,6,'2018-07-24 20:56:40');

/*Table structure for table `t_order1` */

CREATE TABLE `t_order1` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `order_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=228620718068203521 DEFAULT CHARSET=utf8;

/*Data for the table `t_order1` */

insert  into `t_order1`(`order_id`,`user_id`,`order_time`) values 
(228620718068203520,7,'2018-07-24 20:57:07');

/*Table structure for table `t_order2` */

CREATE TABLE `t_order2` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `order_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=228624464642834433 DEFAULT CHARSET=utf8;

/*Data for the table `t_order2` */

insert  into `t_order2`(`order_id`,`user_id`,`order_time`) values 
(228620481266188288,5,'2018-07-24 20:56:10'),
(228624464642834432,8,'2018-07-24 21:12:00');

/*Table structure for table `t_report` */

CREATE TABLE `t_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sale_count` int(11) NOT NULL DEFAULT '0' COMMENT '总销量',
  `sale_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '销售额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `t_report` */

/*Table structure for table `t_report201808` */

CREATE TABLE `t_report201808` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sale_count` int(11) NOT NULL DEFAULT '0' COMMENT '总销量',
  `sale_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '销售额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `t_report201808` */

insert  into `t_report201808`(`id`,`sale_count`,`sale_amount`,`create_time`) values 
(1,555,33333333.44,'2018-08-19 16:27:20');

/*Table structure for table `t_report201809` */

CREATE TABLE `t_report201809` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sale_count` int(11) NOT NULL DEFAULT '0' COMMENT '总销量',
  `sale_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '销售额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `t_report201809` */

insert  into `t_report201809`(`id`,`sale_count`,`sale_amount`,`create_time`) values 
(1,555,33333333.44,'2018-09-17 16:02:01'),
(2,555,33333333.44,'2018-09-17 16:27:20');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
