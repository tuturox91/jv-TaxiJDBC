CREATE SCHEMA `taxiservicedb` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `taxiservicedb`.`manufacturers` (
    `id` BIGINT(1000) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `country` VARCHAR(50) NOT NULL,
    `is_deleted` TINYINT NULL DEFAULT 0,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
