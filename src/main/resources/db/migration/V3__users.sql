DROP FUNCTION IF EXISTS create_user(VARCHAR(15), VARCHAR(128), VARCHAR(256)) CASCADE;

CREATE OR REPLACE FUNCTION create_user(p_phone_number VARCHAR(15), p_email VARCHAR(128), p_password VARCHAR(256))
RETURNS TABLE(id INT, phone_number VARCHAR(15), email VARCHAR(128), first_name VARCHAR(255), last_name VARCHAR(255), password VARCHAR(255),flat_id INT, role VARCHAR(255),verification_status VARCHAR(255), chat_name VARCHAR(255)) AS $$
BEGIN
    IF NOT EXISTS(SELECT 1 FROM users WHERE users.email = p_email OR users.phone_number = p_phone_number) THEN
        RETURN QUERY INSERT INTO users(phone_number, email, password, role, verification_status)
        VALUES (p_phone_number, p_email, p_password, 'ROLE_USER', 'UNVERIFIED')
        RETURNING users.id, users.phone_number, users.email, users.first_name, users.last_name, users.password, users.flat_id,
            users.role, users.verification_status, users.chat_name;
    ELSE
        RAISE EXCEPTION 'User with email % or phone number % already exists', p_email, p_phone_number;
    END IF;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION set_additional_data_to_user(p_id INT, p_first_name VARCHAR(255), p_last_name VARCHAR(255),p_flat_id INT, p_verification_status VARCHAR(255), p_chat_name VARCHAR(255))
RETURNS TABLE(id INT, phone_number VARCHAR(15), email VARCHAR(128), first_name VARCHAR(255), last_name VARCHAR(255), password VARCHAR(255),flat_id INT, role VARCHAR(255),verification_status VARCHAR(255), chat_name VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY UPDATE users
    SET first_name = p_first_name, last_name = p_last_name, flat_id = p_flat_id, verification_status = p_verification_status, chat_name = p_chat_name
    WHERE users.id = p_id
    RETURNING users.id, users.phone_number, users.email, users.first_name, users.last_name, users.password, users.flat_id,
            users.role, users.verification_status, users.chat_name;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION change_user_role(p_id INT, p_role VARCHAR(255))
RETURNS TABLE(id INT, phone_number VARCHAR(15), email VARCHAR(128), first_name VARCHAR(255), last_name VARCHAR(255), password VARCHAR(255),flat_id INT, role VARCHAR(255),verification_status VARCHAR(255), chat_name VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY UPDATE users
    SET role = p_role
    WHERE users.id = p_id
    RETURNING users.id, users.phone_number, users.email, users.first_name, users.last_name, users.password, users.flat_id,
            users.role, users.verification_status, users.chat_name;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION find_user_by_email(p_email VARCHAR(128))
RETURNS TABLE(id INT, phone_number VARCHAR(15), email VARCHAR(128), first_name VARCHAR(255), last_name VARCHAR(255), password VARCHAR(255),flat_id INT, role VARCHAR(255),verification_status VARCHAR(255), chat_name VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY SELECT users.id, users.phone_number, users.email, users.first_name, users.last_name, users.password, users.flat_id,
            users.role, users.verification_status, users.chat_name
    FROM users
    WHERE users.email = p_email;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION find_user_by_phone_number(p_phone_number VARCHAR(15))
RETURNS TABLE(id INT, phone_number VARCHAR(15), email VARCHAR(128), first_name VARCHAR(255), last_name VARCHAR(255), password VARCHAR(255),flat_id INT, role VARCHAR(255),verification_status VARCHAR(255), chat_name VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY SELECT users.id, users.phone_number, users.email, users.first_name, users.last_name, users.password, users.flat_id,
            users.role, users.verification_status, users.chat_name
    FROM users
    WHERE users.phone_number = p_phone_number;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION find_user_by_id(p_id INT)
RETURNS TABLE(id INT, phone_number VARCHAR(15), email VARCHAR(128), first_name VARCHAR(255), last_name VARCHAR(255), password VARCHAR(255),flat_id INT, role VARCHAR(255),verification_status VARCHAR(255), chat_name VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY SELECT users.id, users.phone_number, users.email, users.first_name, users.last_name, users.password, users.flat_id,
            users.role, users.verification_status, users.chat_name
    FROM users
    WHERE users.id = p_id;
END;
$$ LANGUAGE plpgsql;

