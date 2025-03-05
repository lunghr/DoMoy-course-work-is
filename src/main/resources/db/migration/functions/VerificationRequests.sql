CREATE OR REPLACE FUNCTION create_verification_request(p_first_name VARCHAR(255), p_last_name VARCHAR(255),
                                                      p_cadastral_number VARCHAR(255), p_address VARCHAR(255),
                                                      p_flat_number INT, p_user_id INT)
RETURNS TABLE(id INT, first_name VARCHAR(255), last_name VARCHAR(255),
              cadastral_number VARCHAR(255), address VARCHAR(255),
              flat_number INT, user_id INT, status VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY
    INSERT INTO verification_requests (first_name, last_name, cadastral_number, address, flat_number, user_id, status)
    VALUES (p_first_name, p_last_name, p_cadastral_number, p_address, p_flat_number, p_user_id, 'PENDING')
    RETURNING verification_requests.id, verification_requests.first_name, verification_requests.last_name,
              verification_requests.cadastral_number, verification_requests.address,
              verification_requests.flat_number, verification_requests.user_id, verification_requests.status;
END;
$$ LANGUAGE plpgsql;




CREATE OR REPLACE FUNCTION save_verification_request(p_id INT,p_first_name VARCHAR(255), p_last_name VARCHAR(255),
                                                    p_cadastral_number VARCHAR(255), p_address VARCHAR(255),
                                                    p_flat_number INT)
RETURNS TABLE(id INT, first_name VARCHAR(255), last_name VARCHAR(255),
              cadastral_number VARCHAR(255), address VARCHAR(255),
              flat_number INT, user_id INT, status VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY
    UPDATE verification_requests
    SET first_name = p_first_name, last_name = p_last_name, cadastral_number = p_cadastral_number,
        address = p_address, flat_number = p_flat_number, status = 'PENDING'
    WHERE verification_requests.id = p_id
    RETURNING verification_requests.id, verification_requests.first_name, verification_requests.last_name,
              verification_requests.cadastral_number, verification_requests.address,
              verification_requests.flat_number, verification_requests.user_id, verification_requests.status;
END;
$$ LANGUAGE plpgsql;




CREATE OR REPLACE FUNCTION change_verification_request_status(p_id INT, p_status VARCHAR(255))
RETURNS TABLE(id INT, first_name VARCHAR(255), last_name VARCHAR(255),
              cadastral_number VARCHAR(255), address VARCHAR(255),
              flat_number INT, user_id INT, status VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY
    UPDATE verification_requests
    SET status = p_status
    WHERE verification_requests.id = p_id
    RETURNING verification_requests.id, verification_requests.first_name, verification_requests.last_name,
              verification_requests.cadastral_number, verification_requests.address,
              verification_requests.flat_number, verification_requests.user_id, verification_requests.status;
END;
$$ LANGUAGE plpgsql;




CREATE OR REPLACE FUNCTION find_verification_request_by_id(p_id INT)
RETURNS TABLE(id INT, first_name VARCHAR(255), last_name VARCHAR(255),
              cadastral_number VARCHAR(255), address VARCHAR(255),
              flat_number INT, user_id INT, status VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY
    SELECT vr.id, vr.first_name, vr.last_name, vr.cadastral_number, vr.address, vr.flat_number, vr.user_id, vr.status
    FROM verification_requests vr
    WHERE vr.id = p_id;
END;
$$ LANGUAGE plpgsql;




CREATE OR REPLACE FUNCTION find_verification_requests_by_user_id(p_user_id INT)
RETURNS TABLE(id INT, first_name VARCHAR(255), last_name VARCHAR(255),
              cadastral_number VARCHAR(255), address VARCHAR(255),
              flat_number INT, user_id INT, status VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY
    SELECT vr.id, vr.first_name, vr.last_name, vr.cadastral_number, vr.address, vr.flat_number, vr.user_id, vr.status
    FROM verification_requests vr
    WHERE vr.user_id = p_user_id;
END;
$$ LANGUAGE plpgsql;



