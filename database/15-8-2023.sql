-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.30 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for restaurant_manager
CREATE DATABASE IF NOT EXISTS `restaurant_manager` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `restaurant_manager`;

-- Dumping structure for table restaurant_manager.bookings
CREATE TABLE IF NOT EXISTS `bookings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `info_id` int NOT NULL,
  `menu_name_id` int NOT NULL,
  `table_id` int NOT NULL,
  `flag` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8dy1r95yg5uimxfk5o9a4q8oj` (`info_id`),
  KEY `FKknemkymwyt9ou6asq8mp8t2k9` (`menu_name_id`),
  KEY `FKkl19d2e87d10fwmavf7wv17` (`table_id`),
  CONSTRAINT `FK8dy1r95yg5uimxfk5o9a4q8oj` FOREIGN KEY (`info_id`) REFERENCES `bookings_info` (`id`),
  CONSTRAINT `FKkl19d2e87d10fwmavf7wv17` FOREIGN KEY (`table_id`) REFERENCES `table_list` (`id`),
  CONSTRAINT `FKknemkymwyt9ou6asq8mp8t2k9` FOREIGN KEY (`menu_name_id`) REFERENCES `menu_name` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.bookings: ~47 rows (approximately)
INSERT INTO `bookings` (`id`, `info_id`, `menu_name_id`, `table_id`, `flag`) VALUES
	(152, 59, 94, 1, 1),
	(153, 59, 94, 2, 1);

-- Dumping structure for table restaurant_manager.bookings_info
CREATE TABLE IF NOT EXISTS `bookings_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `info` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `deposit` double DEFAULT NULL,
  `date_creat` datetime(6) NOT NULL,
  `start` datetime(6) NOT NULL,
  `end` datetime(6) NOT NULL,
  `flag` int DEFAULT '1',
  `person_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `info` (`info`),
  KEY `FKbprtsirt1m7jblqdvqdot6pxu` (`person_id`),
  CONSTRAINT `FKbprtsirt1m7jblqdvqdot6pxu` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.bookings_info: ~14 rows (approximately)
INSERT INTO `bookings_info` (`id`, `info`, `deposit`, `date_creat`, `start`, `end`, `flag`, `person_id`) VALUES
	(59, 'Hoàng liên hoan với bạn', 100, '2023-08-15 04:39:49.642770', '2023-08-16 07:00:00.000000', '2023-08-16 07:00:00.000000', 1, 64);

-- Dumping structure for table restaurant_manager.dish
CREATE TABLE IF NOT EXISTS `dish` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dish_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `type_id` int NOT NULL,
  `price` double NOT NULL,
  `date_creat` datetime(6) DEFAULT NULL,
  `flag` int NOT NULL DEFAULT '1',
  `dish_comment` varchar(1000) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `date_update` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gv2nha1oyj78a16sit0u93xch` (`dish_name`),
  UNIQUE KEY `UKgv2nha1oyj78a16sit0u93xch` (`dish_name`),
  KEY `FKpyormc76a2588qr1f6fagmm00` (`type_id`),
  CONSTRAINT `FKpyormc76a2588qr1f6fagmm00` FOREIGN KEY (`type_id`) REFERENCES `dish_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.dish: ~23 rows (approximately)
INSERT INTO `dish` (`id`, `dish_name`, `type_id`, `price`, `date_creat`, `flag`, `dish_comment`, `date_update`) VALUES
	(3, 'Cơm tấm', 2, 50, '2023-07-31 11:30:00.000000', 1, NULL, NULL),
	(4, 'Hủ tiếu nam vang', 2, 45, '2023-07-31 18:30:00.000000', 1, NULL, NULL),
	(7, 'Bún chả', 2, 40, '2023-07-31 10:00:00.000000', 1, NULL, NULL),
	(9, 'Bún thịt nướng', 2, 35, '2023-07-31 18:00:00.000000', 1, NULL, NULL),
	(11, 'Bánh canh', 2, 30, '2023-07-31 12:30:00.000000', 1, NULL, NULL),
	(13, 'Cà phê sữa đá', 6, 25, '2023-07-31 08:30:00.000000', 1, NULL, NULL),
	(14, 'Bánh bèo', 2, 25, '2023-07-31 15:45:00.000000', 1, NULL, NULL),
	(17, 'Chè đậu đỏ', 3, 20, '2023-07-31 19:30:00.000000', 1, NULL, NULL),
	(86, 'Bánh cưới 3 tầng', 3, 1000, '2023-08-15 04:20:03.878629', 1, 'style hàn quốc', NULL),
	(87, 'Bánh cưới 4 tầng', 3, 1500, '2023-08-15 04:20:25.236229', 1, 'style hàn quốc', NULL),
	(88, 'Lá ngón xào tỏi', 1, 25, '2023-08-15 04:21:01.328680', 1, 'dành cho người yêu cũ', NULL),
	(89, 'Salad dầu oliu', 1, 50, '2023-08-15 04:21:48.295775', 1, '', NULL),
	(90, 'Kem Cốm tươi', 5, 15, '2023-08-15 04:22:36.773544', 1, 'Tràng Tiền', NULL),
	(91, 'Nem chua', 2, 50, '2023-08-15 04:37:45.266237', 1, '', NULL);

