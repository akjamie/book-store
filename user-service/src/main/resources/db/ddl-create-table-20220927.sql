CREATE TABLE `users`
(
    `id`                    bigint       NOT NULL AUTO_INCREMENT,
    `created_by`            varchar(255) DEFAULT NULL,
    `created_at`            datetime(6)  DEFAULT NULL,
    `updated_by`            varchar(255) DEFAULT NULL,
    `updated_at`            datetime(6)  DEFAULT NULL,
    `alias_name`            varchar(255) DEFAULT NULL,
    `auth_type`             varchar(255) NOT NULL,
    `email`                 varchar(255) DEFAULT NULL,
    `failed_login_attempts` int          DEFAULT NULL,
    `locked_time`           datetime(6)  DEFAULT NULL,
    `password`              varchar(255) DEFAULT NULL,
    `phone_number`          varchar(255) DEFAULT NULL,
    `status`                varchar(255) NOT NULL,
    `user_name`             varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_lqjrcobrh9jc8wpcar64q1bfh` (`user_name`),
    UNIQUE KEY `UK_lqjrcobrh9jc8wpcar65q1bfh` (`phone_number`),
    UNIQUE KEY `UK_lqjrcobrh9jc8wpcar66q1bfh` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `groups`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `created_by`  varchar(255) DEFAULT NULL,
    `created_at`  datetime(6)  DEFAULT NULL,
    `updated_by`  varchar(255) DEFAULT NULL,
    `updated_at`  datetime(6)  DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `name`        varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_182xa1gitcxqhaq6nn3n2kmo3` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `authorities`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `description` varchar(255) DEFAULT NULL,
    `name`        varchar(255) NOT NULL,
    `created_by`  varchar(255) DEFAULT NULL,
    `created_at`  datetime(6)  DEFAULT NULL,
    `updated_by`  varchar(255) DEFAULT NULL,
    `updated_at`  datetime(6)  DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_fxh32buxougyig294aw1n0he` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `group_authorities`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `authority_id` bigint NOT NULL,
    `remark`       varchar(255) DEFAULT NULL,
    `group_id`     bigint NOT NULL,
    `created_by`   varchar(255) DEFAULT NULL,
    `created_at`   datetime(6)  DEFAULT NULL,
    `updated_by`   varchar(255) DEFAULT NULL,
    `updated_at`   datetime(6)  DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKe67amtk5yus1gfskeiwqa7d7m` (`group_id`, `authority_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `group_members`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `remark`     varchar(255) DEFAULT NULL,
    `group_id`   bigint NOT NULL,
    `user_id`    bigint NOT NULL,
    `created_by` varchar(255) DEFAULT NULL,
    `created_at` datetime(6)  DEFAULT NULL,
    `updated_by` varchar(255) DEFAULT NULL,
    `updated_at` datetime(6)  DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKslb8ijvlgmp94sdir0hvwo0xx` (`user_id`, `group_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
