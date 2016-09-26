-- MySQL dump 10.13  Distrib 5.5.46, for Linux (x86_64)
--
-- Host: localhost    Database: demo
-- ------------------------------------------------------
-- Server version   5.5.46

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
-- Table structure for table `demotable`
--

DROP TABLE IF EXISTS `budget`;
CREATE TABLE `budget` (
    `budget_id` BIGINT not null auto_increment primary key,
    `project_id` BIGINT not null,
    `name` VARCHAR(20) not null
) ENGINE=InnoDB AUTO_INCREMENT=10000000;

ALTER TABLE `budget`
ADD UNIQUE `unq_budget_name` (`name`);

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
    `role_id` BIGINT not null auto_increment primary key,
    `budget_id` BIGINT not null,
    `name` VARCHAR(20) not null,
    `locationId` BIGINT,
    `rate` DECIMAL(6,2),
    `comment` VARCHAR(20)
) ENGINE=InnoDB AUTO_INCREMENT=10000000;

ALTER TABLE `role`
    ADD CONSTRAINT `fk_role_budget`
    FOREIGN KEY (`budget_id`)
    REFERENCES `budget` (`budget_id`)
    ON DELETE cascade;


DROP TABLE IF EXISTS `role_fte`;
CREATE TABLE `role_fte` (
    `role_id` BIGINT not null,
    `month` TINYINT(1) not null,
    `fte` DECIMAL(6,2)
) ENGINE=InnoDB;

ALTER TABLE `role_fte`
ADD UNIQUE `unq_role_fte` (`role_id`, `month`);

ALTER TABLE `role_fte`
    ADD CONSTRAINT `fk_fte_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `role` (`role_id`)
    ON DELETE cascade;


DROP TABLE IF EXISTS `role_allocation`;
CREATE TABLE `role_allocation` (
    `role_allocation_id` BIGINT not null auto_increment primary key,
    `role_id` BIGINT not null,
    `locationId` BIGINT not null,
    `resourceId` BIGINT,
    `comment` VARCHAR(200) not null,
    `rate` DECIMAL(6,2)
) ENGINE=InnoDB;

ALTER TABLE `role_allocation`
    ADD CONSTRAINT `fk_alloc_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `role` (`role_id`)
    ON DELETE cascade;

DROP TABLE IF EXISTS `role_allocation_fte`;
CREATE TABLE `role_allocation_fte` (
    `role_allocation_id` BIGINT not null,
    `type` char(1) not null,
    `month` TINYINT(1) not null,
    `fte` DECIMAL(6,2)
) ENGINE=InnoDB;

ALTER TABLE `role_allocation_fte`
ADD UNIQUE `unq_role_alloc_fte` (`role_allocation_id`, `type`, `month`);

ALTER TABLE `role_allocation_fte`
    ADD CONSTRAINT `fk_alloc_fte_aloc`
    FOREIGN KEY (`role_allocation_id`)
    REFERENCES `role_allocation` (`role_allocation_id`)
    ON DELETE cascade;
    