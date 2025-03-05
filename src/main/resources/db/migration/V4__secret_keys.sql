CREATE OR REPLACE FUNCTION create_secret_key(p_key VARCHAR(12))
RETURNS TABLE(id INT, key VARCHAR(12), is_used BOOLEAN, admin_id INT) AS $$
BEGIN
    RETURN QUERY INSERT INTO secret_keys (key, is_used)
    VALUES (p_key, FALSE)
    RETURNING secret_keys.id, secret_keys.key, secret_keys.is_used, secret_keys.admin_id;
END;
$$ LANGUAGE plpgsql;




CREATE OR REPLACE FUNCTION find_secret_key_by_key(p_key VARCHAR(12))
RETURNS TABLE(id INT, key VARCHAR(12), is_used BOOLEAN, admin_id INT) AS $$
BEGIN
    RETURN QUERY
    SELECT sk.id, sk.key, sk.is_used, sk.admin_id
    FROM secret_keys sk
    WHERE sk.key = p_key;
END;
$$ LANGUAGE plpgsql;




CREATE OR REPLACE FUNCTION use_key(p_key VARCHAR(12), p_admin_id INT)
RETURNS TABLE(id INT, key VARCHAR(12), is_used BOOLEAN, admin_id INT) AS $$
BEGIN
    RETURN QUERY
    UPDATE secret_keys
    SET is_used = TRUE, admin_id = p_admin_id
    WHERE secret_keys.key = p_key
    RETURNING secret_keys.id, secret_keys.key, secret_keys.is_used, secret_keys.admin_id;
END;
$$ LANGUAGE plpgsql;

