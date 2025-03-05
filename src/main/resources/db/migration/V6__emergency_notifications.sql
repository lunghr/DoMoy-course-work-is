CREATE OR REPLACE FUNCTION create_emergency_notification(p_title VARCHAR(255), p_description VARCHAR(255),
                                                         p_house_id INT, p_admin_id INT)
RETURNS TABLE(id INT, title VARCHAR(255), description VARCHAR(255), date TIMESTAMP, house_id INT, admin_id INT) AS $$
BEGIN
    RETURN QUERY INSERT INTO emergency_notifications(title, description, date, house_id, admin_id)
    VALUES (p_title, p_description, NOW(), p_house_id, p_admin_id)
    RETURNING emergency_notifications.id, emergency_notifications.title, emergency_notifications.description,
              emergency_notifications.date, emergency_notifications.house_id, emergency_notifications.admin_id;
END;
$$ LANGUAGE plpgsql;

