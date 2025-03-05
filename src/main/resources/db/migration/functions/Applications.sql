CREATE OR REPLACE FUNCTION create_application(p_user_id INT, p_theme VARCHAR(255), p_title VARCHAR(255),
                                              p_description VARCHAR)
    RETURNS TABLE (id INT, status VARCHAR(255), user_id INT, theme VARCHAR(255), title VARCHAR(255),
                   description VARCHAR, created_at TIMESTAMP) AS $$
BEGIN
    RETURN QUERY INSERT INTO applications (status, user_id, theme, title, description, created_at)
    VALUES ('NEW', p_user_id, p_theme, p_title, p_description, NOW())
    RETURNING applications.id, applications.status, applications.user_id, applications.theme, applications.title,
              applications.description, applications.created_at;
END;
$$ LANGUAGE plpgsql;




CREATE OR REPLACE FUNCTION find_application_by_id(p_application_id INT)
    RETURNS TABLE (id INT, status VARCHAR(255), user_id INT, theme VARCHAR(255), title VARCHAR(255),
                   description VARCHAR, created_at TIMESTAMP) AS $$
BEGIN
    RETURN QUERY SELECT applications.id, applications.status, applications.user_id, applications.theme,
                         applications.title, applications.description, applications.created_at
                 FROM applications
                 WHERE applications.id = p_application_id;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION change_application_status(p_application_id INT, p_status VARCHAR(255))
    RETURNS TABLE (id INT, status VARCHAR(255), user_id INT, theme VARCHAR(255), title VARCHAR(255),
                   description VARCHAR, created_at TIMESTAMP) AS $$
BEGIN
    RETURN QUERY UPDATE applications
    SET status = p_status
    WHERE applications.id = p_application_id
    RETURNING applications.id, applications.status, applications.user_id, applications.theme, applications.title,
              applications.description, applications.created_at;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION create_application_response(p_application_id INT, p_admin_id INT, p_response VARCHAR,
                                                       p_status VARCHAR(255))
    RETURNS TABLE (id INT, application_id INT, admin_id INT, response VARCHAR, status VARCHAR(255), date TIMESTAMP) AS $$
BEGIN
    RETURN QUERY INSERT INTO application_responses (application_id, admin_id, response, status, date)
    VALUES (p_application_id, p_admin_id, p_response, p_status, NOW())
    RETURNING application_responses.id, application_responses.application_id, application_responses.admin_id,
              application_responses.response, application_responses.status, application_responses.date;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION find_all_responses_by_application_id(p_application_id INT)
    RETURNS TABLE (id INT, application_id INT, admin_id INT, response VARCHAR, status VARCHAR(255), date TIMESTAMP) AS $$
BEGIN
    RETURN QUERY SELECT application_responses.id, application_responses.application_id, application_responses.admin_id,
                         application_responses.response, application_responses.status, application_responses.date
                 FROM application_responses
                 WHERE application_responses.application_id = p_application_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_file_to_application(p_application_id INT, p_file_name VARCHAR)
    RETURNS TABLE (id INT, application_id INT, file_name VARCHAR) AS $$
BEGIN
    RETURN QUERY INSERT INTO applications_filenames (application_id, file_name)
    VALUES (p_application_id, p_file_name)
    RETURNING applications_filenames.id, applications_filenames.application_id, applications_filenames.file_name;
END;
$$ LANGUAGE plpgsql;