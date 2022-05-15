CREATE DATABASE restaurant_db;
CREATE TABLE `users` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `first_name` varchar(55) NOT NULL,
                         `last_name` varchar(255) NOT NULL,
                         `username` varchar(55) NOT NULL,
                         `password` varchar(4) NOT NULL,
                         `mobile` varchar(10) DEFAULT NULL,
                         `gender` varchar(10) NOT NULL,
                         `email` varchar(55) NOT NULL,
                         `last_login` datetime DEFAULT NULL,
                         `created` datetime NOT NULL,
                         `modified` datetime NOT NULL,
                         `role` varchar(25) DEFAULT NULL ,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `users_email_uindex` (`email`),
                         UNIQUE KEY `users_username_uindex` (`username`),
                         UNIQUE KEY `users_user_id_uindex` (`id`),
                         UNIQUE KEY `users_mobile_uindex` (`mobile`),
                         KEY `users_roles` (`role`)
    );

CREATE TABLE `ingredients` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `user_id` bigint NOT NULL,
                               `title` varchar(75) NOT NULL,
                               `slug` varchar(100) NOT NULL,
                               `type` varchar(20) NOT NULL,
                               `quantity` float NOT NULL DEFAULT '0',
                               `unit` varchar(15) NOT NULL,
                               `created` datetime DEFAULT NULL,
                               `modified` datetime DEFAULT NULL,
                               `content` text,
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `ingredients_id_uindex` (`id`),
                               KEY `ingredients_users_id_fk` (`user_id`),
                               CONSTRAINT `ingredients_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ;

CREATE TABLE `items` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `user_id` bigint DEFAULT NULL,
                         `title` varchar(75) NOT NULL,
                         `slug` varchar(100) NOT NULL,
                         `summary` tinytext,
                         `type` varchar(15) NOT NULL,
                         `cooking` tinyint(1) NOT NULL DEFAULT '0',
                         `price` float NOT NULL DEFAULT '0',
                         `currency` varchar(10) DEFAULT NULL,
                         `recipe` text,
                         `instructions` text,
                         `created` datetime DEFAULT NULL,
                         `modified` datetime DEFAULT NULL,
                         `content` text,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `items_id_uindex` (`id`),
                         KEY `items_users_id_fk` (`user_id`),
                         CONSTRAINT `items_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ;

CREATE TABLE `table_top` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `code` varchar(100) NOT NULL,
                             `status` varchar(10) DEFAULT NULL,
                             `reservation_id` bigint DEFAULT NULL,
                             `capacity` smallint NOT NULL DEFAULT '0',
                             `created` datetime DEFAULT NULL,
                             `modified` datetime DEFAULT NULL,
                             `content` text,
                             `order_id` bigint DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `table_top_id_uindex` (`id`),
                             UNIQUE KEY `table_top_id_uindex_2` (`id`)
);

CREATE TABLE `reservation` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `table_id` bigint NOT NULL,
                               `user_id` bigint NOT NULL,
                               `full_name` varchar(45) DEFAULT NULL,
                               `mobile` varchar(10) NOT NULL,
                               `date_of_reservation` datetime DEFAULT NULL,
                               `created` datetime DEFAULT NULL,
                               `modified` datetime DEFAULT NULL,
                               `content` text,
                               `status` varchar(15) DEFAULT NULL,
                               UNIQUE KEY `booking_table_id_uindex` (`id`),
                               KEY `reservation_table_top_id_fk` (`table_id`),
                               KEY `reservation_users_id_fk` (`user_id`),
                               CONSTRAINT `reservation_table_top_id_fk` FOREIGN KEY (`table_id`) REFERENCES `table_top` (`id`),
                               CONSTRAINT `reservation_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

CREATE TABLE `final_order` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `user_id` bigint DEFAULT NULL,
                               `table_id` bigint DEFAULT NULL,
                               `price` float DEFAULT NULL,
                               `status` varchar(10) NOT NULL DEFAULT 'new',
                               `created` datetime DEFAULT NULL,
                               `modified` datetime DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `order_id_uindex` (`id`),
                               KEY `order_table_top_id_fk` (`table_id`),
                               KEY `order_users_id_fk` (`user_id`),
                               CONSTRAINT `order_table_top_id_fk` FOREIGN KEY (`table_id`) REFERENCES `table_top` (`id`),
                               CONSTRAINT `order_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

CREATE TABLE `order_item` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `item_id` bigint NOT NULL,
                              `quantity` int DEFAULT NULL,
                              `price` float DEFAULT NULL,
                              `order_id` bigint NOT NULL,
                              `comment` text,
                              `created` datetime DEFAULT NULL,
                              `modified` datetime DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `order_item_id_uindex` (`id`),
                              KEY `order_item_items_id_fk` (`item_id`),
                              KEY `order_item_order_id_fk` (`order_id`),
                              CONSTRAINT `order_item_items_id_fk` FOREIGN KEY (`item_id`) REFERENCES `items` (`id`),
                              CONSTRAINT `order_item_order_id_fk` FOREIGN KEY (`order_id`) REFERENCES `final_order` (`id`)
);
CREATE TABLE `payment` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `user_id` bigint DEFAULT NULL,
                           `order_id` bigint DEFAULT NULL,
                           `table_id` bigint DEFAULT NULL,
                           `code` varchar(25) NOT NULL,
                           `type` varchar(20) DEFAULT NULL,
                           `price` float DEFAULT NULL,
                           `created` datetime DEFAULT NULL,
                           `modified` datetime DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `transaction_id_uindex` (`id`),
                           KEY `transaction_order_id_fk` (`order_id`),
                           KEY `transaction_table_top_id_fk` (`table_id`),
                           KEY `transaction_users_id_fk` (`user_id`),
                           CONSTRAINT `transaction_order_id_fk` FOREIGN KEY (`order_id`) REFERENCES `final_order` (`id`),
                           CONSTRAINT `transaction_table_top_id_fk` FOREIGN KEY (`table_id`) REFERENCES `table_top` (`id`),
                           CONSTRAINT `transaction_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);


alter table table_top
    add constraint table_top_reservation_id_fk
        foreign key (reservation_id) references reservation (id);


alter table table_top
    add constraint table_top_final_order_id_fk
        foreign key (order_id) references final_order (id);