-- Dumping structure for table restaurant_manager.dish_type
CREATE TABLE IF NOT EXISTS `dish_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `flag` int DEFAULT '1',
  `type` varchar(500) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t2namn4smv2a2rbw41p52ydar` (`name`) USING BTREE,
  UNIQUE KEY `UKt2namn4smv2a2rbw41p52ydar` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.dish_type: ~6 rows (approximately)
INSERT INTO `dish_type` (`id`, `name`, `flag`, `type`) VALUES
	(1, 'Món chay', 1, ''),
	(2, 'Món mặn', 1, ''),
	(3, 'Món tráng miệng', 1, ''),
	(4, 'Món canh', 1, ''),
	(5, 'Khai vị', 1, ''),
	(6, 'nước uống', 1, '');

-- Dumping structure for table restaurant_manager.food_inventory
CREATE TABLE IF NOT EXISTS `food_inventory` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_create` datetime(6) NOT NULL,
  `date_update` datetime(6) NOT NULL,
  `flag` int NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `price` int NOT NULL,
  `food_type` int NOT NULL,
  `unit` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKahld5gslv4gvspbysgcvuesht` (`food_type`),
  KEY `FKopb0crnpo5vmlmfbsglkjmwpx` (`unit`),
  CONSTRAINT `FKahld5gslv4gvspbysgcvuesht` FOREIGN KEY (`food_type`) REFERENCES `food_types` (`id`),
  CONSTRAINT `FKopb0crnpo5vmlmfbsglkjmwpx` FOREIGN KEY (`unit`) REFERENCES `food_unit_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.food_inventory: ~0 rows (approximately)

-- Dumping structure for table restaurant_manager.food_types
CREATE TABLE IF NOT EXISTS `food_types` (
  `id` int NOT NULL AUTO_INCREMENT,
  `flag` int NOT NULL,
  `type_name` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.food_types: ~0 rows (approximately)

-- Dumping structure for table restaurant_manager.food_unit_types
CREATE TABLE IF NOT EXISTS `food_unit_types` (
  `id` int NOT NULL AUTO_INCREMENT,
  `flag` int DEFAULT NULL,
  `name` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.food_unit_types: ~0 rows (approximately)

-- Dumping structure for table restaurant_manager.menu
CREATE TABLE IF NOT EXISTS `menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `menu_name_id` int NOT NULL,
  `dish_id` int NOT NULL,
  `quantity` int NOT NULL DEFAULT '0',
  `unit_price` double NOT NULL,
  `flag` int DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FKbm92hs82t812cdhtj0l0ok6kl` (`dish_id`),
  KEY `FKl304uqui2vum4qe4s9jftsdbi` (`menu_name_id`),
  CONSTRAINT `FKbm92hs82t812cdhtj0l0ok6kl` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`id`),
  CONSTRAINT `FKl304uqui2vum4qe4s9jftsdbi` FOREIGN KEY (`menu_name_id`) REFERENCES `menu_name` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.menu: ~73 rows (approximately)
INSERT INTO `menu` (`id`, `menu_name_id`, `dish_id`, `quantity`, `unit_price`, `flag`) VALUES
	(183, 94, 3, 1, 50, 1),
	(184, 94, 4, 1, 45, 1),
	(185, 94, 9, 1, 35, 1),
	(186, 94, 14, 2, 25, 1);

-- Dumping structure for table restaurant_manager.menu_name
CREATE TABLE IF NOT EXISTS `menu_name` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `date_creat` datetime(6) NOT NULL,
  `date_update` datetime(6) NOT NULL,
  `flag` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.menu_name: ~22 rows (approximately)
INSERT INTO `menu_name` (`id`, `name`, `date_creat`, `date_update`, `flag`) VALUES
	(94, 'Miền tây sông nước', '2023-08-15 04:37:07.467051', '2023-08-15 04:37:07.467051', 1);

-- Dumping structure for table restaurant_manager.oders
CREATE TABLE IF NOT EXISTS `oders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bill_id` int NOT NULL,
  `dish_id` int NOT NULL,
  `price` double NOT NULL,
  `quantity` int NOT NULL,
  `flag` int DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `oders_dish_id+dish_id` (`dish_id`),
  KEY `FK_oders_oder_info` (`bill_id`),
  CONSTRAINT `FK_oders_oder_info` FOREIGN KEY (`bill_id`) REFERENCES `oder_info` (`id`),
  CONSTRAINT `oders_dish_id+dish_id` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.oders: ~8 rows (approximately)

-- Dumping structure for table restaurant_manager.oder_info
CREATE TABLE IF NOT EXISTS `oder_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `person_id` int DEFAULT NULL,
  `time` datetime(6) DEFAULT NULL,
  `flag` int DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK_oder_info_persons` (`person_id`),
  CONSTRAINT `FK_oder_info_persons` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.oder_info: ~3 rows (approximately)

-- Dumping structure for table restaurant_manager.permissions
CREATE TABLE IF NOT EXISTS `permissions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `date_creat` datetime(6) NOT NULL,
  `date_update` datetime(6) NOT NULL,
  `flag` int DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission_name` (`permission_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.permissions: ~4 rows (approximately)
