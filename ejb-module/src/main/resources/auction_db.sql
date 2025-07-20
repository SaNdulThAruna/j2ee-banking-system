-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.30 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for athena_db
CREATE DATABASE IF NOT EXISTS `athena_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `athena_db`;

-- Dumping structure for table athena_db.account
CREATE TABLE IF NOT EXISTS `account` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `ACCOUNTNAME` varchar(255) DEFAULT NULL,
  `account_number` varchar(255) NOT NULL,
  `ACCOUNTTYPE` varchar(255) DEFAULT NULL,
  `BALANCE` double NOT NULL,
  `CREATEDAT` datetime(6) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `account_number` (`account_number`),
  KEY `FK_ACCOUNT_customer_id` (`customer_id`),
  CONSTRAINT `FK_ACCOUNT_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table athena_db.account: ~3 rows (approximately)
INSERT INTO `account` (`ID`, `ACCOUNTNAME`, `account_number`, `ACCOUNTTYPE`, `BALANCE`, `CREATEDAT`, `STATUS`, `customer_id`) VALUES
	(1, 'Savings - 1', 'ACC202565052', 'SAVINGS', 100010, '2025-07-16 20:03:19.926539', 'ACTIVE', 1),
	(2, 'FD - Sandul -1', 'ACC202564595', 'FIXED_DEPOSIT', 25000, '2025-07-16 20:03:33.058911', 'ACTIVE', 1),
	(3, 'Ishara-Savings-1', 'ACC202563188', 'SAVINGS', 120000, '2025-07-18 03:55:48.632718', 'ACTIVE', 4);

-- Dumping structure for table athena_db.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `CREATEDAT` datetime(6) DEFAULT NULL,
  `EMAIL` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `PHONE` varchar(255) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `EMAIL` (`EMAIL`),
  KEY `FK_CUSTOMER_user_id` (`user_id`),
  CONSTRAINT `FK_CUSTOMER_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table athena_db.customer: ~4 rows (approximately)
INSERT INTO `customer` (`ID`, `ADDRESS`, `CREATEDAT`, `EMAIL`, `full_name`, `PHONE`, `user_id`) VALUES
	(1, 'No.107/2 B.O.P 400 Pulasthigama', '2025-07-16 20:02:19.090775', 'sandultharuna7@gmail.com', 'Sandul Tharuna Munasinghe', '0771112223', 1),
	(2, 'No.50/5, School Road', '2025-07-16 20:20:58.632439', 'john@gmail.com', 'John Doe', '0772221113', 2),
	(3, 'No.56, Church Road', '2025-07-17 03:40:19.445104', 'hansana@gmail.com', 'Hansana Bandara', '0751112223', 3),
	(4, 'No.40/2, Ja-Ela', '2025-07-18 03:55:48.615970', 'ishara@gmail.com', 'Ishara Deshan', '0781112223', 4);

-- Dumping structure for table athena_db.interest_logs
CREATE TABLE IF NOT EXISTS `interest_logs` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `applied_date` datetime(6) NOT NULL,
  `interest_amount` double NOT NULL,
  `rate_applied` double NOT NULL,
  `account_id` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_interest_logs_account_id` (`account_id`),
  CONSTRAINT `FK_interest_logs_account_id` FOREIGN KEY (`account_id`) REFERENCES `account` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table athena_db.interest_logs: ~1 rows (approximately)
INSERT INTO `interest_logs` (`ID`, `applied_date`, `interest_amount`, `rate_applied`, `account_id`) VALUES
	(1, '2025-07-17 00:00:00.018647', 10, 0.0001, 1);

-- Dumping structure for table athena_db.scheduled_jobs
CREATE TABLE IF NOT EXISTS `scheduled_jobs` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `executed_at` datetime(6) NOT NULL,
  `job_name` varchar(255) NOT NULL,
  `log_message` varchar(255) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table athena_db.scheduled_jobs: ~0 rows (approximately)

-- Dumping structure for table athena_db.transaction
CREATE TABLE IF NOT EXISTS `transaction` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `AMOUNT` double NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `transaction_time` datetime(6) NOT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  `from_account_id` int DEFAULT NULL,
  `to_account_id` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_TRANSACTION_to_account_id` (`to_account_id`),
  KEY `FK_TRANSACTION_from_account_id` (`from_account_id`),
  CONSTRAINT `FK_TRANSACTION_from_account_id` FOREIGN KEY (`from_account_id`) REFERENCES `account` (`ID`),
  CONSTRAINT `FK_TRANSACTION_to_account_id` FOREIGN KEY (`to_account_id`) REFERENCES `account` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table athena_db.transaction: ~7 rows (approximately)
INSERT INTO `transaction` (`ID`, `AMOUNT`, `DESCRIPTION`, `STATUS`, `transaction_time`, `TYPE`, `from_account_id`, `to_account_id`) VALUES
	(1, 1000, 'refund', 'SUCCESS', '2025-07-17 04:10:00.000000', 'TRANSFER', 1, 2),
	(2, 1000, 'Refund from Admin', 'SUCCESS', '2025-07-18 00:31:24.118358', 'TRANSFER', 2, 1),
	(3, 500, 'Admin Transaction Test2', 'SUCCESS', '2025-07-18 00:33:25.297677', 'TRANSFER', 2, 1),
	(4, 500, 'Schedule Transaction', 'SUCCESS', '2025-07-18 00:43:00.000000', 'TRANSFER', 1, 2),
	(5, 120000, 'Initial deposit', 'SUCCESS', '2025-07-18 03:55:48.635723', 'DEPOSIT', NULL, 3),
	(6, 100, 'final Test transfer', 'SUCCESS', '2025-07-19 21:15:35.011945', 'TRANSFER', 1, 2),
	(7, 100, 'Final test scheduled transaction', 'SUCCESS', '2025-07-19 21:18:00.000000', 'TRANSFER', 2, 1);

-- Dumping structure for table athena_db.users
CREATE TABLE IF NOT EXISTS `users` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `CREATEDAT` datetime(6) DEFAULT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `ROLE` varchar(255) NOT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USERNAME` (`USERNAME`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table athena_db.users: ~4 rows (approximately)
INSERT INTO `users` (`ID`, `CREATEDAT`, `PASSWORD`, `ROLE`, `STATUS`, `USERNAME`) VALUES
	(1, '2025-07-16 20:02:19.082797', 'R81aCm8tMSkZa+kM9WycQA==:liieVbtJfsjAEywq7H7yyDC5NZ12PzLZmI211I/0Lxw=', 'USER', 'ACTIVE', 'sandul'),
	(2, '2025-07-16 20:20:58.625439', 'G6bhKlcyQHjArqTZ/u28gQ==:j9rQzrjvWjMvzX6OU8mbYm8TH2PVgZXDR6VGTo+gtjo=', 'SUPER_ADMIN', 'ACTIVE', 'john'),
	(3, '2025-07-17 03:40:19.440106', 'udyY484Brpy8IzmAL7OQbQ==:bPAAxgQ3N/u84cr0OcmdbwNaU4RbRVDPUC7bQ18QB8I=', 'ADMIN', 'ACTIVE', 'hansana'),
	(4, '2025-07-18 03:55:48.612520', 'ZTNP8ubMP7DitSJs1qNDfw==:bYYfV9P+KE18+qitvI+lBTuC0Zc6fgnMCLb150tFZ04=', 'USER', 'ACTIVE', 'ishara');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
