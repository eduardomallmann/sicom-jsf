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
-- Dumping data for table `subscriber`
--

LOCK TABLES `subscriber` WRITE;
/*!40000 ALTER TABLE `subscriber` DISABLE KEYS */;
INSERT INTO `subscriber` VALUES (1,'Eduardo Sausen Mallmann','00138795029','mallmann.edu@gmail.com','SUBSCRIBER',NULL,NULL,0,'C4CA4238A0B923820DCC509A6F75849B',''),(2,'Suporte Sigmo','76857617694','suporte@sigmo.org','SUBSCRIBER',NULL,NULL,0,'C4CA4238A0B923820DCC509A6F75849B','1');
/*!40000 ALTER TABLE `subscriber` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `subscriber_details`
--

LOCK TABLES `subscriber_details` WRITE;
/*!40000 ALTER TABLE `subscriber_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscriber_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `subscriber_order`
--

LOCK TABLES `subscriber_order` WRITE;
/*!40000 ALTER TABLE `subscriber_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscriber_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `workshop`
--

LOCK TABLES `workshop` WRITE;
/*!40000 ALTER TABLE `workshop` DISABLE KEYS */;
INSERT INTO `workshop` VALUES (1,'A Renda de Bilro no contexto local de moda',30.00,'2016-08-19 14:00:00','Mary Clasen e Talyta Bastos'),(2,'Ilustração para identidade corpotiva',25.00,'2016-08-24 18:00:00','Doug Menegazzi'),(3,'Composição Fotográfica registro da arte grafite',50.00,'2016-09-02 14:00:00','Sharlene Melanie e Richard Perassi'),(4,'Gestão Visual de projetos',30.00,'2016-09-16 14:20:00','Julio Teixeira'),(5,'Comunicação de marcas a partir de arquétipos',25.00,'2016-09-21 18:00:00','Felipe Machado'),(6,'Empregabilidade, marca e você',0.00,'2016-08-09 20:00:00','Márcio Miranda e Gladys Prado'),(7,'Painel com o artista Driin',0.00,'2016-10-04 19:00:00','Pedro Driin');
/*!40000 ALTER TABLE `workshop` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-17 18:45:18