INSERT INTO `permissions` (`id`, `permission_name`, `date_creat`, `date_update`, `flag`) VALUES
	(1, 'god mode', '2023-07-25 10:30:00.000000', '2023-07-25 10:45:00.000000', 1),
	(2, 'admin', '2023-07-25 11:15:00.000000', '2023-07-25 11:30:00.000000', 2),
	(3, 'employee', '2023-07-25 12:00:00.000000', '2023-07-25 12:15:00.000000', 3),
	(4, 'user', '2023-07-25 13:30:00.000000', '2023-07-25 13:45:00.000000', 4);

-- Dumping structure for table restaurant_manager.persons
CREATE TABLE IF NOT EXISTS `persons` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `last_name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `username` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `password` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `email` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `address` varchar(300) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `date_of_birth` datetime(6) NOT NULL,
  `date_creat` datetime(6) NOT NULL,
  `date_update` datetime(6) NOT NULL,
  `permission` int NOT NULL,
  `flag` int DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`),
  KEY `FK5qpw9u1kph2f9l4kxuwg3vv55` (`permission`),
  CONSTRAINT `FK5qpw9u1kph2f9l4kxuwg3vv55` FOREIGN KEY (`permission`) REFERENCES `permissions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.persons: ~13 rows (approximately)
INSERT INTO `persons` (`id`, `name`, `last_name`, `username`, `password`, `email`, `phone`, `address`, `date_of_birth`, `date_creat`, `date_update`, `permission`, `flag`) VALUES
	(44, 'Đinh La ', 'Thăng', '0323636981', '', 'lathangdeptrai@gmail.com', '0323636981', 'Đội Cấn', '1999-02-28 00:00:00.000000', '2023-08-15 05:26:15.721374', '2023-08-15 05:26:15.721374', 3, 1),
	(45, 'Nguyễn Xuân ', 'Anh', '8563269871', '', 'anhcungdeptrai@gmail.ccom', '8563269871', 'Đà Nẵng', '2000-03-08 00:00:00.000000', '2023-08-15 02:17:00.284208', '2023-08-15 02:17:00.284208', 4, 1),
	(46, 'Ngọc', 'Trinh', '8583269841', '', 'giochiconngoc@gmail.com', '8583269841', 'Cần Thơ', '1989-03-07 00:00:00.000000', '2023-08-04 10:40:03.439368', '2023-08-05 01:03:39.166977', 4, 0),
	(47, 'Nguyễn Thanh', 'Nghị', '8889652364', '', 'nghicungdeptrai@gmail.com', '8889652364', 'Kiên Giang', '1945-11-29 00:00:00.000000', '2023-08-04 10:41:12.512317', '2023-08-05 01:03:30.826199', 4, 1),
	(48, 'Hoàng Trung', 'Hải', '9996554123', '', 'haido@gmail.com', '9996554123', '21 Thái Nguyên ', '1998-03-07 00:00:00.000000', '2023-08-15 02:16:56.271216', '2023-08-15 02:16:56.271216', 4, 1),
	(49, 'ano', 'nymous', '0000000000', '', 'anonymous@anonymous.com', '0000000000', 'universe', '2000-02-19 00:00:00.000000', '2023-08-06 00:29:20.710870', '2023-08-06 00:29:20.710870', 4, 1),
	(50, 'Xuân', 'Bắc', '5559652364', '', 'bacsovoi@gmail.com', '5559652364', 'Kiên Giang', '1945-11-28 00:00:00.000000', '2023-08-15 02:16:53.489005', '2023-08-15 02:16:53.489005', 4, 1),
	(51, 'Nguyễn Trung', 'Hiếu', '0773307333', 'D2U06T4KBLxCkYFEbHwnqw==', 'hieudeptrai@gmail.com', '0773307333', 'Trái Đất', '2023-08-07 00:00:00.000000', '2023-08-15 02:17:05.980000', '2023-08-15 02:17:05.980000', 1, 1),
	(52, 'Xuân', 'Nam', '8888855555', NULL, 'namcungsovo@gmail.com', '8888855555', 'Kiên Giang', '1945-11-28 00:00:00.000000', '2023-08-09 23:50:51.744534', '2023-08-09 23:50:51.744534', 4, 1),
	(53, 'Nguyễn Ngọc', 'Ngạn', '1111199999', NULL, 'ngandaigai@gmail.com', '1111199999', 'Mỹ Tho', '1989-03-07 00:00:00.000000', '2023-08-15 02:16:49.974062', '2023-08-15 02:16:49.974062', 4, 1),
	(54, 'ádsd', 'đâsd', '4444477777', NULL, 'ssdddwww@gmail.com', '4444477777', 'ádasd', '2023-09-01 00:00:00.000000', '2023-08-09 23:56:18.390508', '2023-08-09 23:56:18.390508', 4, 0),
	(64, 'Tần Thủy', 'Hoàng', '1111111111', NULL, 'hoanglamtien@gmail.com', '1111111111', 'Tử Cấm Thành', '2000-06-09 00:00:00.000000', '2023-08-10 00:00:30.894445', '2023-08-10 00:00:30.894445', 4, 1),
	(65, 'Ô Ba', 'Ma', '5555522222', NULL, 'obama@gmail.com', '5555522222', 'Chợ Đồng Xuân', '2000-06-09 00:00:00.000000', '2023-08-15 02:16:45.114186', '2023-08-15 02:16:45.114186', 4, 1);

-- Dumping structure for table restaurant_manager.salaries
CREATE TABLE IF NOT EXISTS `salaries` (
  `id` int NOT NULL AUTO_INCREMENT,
  `salary_amount` float NOT NULL,
  `effective_date` datetime(6) NOT NULL,
  `person_id` int NOT NULL,
  `flag` int DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FKh10cdbmmcww6b1qt5lanwrawk` (`person_id`),
  CONSTRAINT `FKh10cdbmmcww6b1qt5lanwrawk` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.salaries: ~0 rows (approximately)

