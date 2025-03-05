DROP  TABLE IF EXISTS houses CASCADE ;
DROP  TABLE IF EXISTS flats CASCADE ;
DROP TABLE IF EXISTS verification_requests CASCADE ;
DROP TABLE IF EXISTS tsj_requests CASCADE ;
-- DROP TABLE IF EXISTS users;

CREATE TABLE houses (
    id SERIAL PRIMARY KEY,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE flats (
    id SERIAL PRIMARY KEY,
    house_id INT,
    flat_number INT NOT NULL,
    cadastral_number VARCHAR(255) NOT NULL ,
    owner_id INT NOT NULL,
    CONSTRAINT fk_flats_house
        FOREIGN KEY (house_id) REFERENCES houses(id) ON DELETE CASCADE,
    CONSTRAINT fk_flats_user
        FOREIGN KEY (owner_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE verification_requests(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    cadastral_number VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    flat_number INT NOT NULL,
    user_id INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    CONSTRAINT fk_verification_requests_user
        FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE tsj_requests(
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    CONSTRAINT fk_tsj_requests_user
        FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- CREATE TABLE users (
--     id SERIAL PRIMARY KEY,
--     phone_number VARCHAR(255) NOT NULL,
--     email VARCHAR(255) NOT NULL,
--     first_name VARCHAR(255),
--     last_name VARCHAR(255),
--     password VARCHAR(255) NOT NULL,
--     flat_id SERIAL,
--     role VARCHAR(255) NOT NULL,
--     verification_status VARCHAR(255) NOT NULL,
--     chat_name VARCHAR(255)
-- );