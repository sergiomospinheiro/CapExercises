
 CREATE TABLE `orders_spi` (
	`order_id` int(11) NOT NULL AUTO_INCREMENT,
	`transaction_id` varchar(255) NOT NULL,
	`dish_name` varchar(255) NOT NULL,
	`customer_name` varchar(255) NOT NULL,
	`quantity` int(2) NOT NULL,
	`delivery_address` varchar(255),
	`order_date_start` DATETIME NOT NULL,
	`order_date_end` DATETIME NOT NULL,
	`order_status` varchar(100) NOT NULL,
	PRIMARY KEY (`order_id`)