-- Dumping structure for table restaurant_manager.table_list
CREATE TABLE IF NOT EXISTS `table_list` (
  `id` int NOT NULL AUTO_INCREMENT,
  `seating_capacity` int NOT NULL,
  `type` int NOT NULL,
  `status` int NOT NULL DEFAULT '1',
  `flag` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl9e6r1dl47sht9fpeeu6yo18j` (`type`),
  KEY `FK_table_list_table_status` (`status`),
  CONSTRAINT `FK_table_list_table_status` FOREIGN KEY (`status`) REFERENCES `table_status` (`id`),
  CONSTRAINT `FKl9e6r1dl47sht9fpeeu6yo18j` FOREIGN KEY (`type`) REFERENCES `table_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.table_list: ~21 rows (approximately)
INSERT INTO `table_list` (`id`, `seating_capacity`, `type`, `status`, `flag`) VALUES
	(1, 3, 1, 2, 0),
	(2, 4, 1, 3, 0),
	(3, 6, 3, 3, 0),
	(4, 4, 1, 1, 0),
	(5, 6, 2, 2, 0),
	(6, 8, 4, 1, 0),
	(7, 10, 2, 2, 0),
	(8, 5, 3, 1, 0),
	(9, 3, 1, 1, 0),
	(10, 2, 2, 1, 0),
	(11, 4, 1, 1, 0),
	(12, 9, 1, 1, 0),
	(13, 6, 2, 1, 0),
	(14, 4, 3, 1, 0),
	(15, 5, 1, 1, 0),
	(16, 8, 2, 1, 0),
	(17, 3, 1, 1, 0),
	(18, 4, 1, 1, 0),
	(19, 6, 2, 2, 0),
	(20, 2, 3, 1, 0),
	(21, 9, 1, 1, 0);

-- Dumping structure for table restaurant_manager.table_status
CREATE TABLE IF NOT EXISTS `table_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.table_status: ~3 rows (approximately)
INSERT INTO `table_status` (`id`, `name`) VALUES
	(3, 'đã đặt'),
	(2, 'đang sửa chữa'),
	(1, 'trống');

-- Dumping structure for table restaurant_manager.table_type
CREATE TABLE IF NOT EXISTS `table_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `comment` varchar(500) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `flag` int DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.table_type: ~4 rows (approximately)
INSERT INTO `table_type` (`id`, `name`, `comment`, `flag`) VALUES
	(1, 'vip1', 'đào 18', 1),
	(2, 'vip2', 'đào 50', 1),
	(3, 'vip3', 'điều hòa', 1),
	(4, 'vip4', 'ko điều hòa', 1);

-- Dumping structure for table restaurant_manager.transactions
CREATE TABLE IF NOT EXISTS `transactions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `person_id` int NOT NULL,
  `quantity` double NOT NULL,
  `type` int NOT NULL,
  `comment` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `date_creat` datetime(6) NOT NULL,
  `date_update` datetime(6) DEFAULT NULL,
  `flag` int DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK4njcsctdjs0trk63v2oid4x45` (`person_id`),
  KEY `FKqy19yjeh2ewt57hcr4ovwc306` (`type`),
  CONSTRAINT `FK4njcsctdjs0trk63v2oid4x45` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`),
  CONSTRAINT `FKqy19yjeh2ewt57hcr4ovwc306` FOREIGN KEY (`type`) REFERENCES `transactions_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.transactions: ~44 rows (approximately)
INSERT INTO `transactions` (`id`, `person_id`, `quantity`, `type`, `comment`, `date_creat`, `date_update`, `flag`) VALUES
	(129, 64, 100, 6, 'Deposit: Hoàng liên hoan với bạn', '2023-08-15 04:39:49.642770', NULL, 1),
	(130, 64, 260, 7, 'Debt: Hoàng liên hoan với bạn', '2023-08-15 04:39:49.704394', NULL, 1),
	(131, 51, 5000, 3, 'sửa điều hòa', '2023-08-04 15:00:00.000000', NULL, 1),
	(132, 51, 15000, 4, 'trả tiền điện tháng 3', '2023-04-01 19:00:00.000000', NULL, 1),
	(133, 44, 5000, 5, 'mua thịt bò - hóa đơn số 564654', '2023-05-06 15:00:00.000000', NULL, 1),
	(134, 51, 50000, 1, 'trả lương tháng 7', '2023-08-09 19:00:00.000000', NULL, 1);

-- Dumping structure for table restaurant_manager.transactions_type
CREATE TABLE IF NOT EXISTS `transactions_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `date_creat` datetime(6) NOT NULL,
  `date_update` datetime(6) NOT NULL,
  `flag` int DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table restaurant_manager.transactions_type: ~6 rows (approximately)
INSERT INTO `transactions_type` (`id`, `type`, `date_creat`, `date_update`, `flag`) VALUES
	(1, 'Chi - Trả lương', '2023-07-27 17:06:05.000000', '2023-07-27 17:06:08.000000', 2),
	(2, 'Thu - Khách hàng thanh toán', '2023-07-27 17:06:49.000000', '2023-07-27 17:06:51.000000', 1),
	(3, 'Chi - Bảo trì', '2023-07-27 17:07:15.000000', '2023-07-27 17:07:16.000000', 2),
	(4, 'Chi - Thanh hóa đơn chi phí', '2023-07-27 17:07:37.000000', '2023-07-27 17:07:38.000000', 2),
	(5, 'Chi - Mua nguyên liệu', '2023-07-27 17:09:18.000000', '2023-07-27 17:09:19.000000', 2),
	(6, 'Thu - Khách hàng đặt cọc', '2023-07-27 17:11:46.000000', '2023-07-27 17:11:48.000000', 1),
	(7, 'Nợ - Khách hàng còn thiếu', '2023-08-04 06:30:14.000000', '2023-08-04 06:30:15.000000', 1);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
