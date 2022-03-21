CREATE TABLE `menu_spi` (
	`id` int(10) NOT NULL AUTO_INCREMENT,
	`dish_name` varchar(100) NOT NULL,
	`available_meals` INT NOT NULL,
	`availability` boolean NOT NULL,
	`week` INT(2) NOT NULL,
	PRIMARY KEY (`id`)
);

