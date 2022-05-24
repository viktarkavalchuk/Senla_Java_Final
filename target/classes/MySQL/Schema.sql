-- MySQL Script generated by MySQL Workbench
-- Tue May 24 14:10:59 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Private_announcements
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `Private_announcements` ;

-- -----------------------------------------------------
-- Schema Private_announcements
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Private_announcements` DEFAULT CHARACTER SET utf8 ;
USE `Private_announcements` ;

-- -----------------------------------------------------
-- Table `Private_announcements`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Private_announcements`.`User` ;

CREATE TABLE IF NOT EXISTS `Private_announcements`.`User` (
  `id_User` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `telephone_Number` VARCHAR(45) NULL,
  `login` VARCHAR(45) NULL,
  `password` VARCHAR(75) NULL,
  PRIMARY KEY (`id_User`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Private_announcements`.`Role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Private_announcements`.`Role` ;

CREATE TABLE IF NOT EXISTS `Private_announcements`.`Role` (
  `id_Role` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NULL,
  PRIMARY KEY (`id_Role`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Private_announcements`.`Announcement`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Private_announcements`.`Announcement` ;

CREATE TABLE IF NOT EXISTS `Private_announcements`.`Announcement` (
  `id_Announcement` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `price` INT NULL,
  `startDate` DATE NULL,
  `soldDate` DATE NULL,
  `description` VARCHAR(455) NULL,
  `vip` TINYINT(1) NULL,
  `sold` TINYINT(1) NULL,
  `user_Id` INT NOT NULL,
  `rating_user` DOUBLE NULL DEFAULT 5.0,
  PRIMARY KEY (`id_Announcement`),
  INDEX `userID_idx` (`user_Id` ASC),
  CONSTRAINT `userID`
    FOREIGN KEY (`user_Id`)
    REFERENCES `Private_announcements`.`User` (`id_User`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Private_announcements`.`Comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Private_announcements`.`Comment` ;

CREATE TABLE IF NOT EXISTS `Private_announcements`.`Comment` (
  `idComment` INT NOT NULL AUTO_INCREMENT,
  `bodyComment` VARCHAR(500) NULL,
  `Sender` INT NULL,
  `toAnnouncement` INT NULL,
  PRIMARY KEY (`idComment`),
  INDEX `sender_idx` (`Sender` ASC),
  INDEX `toAnnouncement_idx` (`toAnnouncement` ASC),
  CONSTRAINT `senderComment`
    FOREIGN KEY (`Sender`)
    REFERENCES `Private_announcements`.`User` (`id_User`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `toAnnouncement`
    FOREIGN KEY (`toAnnouncement`)
    REFERENCES `Private_announcements`.`Announcement` (`id_Announcement`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Private_announcements`.`Chat`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Private_announcements`.`Chat` ;

CREATE TABLE IF NOT EXISTS `Private_announcements`.`Chat` (
  `idChat` INT NOT NULL AUTO_INCREMENT,
  `chatSender` INT NULL,
  `chatRecipient` INT NULL,
  `message` VARCHAR(500) NULL,
  PRIMARY KEY (`idChat`),
  INDEX `sender_idx` (`chatSender` ASC),
  INDEX `resipient_idx` (`chatRecipient` ASC),
  CONSTRAINT `senderChat`
    FOREIGN KEY (`chatSender`)
    REFERENCES `Private_announcements`.`User` (`id_User`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `resipientChat`
    FOREIGN KEY (`chatRecipient`)
    REFERENCES `Private_announcements`.`User` (`id_User`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Private_announcements`.`User_Role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Private_announcements`.`User_Role` ;

CREATE TABLE IF NOT EXISTS `Private_announcements`.`User_Role` (
  `idUser_Role` INT NOT NULL AUTO_INCREMENT,
  `user` INT NULL,
  `role` INT NOT NULL,
  PRIMARY KEY (`idUser_Role`),
  INDEX `UserRole_idx` (`user` ASC),
  CONSTRAINT `UserRole`
    FOREIGN KEY (`user`)
    REFERENCES `Private_announcements`.`User` (`id_User`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Role`
    FOREIGN KEY (`role`)
    REFERENCES `Private_announcements`.`Role` (`id_Role`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Private_announcements`.`Rating`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Private_announcements`.`Rating` ;

CREATE TABLE IF NOT EXISTS `Private_announcements`.`Rating` (
  `idRating` INT NOT NULL AUTO_INCREMENT,
  `toUser` INT NOT NULL,
  `fromUser` INT NOT NULL,
  `rating` INT NULL,
  INDEX `toUser_idx` (`toUser` ASC),
  INDEX `fromUser_idx` (`fromUser` ASC),
  PRIMARY KEY (`idRating`),
  CONSTRAINT `toUser`
    FOREIGN KEY (`toUser`)
    REFERENCES `Private_announcements`.`User` (`id_User`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fromUser`
    FOREIGN KEY (`fromUser`)
    REFERENCES `Private_announcements`.`User` (`id_User`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `Private_announcements`.`User`
-- -----------------------------------------------------
START TRANSACTION;
USE `Private_announcements`;
INSERT INTO `Private_announcements`.`User` (`id_User`, `name`, `email`, `telephone_Number`, `login`, `password`) VALUES (1, 'Admin', 'email@mail.com', '+3752912345678', 'Admin', '$2a$10$Xgp47yiwVrJGZSqcu6pRUei69ak8okFIFGO1EE2fdk4qNCbB93Bzq');
INSERT INTO `Private_announcements`.`User` (`id_User`, `name`, `email`, `telephone_Number`, `login`, `password`) VALUES (2, 'User_1', 'user_1@mail.com', '+3752912345679', 'User1', '$2a$10$Xgp47yiwVrJGZSqcu6pRUei69ak8okFIFGO1EE2fdk4qNCbB93Bzq');
INSERT INTO `Private_announcements`.`User` (`id_User`, `name`, `email`, `telephone_Number`, `login`, `password`) VALUES (3, 'User_2', 'user_2@mail.com', '+3752912345680', 'User2', '$2a$12$FKekHYTqCdYSzoVZwNB/8OmnRHuHdPS7HHHfXEcEnmVgFXTW/WBh.');
INSERT INTO `Private_announcements`.`User` (`id_User`, `name`, `email`, `telephone_Number`, `login`, `password`) VALUES (4, 'User_3', 'user_3@mail.com', '+3752912345681', 'User3', '$2a$12$FKekHYTqCdYSzoVZwNB/8OmnRHuHdPS7HHHfXEcEnmVgFXTW/WBh.');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Private_announcements`.`Role`
-- -----------------------------------------------------
START TRANSACTION;
USE `Private_announcements`;
INSERT INTO `Private_announcements`.`Role` (`id_Role`, `role`) VALUES (1, 'ADMIN');
INSERT INTO `Private_announcements`.`Role` (`id_Role`, `role`) VALUES (2, 'USER');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Private_announcements`.`Announcement`
-- -----------------------------------------------------
START TRANSACTION;
USE `Private_announcements`;
INSERT INTO `Private_announcements`.`Announcement` (`id_Announcement`, `name`, `price`, `startDate`, `soldDate`, `description`, `vip`, `sold`, `user_Id`, `rating_user`) VALUES (1, 'Vacuum cleaner BOSCH', 50, '2022-01-20', NULL, 'Almost new, under warranty', 0, 0, 2, 5.0);
INSERT INTO `Private_announcements`.`Announcement` (`id_Announcement`, `name`, `price`, `startDate`, `soldDate`, `description`, `vip`, `sold`, `user_Id`, `rating_user`) VALUES (2, 'TV LG', 250, '2022-01-20', NULL, NULL, 1, 0, 3, 5.0);
INSERT INTO `Private_announcements`.`Announcement` (`id_Announcement`, `name`, `price`, `startDate`, `soldDate`, `description`, `vip`, `sold`, `user_Id`, `rating_user`) VALUES (3, 'Pump', 10, '2022-01-20', '2022-05-01', NULL, 0, 1, 4, 5.0);
INSERT INTO `Private_announcements`.`Announcement` (`id_Announcement`, `name`, `price`, `startDate`, `soldDate`, `description`, `vip`, `sold`, `user_Id`, `rating_user`) VALUES (4, 'PC 1700 MHz', 500, '2022-01-20', NULL, NULL, 0, 0, 4, 5.0);
INSERT INTO `Private_announcements`.`Announcement` (`id_Announcement`, `name`, `price`, `startDate`, `soldDate`, `description`, `vip`, `sold`, `user_Id`, `rating_user`) VALUES (5, 'Car Ford Focus 1', 2700, '2022-02-25', NULL, 'Diesel engine, consumables replaced, two sets of tires', 0, 0, 2, 5.0);
INSERT INTO `Private_announcements`.`Announcement` (`id_Announcement`, `name`, `price`, `startDate`, `soldDate`, `description`, `vip`, `sold`, `user_Id`, `rating_user`) VALUES (6, 'Car Rover 416', 1200, '2022-04-21', NULL, NULL, 0, 0, 2, 5.0);
INSERT INTO `Private_announcements`.`Announcement` (`id_Announcement`, `name`, `price`, `startDate`, `soldDate`, `description`, `vip`, `sold`, `user_Id`, `rating_user`) VALUES (7, 'Washing machine', 60, '2022-04-23', NULL, NULL, 1, 0, 4, 5.0);
INSERT INTO `Private_announcements`.`Announcement` (`id_Announcement`, `name`, `price`, `startDate`, `soldDate`, `description`, `vip`, `sold`, `user_Id`, `rating_user`) VALUES (8, 'PC', 299, '2022-05-01', NULL, NULL, 0, 0, 3, 5.0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Private_announcements`.`Comment`
-- -----------------------------------------------------
START TRANSACTION;
USE `Private_announcements`;
INSERT INTO `Private_announcements`.`Comment` (`idComment`, `bodyComment`, `Sender`, `toAnnouncement`) VALUES (1, 'Comment1', 1, 2);
INSERT INTO `Private_announcements`.`Comment` (`idComment`, `bodyComment`, `Sender`, `toAnnouncement`) VALUES (2, 'Comment2', 2, 2);
INSERT INTO `Private_announcements`.`Comment` (`idComment`, `bodyComment`, `Sender`, `toAnnouncement`) VALUES (3, 'Comment3', 3, 3);
INSERT INTO `Private_announcements`.`Comment` (`idComment`, `bodyComment`, `Sender`, `toAnnouncement`) VALUES (4, 'Comment4', 4, 4);
INSERT INTO `Private_announcements`.`Comment` (`idComment`, `bodyComment`, `Sender`, `toAnnouncement`) VALUES (5, 'Comment5', 1, 3);
INSERT INTO `Private_announcements`.`Comment` (`idComment`, `bodyComment`, `Sender`, `toAnnouncement`) VALUES (6, 'Comment6', 3, 4);
INSERT INTO `Private_announcements`.`Comment` (`idComment`, `bodyComment`, `Sender`, `toAnnouncement`) VALUES (7, 'Comment7', 4, 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Private_announcements`.`Chat`
-- -----------------------------------------------------
START TRANSACTION;
USE `Private_announcements`;
INSERT INTO `Private_announcements`.`Chat` (`idChat`, `chatSender`, `chatRecipient`, `message`) VALUES (1, 3, 2, 'Will you give me a discount?');
INSERT INTO `Private_announcements`.`Chat` (`idChat`, `chatSender`, `chatRecipient`, `message`) VALUES (2, 2, 3, 'No.');
INSERT INTO `Private_announcements`.`Chat` (`idChat`, `chatSender`, `chatRecipient`, `message`) VALUES (3, 1, 2, 'sale ad posted');
INSERT INTO `Private_announcements`.`Chat` (`idChat`, `chatSender`, `chatRecipient`, `message`) VALUES (4, 2, 4, '1');
INSERT INTO `Private_announcements`.`Chat` (`idChat`, `chatSender`, `chatRecipient`, `message`) VALUES (5, 3, 1, 'How to change the password?');
INSERT INTO `Private_announcements`.`Chat` (`idChat`, `chatSender`, `chatRecipient`, `message`) VALUES (6, 4, 1, 'Can I recover my password? By mail?');
INSERT INTO `Private_announcements`.`Chat` (`idChat`, `chatSender`, `chatRecipient`, `message`) VALUES (7, 1, 3, 'Thank you!');
INSERT INTO `Private_announcements`.`Chat` (`idChat`, `chatSender`, `chatRecipient`, `message`) VALUES (8, 3, 2, 'Sold');
INSERT INTO `Private_announcements`.`Chat` (`idChat`, `chatSender`, `chatRecipient`, `message`) VALUES (9, 2, 3, 'Have a nice day');
INSERT INTO `Private_announcements`.`Chat` (`idChat`, `chatSender`, `chatRecipient`, `message`) VALUES (10, 2, 4, 'Put in reserve');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Private_announcements`.`User_Role`
-- -----------------------------------------------------
START TRANSACTION;
USE `Private_announcements`;
INSERT INTO `Private_announcements`.`User_Role` (`idUser_Role`, `user`, `role`) VALUES (1, 1, 1);
INSERT INTO `Private_announcements`.`User_Role` (`idUser_Role`, `user`, `role`) VALUES (2, 1, 2);
INSERT INTO `Private_announcements`.`User_Role` (`idUser_Role`, `user`, `role`) VALUES (3, 2, 2);
INSERT INTO `Private_announcements`.`User_Role` (`idUser_Role`, `user`, `role`) VALUES (4, 3, 2);
INSERT INTO `Private_announcements`.`User_Role` (`idUser_Role`, `user`, `role`) VALUES (5, 4, 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Private_announcements`.`Rating`
-- -----------------------------------------------------
START TRANSACTION;
USE `Private_announcements`;
INSERT INTO `Private_announcements`.`Rating` (`idRating`, `toUser`, `fromUser`, `rating`) VALUES (1, 1, 2, 5);
INSERT INTO `Private_announcements`.`Rating` (`idRating`, `toUser`, `fromUser`, `rating`) VALUES (2, 2, 4, 4);
INSERT INTO `Private_announcements`.`Rating` (`idRating`, `toUser`, `fromUser`, `rating`) VALUES (3, 2, 3, 5);
INSERT INTO `Private_announcements`.`Rating` (`idRating`, `toUser`, `fromUser`, `rating`) VALUES (4, 3, 4, 3);
INSERT INTO `Private_announcements`.`Rating` (`idRating`, `toUser`, `fromUser`, `rating`) VALUES (5, 3, 2, 2);
INSERT INTO `Private_announcements`.`Rating` (`idRating`, `toUser`, `fromUser`, `rating`) VALUES (6, 4, 1, 1);
INSERT INTO `Private_announcements`.`Rating` (`idRating`, `toUser`, `fromUser`, `rating`) VALUES (7, 4, 2, 3);
INSERT INTO `Private_announcements`.`Rating` (`idRating`, `toUser`, `fromUser`, `rating`) VALUES (8, 4, 3, 2);

COMMIT;

