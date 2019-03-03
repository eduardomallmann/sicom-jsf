-- MySQL dump 10.13  Distrib 5.6.30, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: sicom
-- ------------------------------------------------------
-- Server version	5.6.30-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `subscriber`
--

DROP TABLE IF EXISTS `subscriber`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscriber` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `full_name` varchar(144) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `email` varchar(144) NOT NULL,
  `role` varchar(52) NOT NULL,
  `course` varchar(144) DEFAULT NULL,
  `job` varchar(144) DEFAULT NULL,
  `event` tinyint(1) NOT NULL DEFAULT '0',
  `password` varchar(144) NOT NULL,
  `unencrypted_password` varchar(244) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uc_email` (`email`),
  UNIQUE KEY `uc_cpf` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subscriber_details`
--

DROP TABLE IF EXISTS `subscriber_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscriber_details` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `subscriber_id` int(11) unsigned NOT NULL,
  `workshop_id` int(11) unsigned NOT NULL,
  `presence` tinyint(1) NOT NULL DEFAULT '0',
  `payment` tinyint(1) NOT NULL DEFAULT '0',
  `order_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_details_order` (`order_id`),
  CONSTRAINT `fk_details_order` FOREIGN KEY (`order_id`) REFERENCES `subscriber_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subscriber_order`
--

DROP TABLE IF EXISTS `subscriber_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscriber_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `subscriber_id` int(11) unsigned NOT NULL,
  `amount` decimal(5,2) NOT NULL,
  `transaction_id` varchar(250) DEFAULT NULL,
  `redirect_url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `vi_auth_group`
--

DROP TABLE IF EXISTS `vi_auth_group`;
/*!50001 DROP VIEW IF EXISTS `vi_auth_group`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `vi_auth_group` AS SELECT 
 1 AS `email`,
 1 AS `role`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `workshop`
--

DROP TABLE IF EXISTS `workshop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workshop` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `description` varchar(144) DEFAULT NULL,
  `price` double(4,2) DEFAULT '0.00',
  `workshop_date` datetime NOT NULL DEFAULT '1900-01-01 00:00:01',
  `panelist` varchar(144) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Final view structure for view `vi_auth_group`
--

/*!50001 DROP VIEW IF EXISTS `vi_auth_group`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`sicom`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vi_auth_group` AS select `s`.`email` AS `email`,`s`.`role` AS `role` from `subscriber` `s` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-17 18:42:11
