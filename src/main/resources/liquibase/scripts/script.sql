-- Таблица для объявлений (ads)
CREATE TABLE ads (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    price INT,
    comments TEXT,
    owner_id BIGINT,
    FOREIGN KEY (owner_id) REFERENCES users(id)
);

-- Таблица для аватаров пользователей (avatars)
CREATE TABLE avatars (
    id BIGSERIAL PRIMARY KEY,
    image_path VARCHAR(255),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Таблица для комментариев (comments)
CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    text TEXT,
    created_at BIGINT,
    author_id BIGINT,
    ad_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (ad_id) REFERENCES ads(id)
);

-- Таблица для изображений объявлений (images)
CREATE TABLE images (
    id BIGSERIAL PRIMARY KEY,
    image_path VARCHAR(255),
    ad_id BIGINT,
    FOREIGN KEY (ad_id) REFERENCES ads(id)
);

-- Таблица для пользователей (users)
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone VARCHAR(255),
    role VARCHAR(50)
);
