-- MySQL Script generated by MySQL Workbench
-- Fri May  6 15:32:28 2022
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
  `telefone_Number` VARCHAR(45) NULL,
  `login` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  PRIMARY KEY (`id_User`))
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
  `comments` INT NULL,
  `vip` TINYINT(1) NULL,
  `sold` TINYINT(1) NULL,
  `user_Id` INT NULL,
  PRIMARY KEY (`id_Announcement`),
  INDEX `userID_idx` (`user_Id` ASC),
  INDEX `comment_idx` (`comments` ASC),
  CONSTRAINT `userID`
    FOREIGN KEY (`user_Id`)
    REFERENCES `Private_announcements`.`User` (`id_User`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `comment`
    FOREIGN KEY (`comments`)
    REFERENCES `Private_announcements`.`Comment` (`idComment`)
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
-- Table `Private_announcements`.`Users_Roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Private_announcements`.`Users_Roles` ;

CREATE TABLE IF NOT EXISTS `Private_announcements`.`Users_Roles` (
  `idUsers_Roles` INT NOT NULL AUTO_INCREMENT,
  `user` INT NULL,
  `role` INT NULL,
  PRIMARY KEY (`idUsers_Roles`),
  INDEX `Role_idx` (`role` ASC),
  INDEX `User_idx` (`user` ASC),
  CONSTRAINT `Role`
    FOREIGN KEY (`role`)
    REFERENCES `Private_announcements`.`Role` (`id_Role`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `User`
    FOREIGN KEY (`user`)
    REFERENCES `Private_announcements`.`User` (`id_User`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Private_announcements`.`Rating`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Private_announcements`.`Rating` ;

CREATE TABLE IF NOT EXISTS `Private_announcements`.`Rating` (
  `idRating` INT NOT NULL AUTO_INCREMENT,
  `toUser` INT NULL,
  `fromUser` INT NULL,
  `raiting` INT NULL,
  INDEX `toUser_idx` (`toUser` ASC),
  INDEX `fromUser_idx` (`fromUser` ASC),
  PRIMARY KEY (`idRating`),
  CONSTRAINT `toUser`
    FOREIGN KEY (`toUser`)
    REFERENCES `Private_announcements`.`User` (`id_User`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fromUser`
    FOREIGN KEY (`fromUser`)
    REFERENCES `Private_announcements`.`User` (`id_User`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `Private_announcements`.`User`
-- -----------------------------------------------------
START TRANSACTION;
USE `Private_announcements`;
INSERT INTO `Private_announcements`.`User` (`id_User`, `name`, `email`, `telefone_Number`, `login`, `password`) VALUES (1, 'Admin', 'email@mail.com', '+3752912345678', 'Admin', '123');
INSERT INTO `Private_announcements`.`User` (`id_User`, `name`, `email`, `telefone_Number`, `login`, `password`) VALUES (2, 'User_1', 'user_1@mail.com', '+3752912345679', 'User1', '001');
INSERT INTO `Private_announcements`.`User` (`id_User`, `name`, `email`, `telefone_Number`, `login`, `password`) VALUES (3, 'User_2', 'user_2@mail.com', '+3752912345680', 'User2', '002');
INSERT INTO `Private_announcements`.`User` (`id_User`, `name`, `email`, `telefone_Number`, `login`, `password`) VALUES (4, 'User_3', 'user_3@mail.com', '+3752912345681', 'User3', '003');

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
INSERT INTO `Private_announcements`.`Announcement` (`id_Announcement`, `name`, `price`, `startDate`, `soldDate`, `description`, `comments`, `vip`, `sold`, `user_Id`) VALUES (1, 'Пылесос BOSCH', 50, '2022-01-20', NULL, 'Почти новый, на гарантии', NULL, 0, 0, 2);
INSERT INTO `Private_announcements`.`Announcement` (`id_Announcement`, `name`, `price`, `startDate`, `soldDate`, `description`, `comments`, `vip`, `sold`, `user_Id`) VALUES (2, 'Телевизор LG', 250, '2022-01-20', NULL, NULL, NULL, 1, 0, 3);
INSERT INTO `Private_announcements`.`Announcement` (`id_Announcement`, `name`, `price`, `startDate`, `soldDate`, `description`, `comments`, `vip`, `sold`, `user_Id`) VALUES (3, 'Домкрат', 10, '2022-01-20', NULL, NULL, NULL, 0, 0, 4);
INSERT INTO `Private_announcements`.`Announcement` (`id_Announcement`, `name`, `price`, `startDate`, `soldDate`, `description`, `comments`, `vip`, `sold`, `user_Id`) VALUES (4, 'PC', 500, '2022-01-20', NULL, NULL, NULL, 0, 0, 4);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Private_announcements`.`Chat`
-- -----------------------------------------------------
START TRANSACTION;
USE `Private_announcements`;
INSERT INTO `Private_announcements`.`Chat` (`idChat`, `chatSender`, `chatRecipient`, `message`) VALUES (1, 3, 2, 'Скидку сделаете?');
INSERT INTO `Private_announcements`.`Chat` (`idChat`, `chatSender`, `chatRecipient`, `message`) VALUES (2, 2, 3, 'Нет.');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Private_announcements`.`Users_Roles`
-- -----------------------------------------------------
START TRANSACTION;
USE `Private_announcements`;
INSERT INTO `Private_announcements`.`Users_Roles` (`idUsers_Roles`, `user`, `role`) VALUES (1, 1, 1);
INSERT INTO `Private_announcements`.`Users_Roles` (`idUsers_Roles`, `user`, `role`) VALUES (2, 1, 2);
INSERT INTO `Private_announcements`.`Users_Roles` (`idUsers_Roles`, `user`, `role`) VALUES (3, 2, 2);
INSERT INTO `Private_announcements`.`Users_Roles` (`idUsers_Roles`, `user`, `role`) VALUES (4, 3, 2);
INSERT INTO `Private_announcements`.`Users_Roles` (`idUsers_Roles`, `user`, `role`) VALUES (5, 4, 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Private_announcements`.`Rating`
-- -----------------------------------------------------
START TRANSACTION;
USE `Private_announcements`;
INSERT INTO `Private_announcements`.`Rating` (`idRating`, `toUser`, `fromUser`, `raiting`) VALUES (1, 2, 2, 5);
INSERT INTO `Private_announcements`.`Rating` (`idRating`, `toUser`, `fromUser`, `raiting`) VALUES (2, 2, 4, 4);
INSERT INTO `Private_announcements`.`Rating` (`idRating`, `toUser`, `fromUser`, `raiting`) VALUES (3, 2, 3, 5);
INSERT INTO `Private_announcements`.`Rating` (`idRating`, `toUser`, `fromUser`, `raiting`) VALUES (4, 3, 4, 3);
INSERT INTO `Private_announcements`.`Rating` (`idRating`, `toUser`, `fromUser`, `raiting`) VALUES (5, 3, 2, 2);
INSERT INTO `Private_announcements`.`Rating` (`idRating`, `toUser`, `fromUser`, `raiting`) VALUES (6, 4, 1, 1);
INSERT INTO `Private_announcements`.`Rating` (`idRating`, `toUser`, `fromUser`, `raiting`) VALUES (7, 4, 2, 3);
INSERT INTO `Private_announcements`.`Rating` (`idRating`, `toUser`, `fromUser`, `raiting`) VALUES (8, 4, 3, 2);

COMMIT;

