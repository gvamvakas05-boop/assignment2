CREATE DATABASE  IF NOT EXISTS `university_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `university_db`;
-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: university_db
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `course_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `title` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `professor_id` int DEFAULT NULL,
  PRIMARY KEY (`course_code`),
  KEY `professor_id` (`professor_id`),
  CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`professor_id`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES ('ECO-101','Μικροοικονομική Θεωρία',8),('ECO-102','Μακροοικονομική Θεωρία',10),('ECO-304','Δημόσια Οικονομικά',9),('INF-101','Εισαγωγή στον Προγραμματισμό (Java)',8),('INF-102','Δομές Δεδομένων',3),('INF-202','Προγραμματισμός στο Διαδίκτυο',NULL),('INF-203','Λειτουργικά Συστήματα',4),('INF-301','Βάσεις Δεδομένων',NULL),('INF-305','Αρχιτεκτονική Υπολογιστών',5),('INF-405','Τεχνητή Νοημοσύνη',NULL),('MARK-201','Αρχές Μάρκετινγκ',NULL),('MARK-305','Ψηφιακό Μάρκετινγκ',10),('STAT-101','Εισαγωγή στη Στατιστική',NULL),('STAT-105','Πιθανότητες & Στατιστική',6),('STAT-203','Ανάλυση Δεδομένων',NULL),('STAT-302','Στοχαστικές Διαδικασίες',7);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `surname` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `department` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'maria_sec','zaq1xsw2cde3!A','Μαρία','Παπαδοπούλου','Πληροφορική','secretary'),(2,'eleni_sec','zaq1xsw2cde3!A','Ελένη','Κωνσταντίνου','Διοικητική Επιστήμη','secretary'),(3,'nikos_prof','password123','Νικόλαος','Γεωργίου','Πληροφορική','professor'),(4,'dim_prof','password123','Δημήτριος','Ιωάννου','Πληροφορική','professor'),(5,'andreas_prof','password123','Ανδρέας','Βασιλείου','Πληροφορική','professor'),(6,'katerina_prof','password123','Κατερίνα','Δημητρίου','Στατιστική','professor'),(7,'john_prof','password123','Ιωάννης','Νικολάου','Στατιστική','professor'),(8,'anna_prof','password123','Άννα','Κωστοπούλου','Οικονομικά','professor'),(9,'giorgos_prof','password123','Γεώργιος','Μιχαηλίδης','Οικονομικά','professor'),(10,'sofia_prof','password123','Σοφία','Αλεξάνδρου','Μάρκετινγκ','professor'),(11,'stud1','password123','Γιάννης','Αντωνίου','Πληροφορική','student'),(12,'stud2','password123','Αλέξανδρος','Μακρής','Πληροφορική','student');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-27 18:01:34
