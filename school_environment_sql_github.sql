-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: school_environment
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `class_groups`
--

DROP TABLE IF EXISTS `class_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_groups` (
  `groupID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`groupID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_groups`
--

LOCK TABLES `class_groups` WRITE;
/*!40000 ALTER TABLE `class_groups` DISABLE KEYS */;
INSERT INTO `class_groups` VALUES (1,'Morals'),(2,'Energy & Earth Sciences Studies'),(3,'Medics for All');
/*!40000 ALTER TABLE `class_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marks`
--

DROP TABLE IF EXISTS `marks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `marks` (
  `markID` int NOT NULL AUTO_INCREMENT,
  `studentID` int DEFAULT NULL,
  `subjectID` int DEFAULT NULL,
  `date` date DEFAULT NULL,
  `mark` int DEFAULT NULL,
  `passFail` tinyint DEFAULT NULL,
  PRIMARY KEY (`markID`),
  KEY `studentID` (`studentID`),
  KEY `subjectID` (`subjectID`),
  CONSTRAINT `marks_ibfk_1` FOREIGN KEY (`studentID`) REFERENCES `students` (`studentID`),
  CONSTRAINT `marks_ibfk_2` FOREIGN KEY (`subjectID`) REFERENCES `subjects` (`subjectID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marks`
--

LOCK TABLES `marks` WRITE;
/*!40000 ALTER TABLE `marks` DISABLE KEYS */;
INSERT INTO `marks` VALUES (1,7,5,'2021-01-02',86,1),(2,8,4,'2021-03-12',96,1),(3,8,4,'2020-12-04',89,1),(4,8,4,'2021-03-27',85,1),(5,8,4,'2020-09-18',85,1);
/*!40000 ALTER TABLE `marks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `studentID` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `groupID` int DEFAULT NULL,
  PRIMARY KEY (`studentID`),
  KEY `groupID_idx` (`groupID`),
  CONSTRAINT `students_ibfk_1` FOREIGN KEY (`groupID`) REFERENCES `class_groups` (`groupID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (1,'Mary','Jane',3),(2,'Eloise','Lewis',NULL),(4,'Mark','Ferris',NULL),(5,'Nira','Low',1),(6,'Joe','Bloggs',NULL),(7,'Jane','Doe',1),(8,'Roselyn','Kamugisha',2),(9,'Peter','Crew',NULL),(10,'Angelica','Lewis',NULL),(11,'Rebecca','Smith',NULL);
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjects`
--

DROP TABLE IF EXISTS `subjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subjects` (
  `subjectID` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`subjectID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjects`
--

LOCK TABLES `subjects` WRITE;
/*!40000 ALTER TABLE `subjects` DISABLE KEYS */;
INSERT INTO `subjects` VALUES (2,'Philosophy & Ethics'),(3,'History'),(4,'Energy & Earth Sciences'),(5,'Law'),(6,'Philanthropy'),(7,'Medicine');
/*!40000 ALTER TABLE `subjects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjectsandteachers`
--

DROP TABLE IF EXISTS `subjectsandteachers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subjectsandteachers` (
  `teacherID` int NOT NULL AUTO_INCREMENT,
  `teacherName` varchar(45) DEFAULT NULL,
  `subjectID` int DEFAULT NULL,
  `groupID` int DEFAULT NULL,
  PRIMARY KEY (`teacherID`),
  KEY `subjectID_idx` (`subjectID`),
  KEY `groupID_idx` (`groupID`),
  CONSTRAINT `groupID` FOREIGN KEY (`groupID`) REFERENCES `class_groups` (`groupID`),
  CONSTRAINT `subjectID` FOREIGN KEY (`subjectID`) REFERENCES `subjects` (`subjectID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjectsandteachers`
--

LOCK TABLES `subjectsandteachers` WRITE;
/*!40000 ALTER TABLE `subjectsandteachers` DISABLE KEYS */;
INSERT INTO `subjectsandteachers` VALUES (1,'Karen',5,1),(2,'Jacob',6,NULL),(3,'Gwen',4,2),(4,'Lucy',7,3);
/*!40000 ALTER TABLE `subjectsandteachers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'school_environment'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-24 15:50:46
