-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema dbwallethub
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema dbwallethub
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `dbwallethub` DEFAULT CHARACTER SET utf8 ;
USE `dbwallethub` ;

-- -----------------------------------------------------
-- Table `dbwallethub`.`log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbwallethub`.`log` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `date` DATETIME NULL DEFAULT NULL,
  `ip_address` BIGINT(20) NOT NULL,
  `message` VARCHAR(255) NULL DEFAULT NULL,
  `request` VARCHAR(255) NULL DEFAULT NULL,
  `status` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 116485
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `dbwallethub`.`log_more_than_100`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbwallethub`.`log_more_than_100` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `comments` VARCHAR(255) NULL DEFAULT NULL,
  `count` BIGINT(20) NOT NULL,
  `ip_address` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `dbwallethub`.`log_more_than_250`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbwallethub`.`log_more_than_250` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `comments` VARCHAR(255) NULL DEFAULT NULL,
  `count` BIGINT(20) NOT NULL,
  `ip_address` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
