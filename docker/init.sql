CREATE DATABASE IF NOT EXISTS `webflux_vue3` DEFAULT CHARACTER SET = utf8 DEFAULT COLLATE = utf8_general_ci;
CREATE TABLE `examples` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
