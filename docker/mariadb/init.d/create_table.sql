DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles`
(
    `user_id` bigint(20) NOT NULL,
    `roles`   varchar(50) NOT NULL,
    UNIQUE KEY `unique_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT,
    `username`     varchar(150) NOT NULL,
    `email`        varchar(150) NOT NULL,
    `name`         varchar(50)  NOT NULL,
    `nickname`     varchar(50)  NOT NULL,
    `phone_number` varchar(20) NOT NULL,
    `password`     varchar(255) NOT NULL,
    `updated_at`   datetime    DEFAULT NULL,
    `created_at`   datetime     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_username` (`username`) USING BTREE,
    UNIQUE KEY `unique_nickname` (`nickname`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
