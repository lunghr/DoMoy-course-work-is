CREATE OR REPLACE FUNCTION create_admin(p_phone_number VARCHAR(15), p_email VARCHAR(128), p_password VARCHAR(256))
RETURNS TABLE (id INT, phone_number VARCHAR(15), email VARCHAR(128),first_name VARCHAR(255), last_name VARCHAR(255),
               password VARCHAR(255), role VARCHAR(255), chat_name VARCHAR(255)) AS $$
BEGIN
    IF NOT EXISTS(SELECT 1 FROM admins WHERE admins.email = p_email OR admins.phone_number = p_phone_number) THEN
        RETURN QUERY INSERT INTO admins(phone_number, email, password, role)
        VALUES (p_phone_number, p_email, p_password, 'ROLE_ADMIN')
        RETURNING admins.id, admins.phone_number, admins.email, admins.first_name, admins.last_name, admins.password,
            admins.role, admins.chat_name;
    ELSE
        RAISE EXCEPTION 'Admin with email % or phone number % already exists', p_email, p_phone_number;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION delete_admin(p_id INT)
RETURNS TABLE (id INT, phone_number VARCHAR(15), email VARCHAR(128),first_name VARCHAR(255), last_name VARCHAR(255),
               password VARCHAR(255), role VARCHAR(255), chat_name VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY DELETE FROM admins WHERE admins.id = p_id
    RETURNING admins.id, admins.phone_number, admins.email, admins.first_name, admins.last_name, admins.password,
            admins.role, admins.chat_name;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION set_additional_data_to_admin(p_id INT, p_first_name VARCHAR(255), p_last_name VARCHAR(255), p_chat_name VARCHAR(255))
RETURNS TABLE (id INT, phone_number VARCHAR(15), email VARCHAR(128),first_name VARCHAR(255), last_name VARCHAR(255),
               password VARCHAR(255), role VARCHAR(255), chat_name VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY UPDATE admins
    SET first_name = p_first_name, last_name = p_last_name, chat_name = p_chat_name
    WHERE admins.id = p_id
    RETURNING admins.id, admins.phone_number, admins.email, admins.first_name, admins.last_name, admins.password,
            admins.role, admins.chat_name;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_admin_by_email(p_email VARCHAR(128))
RETURNS TABLE (id INT, phone_number VARCHAR(15), email VARCHAR(128),first_name VARCHAR(255), last_name VARCHAR(255),
               password VARCHAR(255), role VARCHAR(255), chat_name VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY SELECT admins.id, admins.phone_number, admins.email, admins.first_name, admins.last_name, admins.password,
            admins.role, admins.chat_name
    FROM admins
    WHERE admins.email = p_email;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_admin_by_phone_number(p_phone_number VARCHAR(15))
RETURNS TABLE (id INT, phone_number VARCHAR(15), email VARCHAR(128),first_name VARCHAR(255), last_name VARCHAR(255),
               password VARCHAR(255), role VARCHAR(255), chat_name VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY SELECT admins.id, admins.phone_number, admins.email, admins.first_name, admins.last_name, admins.password,
            admins.role, admins.chat_name
    FROM admins
    WHERE admins.phone_number = p_phone_number;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_all_admins()
RETURNS TABLE (id INT, phone_number VARCHAR(15), email VARCHAR(128),first_name VARCHAR(255), last_name VARCHAR(255),
               password VARCHAR(255), role VARCHAR(255), chat_name VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY SELECT admins.id, admins.phone_number, admins.email, admins.first_name, admins.last_name, admins.password,
            admins.role, admins.chat_name
    FROM admins;
END;
$$ LANGUAGE plpgsql;