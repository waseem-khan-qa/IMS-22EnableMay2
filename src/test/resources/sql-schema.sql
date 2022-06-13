drop schema ims;

CREATE SCHEMA IF NOT EXISTS `ims`;

USE `ims`;

CREATE TABLE IF NOT EXISTS `ims`.`customers` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(40) DEFAULT NULL,
    `surname` VARCHAR(40) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ims`.`items` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(40) DEFAULT NULL,
    `value` DOUBLE(30, 2),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ims`.`orders` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `customer_id` INT(11) NOT NULL ,
    PRIMARY KEY (`id`),
	CONSTRAINT `FK_customer_order` FOREIGN KEY (`customer_id`)
    REFERENCES `customers`(`id`)
);
CREATE TABLE IF NOT EXISTS `ims`.`order_items` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `order_id` INT(11) NOT NULL,
    `item_id` INT(11) NOT NULL,
    PRIMARY KEY (`id`, `order_id`),
	CONSTRAINT FK_orderItem_order FOREIGN KEY (`order_id`)
    REFERENCES `orders`(`id`),
	CONSTRAINT `FK_orderItem_item` FOREIGN KEY (`item_id`)
    REFERENCES `items`(`id`)
);

