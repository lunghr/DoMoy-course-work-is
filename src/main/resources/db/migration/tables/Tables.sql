DROP  TABLE IF EXISTS houses CASCADE ;
DROP  TABLE IF EXISTS flats CASCADE ;
DROP TABLE IF EXISTS verification_requests CASCADE ;
DROP TABLE IF EXISTS tsj_requests CASCADE ;
DROP TABLE IF EXISTS secret_keys CASCADE ;
DROP TABLE IF EXISTS emergency_notifications CASCADE ;
DROP TABLE IF EXISTS applications CASCADE ;
DROP TABLE IF EXISTS applications_filenames CASCADE ;
DROP TABLE IF EXISTS application_responses CASCADE ;
DROP TABLE IF EXISTS posts CASCADE ;
-- DROP TABLE IF EXISTS users;

CREATE TABLE houses (
    id SERIAL PRIMARY KEY,
    address VARCHAR(255) NOT NULL
);


-- change user_id on id
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

-- change user_id on id
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

-- change admin_id on id
CREATE TABLE  secret_keys(
    id SERIAL PRIMARY KEY,
    key VARCHAR(12) NOT NULL,
    is_used BOOLEAN NOT NULL,
    admin_id INT,
    CONSTRAINT fk_secret_keys_admin
        FOREIGN KEY (admin_id) REFERENCES admins(admin_id) ON DELETE CASCADE
);

-- change admin_id on id
CREATE TABLE emergency_notifications(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR NOT NULL,
    date TIMESTAMP NOT NULL,
    house_id INT NOT NULL,
    admin_id INT NOT NULL,
    CONSTRAINT fk_emergency_notifications_admin
        FOREIGN KEY (admin_id) REFERENCES admins(admin_id) ON DELETE CASCADE,
    CONSTRAINT fk_emergency_notifications_house
        FOREIGN KEY (house_id) REFERENCES houses(id) ON DELETE CASCADE
);

-- change user_id on id
CREATE TABLE applications(
    id SERIAL PRIMARY KEY,
    status VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    theme VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_applications_user
        FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE applications_filenames(
    id SERIAL PRIMARY KEY,
    application_id INT NOT NULL,
    file_name VARCHAR NOT NULL,
    CONSTRAINT fk_applications_files_application
        FOREIGN KEY (application_id) REFERENCES applications(id) ON DELETE CASCADE
);

CREATE TABLE application_responses(
    id SERIAL PRIMARY KEY,
    application_id INT NOT NULL,
    admin_id INT NOT NULL,
    response VARCHAR NOT NULL,
    status VARCHAR(255) NOT NULL,
    date TIMESTAMP NOT NULL,
    CONSTRAINT fk_application_responses_application
        FOREIGN KEY (application_id) REFERENCES applications(id) ON DELETE CASCADE,
    CONSTRAINT fk_application_responses_admin
        FOREIGN KEY (admin_id) REFERENCES admins(admin_id) ON DELETE CASCADE
);

CREATE TABLE posts(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL,
    filename VARCHAR,
    author VARCHAR(255) NOT NULL
)

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