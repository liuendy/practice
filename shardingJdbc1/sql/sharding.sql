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

/*Table structure for table `t_order` */

CREATE TABLE `t_order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `order_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=228620604381593602 DEFAULT CHARSET=utf8;

/*Data for the table `t_order` */

insert  into `t_order`(`order_id`,`user_id`,`order_time`) values 
(228620604381593600,6,'2018-07-24 20:56:40'),
(228620604381593601,8,'2018-09-17 18:27:29');

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
) ENGINE=InnoDB AUTO_INCREMENT=248505409994752001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `t_report201808` */

insert  into `t_report201808`(`id`,`sale_count`,`sale_amount`,`create_time`) values 
(1,555,33333333.44,'2018-08-19 16:27:20'),
(248505409994752000,555,33333333.44,'2018-08-19 16:27:20');

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
