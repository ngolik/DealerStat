CREATE TABLE `comment`
(
    `id`            int    NOT NULL AUTO_INCREMENT,
    `createdAt`     datetime(6)  DEFAULT NULL,
    `updatedAt`     datetime(6)  DEFAULT NULL,
    `message`       varchar(255) DEFAULT NULL,
    `rate`          int    NOT NULL,
    `approved`      bit(1) NOT NULL,
    `author_id`     int    NOT NULL,
    `gameobject_id` int    NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKmve6jmvinowvmroe9om9bake1` (`author_id`),
    KEY `FKanbr11tu1qohi1uae668id3x` (`gameobject_id`),
    CONSTRAINT `FKanbr11tu1qohi1uae668id3x` FOREIGN KEY (`gameobject_id`) REFERENCES `gameobject` (`id`),
    CONSTRAINT `FKmve6jmvinowvmroe9om9bake1` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `game`
(
    `id`   int NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_gdtr9fjw6icy8hhf02kpmvqpc` (`name`)
);

CREATE TABLE `game_gameobject`
(
    `Game_id`        int NOT NULL,
    `gameobjects_id` int NOT NULL,
    KEY `FK17k7i0xgdnsk9843k44n3skfe` (`gameobjects_id`),
    KEY `FKt1hbfmaxsk9jrfejbv86trwvr` (`Game_id`),
    CONSTRAINT `FK17k7i0xgdnsk9843k44n3skfe` FOREIGN KEY (`gameobjects_id`) REFERENCES `gameobject` (`id`),
    CONSTRAINT `FKt1hbfmaxsk9jrfejbv86trwvr` FOREIGN KEY (`Game_id`) REFERENCES `game` (`id`)
);
CREATE TABLE `game_unconfirmedgameobject`
(
    `Game_id`                   int NOT NULL,
    `unconfirmedGameObjects_id` int NOT NULL,
    KEY `FKsqnuq1ye15f52b34wqexay42e` (`unconfirmedGameObjects_id`),
    KEY `FKc1uwsa7uc1t3s233dwe8lfgt0` (`Game_id`),
    CONSTRAINT `FKc1uwsa7uc1t3s233dwe8lfgt0` FOREIGN KEY (`Game_id`) REFERENCES `game` (`id`),
    CONSTRAINT `FKsqnuq1ye15f52b34wqexay42e` FOREIGN KEY (`unconfirmedGameObjects_id`) REFERENCES `unconfirmedgameobject` (`id`)
);
CREATE TABLE `gameobject`
(
    `id`        int          NOT NULL AUTO_INCREMENT,
    `createdAt` datetime(6) DEFAULT NULL,
    `updatedAt` datetime(6) DEFAULT NULL,
    `status`    int          NOT NULL,
    `text`      varchar(255) NOT NULL,
    `title`     varchar(255) NOT NULL,
    `approved`  bit(1)       NOT NULL,
    `author_id` int         DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKm9kkrd5b5kxln322i35a59ilk` (`author_id`),
    CONSTRAINT `FKm9kkrd5b5kxln322i35a59ilk` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `gameobject_game`
(
    `gameobjects_id` int NOT NULL,
    `game_id`        int NOT NULL,
    KEY `FKt84jpo1r1sdsii8fm1mqeni8` (`game_id`),
    KEY `FK3lbnwexj7jlhtc6jm20gxfbru` (`gameobjects_id`),
    CONSTRAINT `FK3lbnwexj7jlhtc6jm20gxfbru` FOREIGN KEY (`gameobjects_id`) REFERENCES `gameobject` (`id`),
    CONSTRAINT `FKt84jpo1r1sdsii8fm1mqeni8` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`)
);
CREATE TABLE `replycode`
(
    `id`         int          NOT NULL AUTO_INCREMENT,
    `code`       varchar(255) NOT NULL,
    `expiryDate` datetime(6)  NOT NULL,
    `user_id`    int          NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKdu14rb6u1s2b4nyu33alug3m6` (`user_id`),
    CONSTRAINT `FKdu14rb6u1s2b4nyu33alug3m6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);
CREATE TABLE `role`
(
    `id`   int          NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `token`
(
    `id`         int          NOT NULL AUTO_INCREMENT,
    `expiryDate` datetime(6)  NOT NULL,
    `token`      varchar(255) NOT NULL,
    `user_id`    int          NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKiiyr9nhulmfrvje08nvravy02` (`user_id`),
    CONSTRAINT `FKiiyr9nhulmfrvje08nvravy02` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);
CREATE TABLE `unconfirmedcomment`
(
    `id`         int NOT NULL AUTO_INCREMENT,
    `createdAt`  datetime(6)  DEFAULT NULL,
    `updatedAt`  datetime(6)  DEFAULT NULL,
    `message`    varchar(255) DEFAULT NULL,
    `rate`       int NOT NULL,
    `comment_id` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKo5clpmdffida2u5qaq9293hsj` (`comment_id`),
    CONSTRAINT `FKo5clpmdffida2u5qaq9293hsj` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`)
);
CREATE TABLE `unconfirmedgameobject`
(
    `id`            int          NOT NULL AUTO_INCREMENT,
    `createdAt`     datetime(6) DEFAULT NULL,
    `updatedAt`     datetime(6) DEFAULT NULL,
    `status`        int          NOT NULL,
    `text`          varchar(255) NOT NULL,
    `title`         varchar(255) NOT NULL,
    `gameobject_id` int          NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKmx5otwe9tbah8broyhp2vvd4v` (`gameobject_id`),
    CONSTRAINT `FKmx5otwe9tbah8broyhp2vvd4v` FOREIGN KEY (`gameobject_id`) REFERENCES `gameobject` (`id`)
);
CREATE TABLE `unconfirmedgameobject_game`
(
    `unconfirmed_gameobject_hasid` int NOT NULL,
    `game_id`                      int NOT NULL,
    KEY `FK33rg12nf345w27a95mucmq670` (`game_id`),
    KEY `FKgcodyj9f0368jbppfagqp8ytq` (`unconfirmed_gameobject_hasid`),
    CONSTRAINT `FK33rg12nf345w27a95mucmq670` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`),
    CONSTRAINT `FKgcodyj9f0368jbppfagqp8ytq` FOREIGN KEY (`unconfirmed_gameobject_hasid`) REFERENCES `unconfirmedgameobject` (`id`)
);
CREATE TABLE `user`
(
    `id`        int          NOT NULL AUTO_INCREMENT,
    `createdAt` datetime(6)  DEFAULT NULL,
    `updatedAt` datetime(6)  DEFAULT NULL,
    `email`     varchar(255) DEFAULT NULL,
    `enabled`   bit(1)       NOT NULL,
    `firstName` varchar(255) DEFAULT NULL,
    `lastName`  varchar(255) DEFAULT NULL,
    `password`  varchar(255) NOT NULL,
    `role_id`   int          NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_9t4er00mstv8by4kyik0uong0` (`email`),
    KEY `FK84qlpfci484r1luck11eno6ec` (`role_id`),
    CONSTRAINT `FK84qlpfci484r1luck11eno6ec` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);

