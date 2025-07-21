CREATE SCHEMA `jv_jdbc_taxi` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `jv_jdbc_taxi`.`manufacturers` (
                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                `name` VARCHAR(255) NOT NULL,
                                                `country` VARCHAR(255) NOT NULL,
                                                `is_deleted` TINYINT NOT NULL DEFAULT 0,
                                                PRIMARY KEY (`id`))
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
