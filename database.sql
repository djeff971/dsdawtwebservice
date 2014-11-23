-- MySQL dump 10.13  Distrib 5.5.34, for osx10.6 (i386)
--
-- Host: localhost    Database: isep_awt
-- ------------------------------------------------------
-- Server version	5.5.34

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
-- Table structure for table `isep_awt_tweet`
--

DROP TABLE IF EXISTS `isep_awt_tweet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `isep_awt_tweet` (
  `tweet_id` int(11) NOT NULL AUTO_INCREMENT,
  `author_id` int(11) NOT NULL,
  `message` varchar(200) NOT NULL,
  `tweet_date` date NOT NULL,
  PRIMARY KEY (`tweet_id`),
  KEY `FK_TWEET` (`author_id`),
  CONSTRAINT `FK_TWEET` FOREIGN KEY (`author_id`) REFERENCES `isep_awt_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isep_awt_tweet`
--

LOCK TABLES `isep_awt_tweet` WRITE;
/*!40000 ALTER TABLE `isep_awt_tweet` DISABLE KEYS */;
INSERT INTO `isep_awt_tweet` VALUES (1,2,'this is my first tweet hehe ^^','2014-09-04');
INSERT INTO `isep_awt_tweet` VALUES (2,3,'@dpierre bienvenue !','2014-09-04');
/*!40000 ALTER TABLE `isep_awt_tweet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `isep_awt_user`
--

DROP TABLE IF EXISTS `isep_awt_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `isep_awt_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `nickname` varchar(20) NOT NULL,
  `joined_date` date NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isep_awt_user`
--

LOCK TABLES `isep_awt_user` WRITE;
/*!40000 ALTER TABLE `isep_awt_user` DISABLE KEYS */;
INSERT INTO `isep_awt_user` VALUES (1,'doriane selva','@dodo','2010-09-04');
INSERT INTO `isep_awt_user` VALUES (2,'djeffrey pierre','@dpierre','2014-09-04');
INSERT INTO `isep_awt_user` VALUES (3,'sarah marcon','@sma','2014-06-30');
INSERT INTO `isep_awt_user` VALUES (4,'jonh doe','@jdk','2006-03-10');
INSERT INTO `isep_awt_user` VALUES (5,'pierre manu','@pmn','2006-05-14');
INSERT INTO `isep_awt_user` VALUES (6,'alain','@altolabs','2004-10-12');
INSERT INTO `isep_awt_user` VALUES (7,'Paul','@altolabs','2004-10-12');
/*!40000 ALTER TABLE `isep_awt_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-23 15:58:01
