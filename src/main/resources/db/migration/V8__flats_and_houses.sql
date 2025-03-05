CREATE OR REPLACE FUNCTION create_flat(p_house_id INT, p_flat_number INT, p_cadastral_number VARCHAR(255), p_owner_id INT)
RETURNS TABLE (id INT, house_id INT, flat_number INT, cadastral_number VARCHAR(255), owner_id INT) AS $$
BEGIN
    IF NOT EXISTS(SELECT 1 FROM flats f WHERE f.house_id = p_house_id AND f.flat_number = p_flat_number) THEN
        RETURN QUERY INSERT INTO flats (house_id, flat_number, cadastral_number, owner_id)
            VALUES (p_house_id, p_flat_number, p_cadastral_number, p_owner_id)
            RETURNING flats.id, flats.house_id, flats.flat_number, flats.cadastral_number, flats.owner_id;
    ELSE
        RAISE EXCEPTION 'Flat with house_id % and flat_number % already exists', p_house_id, p_flat_number;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_flat_by_house_and_flat_number(p_house_address VARCHAR(255), p_flat_number INT)
RETURNS TABLE (id INT, house_id INT, flat_number INT, cadastral_number VARCHAR(255), owner_id INT) AS $$
BEGIN
    RETURN QUERY
    SELECT f.id, f.house_id, f.flat_number, f.cadastral_number, f.owner_id
    FROM flats f
             JOIN houses h ON f.house_id = h.id
    WHERE h.address = p_house_address AND f.flat_number = p_flat_number;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION create_house(p_address VARCHAR(255))
    RETURNS TABLE (id INT, address VARCHAR(255)) AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM houses WHERE houses.address = p_address) THEN
        RETURN QUERY
            INSERT INTO houses (address)
                VALUES (p_address)
                RETURNING houses.id, houses.address;
    ELSE
        RAISE EXCEPTION 'House with address % already exists', p_address;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_house_by_address(p_address VARCHAR(255))
RETURNS TABLE (id INT, address VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY
    SELECT h.id, h.address
    FROM houses h
    WHERE h.address = p_address;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_house_by_id(p_id INT)
RETURNS TABLE (id INT, address VARCHAR(255)) AS $$
BEGIN
    RETURN QUERY
    SELECT h.id, h.address
    FROM houses h
    WHERE h.id = p_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_flats_by_house_id(p_house_id INT)
RETURNS TABLE (flat_number INT) AS $$
BEGIN
    RETURN QUERY
    SELECT f.flat_number
    FROM flats f
    WHERE f.house_id = p_house_id;
END;
$$ LANGUAGE plpgsql;
