-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ttcn-ecommerce
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdby` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `createddate` datetime(6) DEFAULT NULL,
  `modifiedby` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `modifieddate` datetime(6) DEFAULT NULL,
  `note` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `total_cost` decimal(19,2) DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK624gtjin3po807j3vix093tlf` (`customer_id`),
  CONSTRAINT `FK624gtjin3po807j3vix093tlf` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdby` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `createddate` datetime(6) DEFAULT NULL,
  `modifiedby` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `modifieddate` datetime(6) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `cart_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `status` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FKlt3mrhyikkt94xukyqrv652jd` (`cart_id`),
  KEY `FKdfk89kr9xleu3aljo18y2lyec` (`product_id`),
  CONSTRAINT `FKdfk89kr9xleu3aljo18y2lyec` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKlt3mrhyikkt94xukyqrv652jd` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdby` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `createddate` datetime(6) DEFAULT NULL,
  `modifiedby` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `modifieddate` datetime(6) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `description` text COLLATE utf8mb4_bin,
  `thumbnail` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,NULL,NULL,NULL,NULL,'laptop',NULL,'fas fa-laptop'),(2,NULL,NULL,NULL,NULL,'Tai nghe',NULL,'fas fa-headphones');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdby` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `createddate` datetime(6) DEFAULT NULL,
  `modifiedby` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `modifieddate` datetime(6) DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `phone_number` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `gender` int DEFAULT NULL,
  `enabled` int DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `profile_picture` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  `provider` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `FKo2oh87rk6lunf0lic1svc9y75` (`role_id`),
  CONSTRAINT `FKo2oh87rk6lunf0lic1svc9y75` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdby` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `createddate` datetime(6) DEFAULT NULL,
  `modifiedby` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `modifieddate` datetime(6) DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `phone_number` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `gender` int DEFAULT NULL,
  `enabled` int DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `profile_picture` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_role`
--

