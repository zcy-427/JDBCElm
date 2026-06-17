CREATE DATABASE IF NOT EXISTS `jdbc-elm`;
USE `jdbc-elm`;

CREATE TABLE IF NOT EXISTS admin (
    admin_id INT AUTO_INCREMENT comment '管理员ID',
    username VARCHAR(50) NOT NULL comment '管理员用户名',
    password VARCHAR(100) NOT NULL comment '管理员密码',

    PRIMARY KEY (`admin_id`),
    UNIQUE KEY `idx_username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  comment = '管理员表';


-- 商家表
CREATE TABLE IF NOT EXISTS business (
    business_id INT AUTO_INCREMENT comment '商家ID',
    password VARCHAR(100) NOT NULL DEFAULT '123456' comment '商家登录密码',
    business_name VARCHAR(100) NOT NULL comment '商家名称',
    business_address VARCHAR(200) NOT NULL comment '商家地址',
    business_explain VARCHAR(300) comment '商家介绍',
    star_price DECIMAL(8, 2) NOT NULL DEFAULT 0.00 comment '起送费',
    delivery_price DECIMAL(8, 2) NOT NULL DEFAULT 0.00 comment '配送费',

    PRIMARY KEY(`business_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  comment = '商家表';


-- 食品表
CREATE TABLE IF NOT EXISTS food (
    food_id INT AUTO_INCREMENT comment '食品ID',
    business_id INT NOT NULL comment '所属商家ID',
    food_name VARCHAR(100) NOT NULL comment '食品名称',
    food_explain VARCHAR(300) comment '食品介绍',
    food_price DECIMAL(8, 2) NOT NULL DEFAULT 0.00 comment '食品价格',

    PRIMARY KEY (`food_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  comment = '食品表';


-- 初始管理员账号
INSERT INTO admin (username, password)
VALUES ('admin', '123456');