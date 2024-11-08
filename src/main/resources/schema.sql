SET time_zone = '+09:00';

CREATE TABLE IF NOT EXISTS users (

        user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(15) NOT NULL,
        password VARCHAR(15) NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        deleted_at TIMESTAMP DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS boards (

        board_id BIGINT AUTO_INCREMENT PRIMARY KEY,
        boardname VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS posts (

        post_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
        title      VARCHAR(30) NOT NULL,
        body       TEXT         NOT NULL,
        user_id    BIGINT,
        board_id   BIGINT,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        deleted_at TIMESTAMP DEFAULT NULL,
        FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE SET NULL,
        FOREIGN KEY (board_id) REFERENCES boards(board_id) ON DELETE SET NULL
);

DROP TABLE IF EXISTS tests;
DROP TABLE IF EXISTS traffics;
CREATE TABLE traffics (

        traffic_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
        url        VARCHAR(200),
        vus        BIGINT       NOT NULL,
        duration   VARCHAR(20)  NOT NULL,
        rps        BIGINT,
        user_id    BIGINT,
        board_id   BIGINT,
        created_at TIMESTAMP,
        updated_at TIMESTAMP,
        deleted_at TIMESTAMP DEFAULT NULL,
        FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE SET NULL,
        FOREIGN KEY (board_id) REFERENCES boards(board_id) ON DELETE SET NULL
);