DROP TABLE IF EXISTS `employee_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_role` (
  `employee_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`employee_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_role`
--

LOCK TABLES `employee_role` WRITE;
/*!40000 ALTER TABLE `employee_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdby` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `createddate` datetime(6) DEFAULT NULL,
  `modifiedby` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `modifieddate` datetime(6) DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  `rating` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1mtsbur82frn64de7balymq9k` (`product_id`),
  KEY `FK1mtsbus82frn64de7balymq9d` (`customer_id`),
  CONSTRAINT `FK1mtsbur82frn64de7balymq9k` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK1mtsbus82frn64de7balymq9d` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdby` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `createddate` datetime(6) DEFAULT NULL,
  `modifiedby` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `modifieddate` datetime(6) DEFAULT NULL,
  `short_description` text COLLATE utf8mb4_bin,
  `description` text COLLATE utf8mb4_bin,
  `brand` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `unit_in_stock` int NOT NULL DEFAULT '0',
  `thumbnail` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
  CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,NULL,NULL,NULL,NULL,NULL,'Laptop APPLE MacBook Pro 2020 MYD82SA/A có vẻ ngoài cực kỳ sang trọng được kế thừa từ các thế hệ đi trước nhưng bên trong chứa đựng một cấu hình thực sự rất đáng gờm. Sản phẩm Macbook này hứa hẹn sẽ giúp bạn vượt qua mọi công việc hằng ngày ở văn phòng hay cả tại nhà riêng, thích hợp cho mọi người ưa thích sự sáng tạo, luôn tìm tòi những điều mới mẻ trong công việc.','Apple','Laptop APPLE MacBook Pro 2020 MYD82SA/A ( 13.3\\\" Apple M1/8GB/256GB SSD/macOS/1.4kg)',31890000.00,100,'https://lh3.googleusercontent.com/6iW6tc0lHp4paCYznq-gC5mEXEGMSBmrSq2I4MaXdPne5XWQI4l8m-bGRVCRFH94d4PEqtUIdH3FERr-VNDWaT2k9qcZ5Ey_PQ=w500-rw',1),(2,NULL,NULL,NULL,NULL,NULL,'Các dòng sản phẩm của thương hiệu Apple luôn được đánh giá cao về chất lượng cũng như về hình dáng, mẫu mã. Nếu bạn đang cần một chiếc laptop mỏng, nhẹ, nhỏ gọn để tiện mang theo bên mình thì lựa chọn Laptop APPLE MacBook Air 2020 MGN63SA/A là vô cùng hợp lý.','Apple','Laptop APPLE MacBook Air 2020 MGN63SA/A ( 13.3\\\" Apple M1/8GB/256GB SSD/macOS/1.3kg)',26500000.00,10,'https://lh3.googleusercontent.com/Ezh1zisXToaMPP30pXE50dnotXpEyxnGsYpbd6uZc6jEWRWhMrMY2EDuXNcWPhw4nfcwwC-mGGVEkkRtRSJE0P3hRPuvCjw=w500-rw',1),(19,'','2021-12-31 15:11:50.452000','','2021-12-31 15:30:52.004000','Máy tính xách tay/ Laptop MacBook Air 2020 13.3\" MGN73SA/A','Máy tính xách tay/ Laptop MacBook Air 2020 13.3\" MGN73SA/A mang đến hiệu năng xử lý mạnh mẽ ẩn bên trong thiết kế sang trọng và tinh tế. Là dòng laptop cao cấp đến từ thương hiệu nổi tiếng Apple, MacBook Air 2020 13.3\" MGN73SA/A hứa hẹn sẽ là người bạn đồng hành tuyệt vời, đáp ứng đa dạng các nhu cầu sử dụng, từ học tập, công việc đến giải trí.','Apple','Laptop APPLE MacBook Air 2020 MGN73SA/A ( 13.3\" Apple M1/8GB/512GB SSD/macOS/1.3kg)',31790000.00,50,'https://lh3.googleusercontent.com/skwj0sp9gWzzKtL3cuFtE7kncj6bDcdGfezZpM6WByG8MUAykq_97iN5EzZefQVDPJrrQOaE5yvOsRMKXEup3N7qOoRJpK4p_A=w500-rw',1),(20,NULL,NULL,NULL,NULL,NULL,'Tai nghe Corsair HS70 PRO WIRELESS SE Cream (CA-9011210-AP) là dòng tai nghe không dây, đây là phiên bản tiếp nối 2 bản HS60 và HS50. Chỉ với mức giá tầm 2 đến 3 triệu đồng, bạn đã sở hữu được chiếc tai nghe khá chất lượng dành cho game thủ. Cùng khám phá xem những chiếc tai nghe này có những đặc điểm nổi bật nào.',NULL,'Tai nghe Corsair HS70 PRO WIRELESS SE Cream (CA-9011210-AP)',1949000.00,30,'https://lh3.googleusercontent.com/xBzwzaZwgdmnYNtpChmLij4_UJfnyIKglGLauf7vb2XD--tQa7p5yNsYjqwZke2THeUadGs7--UTXZjACQP3rs-hByMPinwN=w500-rw',2),(23,'','2021-12-31 15:55:00.831000',NULL,NULL,'','Laptop Dell Inspiron 5584 (N5I5413W) là chiếc laptop văn phòng có cấu hình mạnh mẽ vượt trội và tốc độ xử lí dữ liệu nhanh chóng. Với card đồ họa rời MX130 2 GB, laptop còn giúp bạn xử lí công việc đồ họa và chơi một số tựa game nhẹ.','Dell','Laptop Dell Inspiron 15 5584 (5584-N5I5353W) (i5-8265U) (Bạc',15400000.00,50,'https://lh3.googleusercontent.com/NlaNmuQD7VOrBJn8_gc-ZBGv4OJsWMc3Ex-1yL2ZPeMXjOK7NXpbExKAl4Qlj2Dm_1bT4xVYL1hvb1CWJkd7rqoUitNyhRyi=w500-rw',1);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdby` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `createddate` datetime(6) DEFAULT NULL,
  `modifiedby` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `modifieddate` datetime(6) DEFAULT NULL,
  `code` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-31 23:05:50
