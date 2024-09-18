CREATE PROCEDURE sp_register_user(
    IN p_phone_number VARCHAR(20),
    IN p_password VARCHAR(255),
    IN p_user_name VARCHAR(100)
)
BEGIN
    INSERT INTO users (phone_number, password, user_name, registration_time)
    VALUES (p_phone_number, p_password, p_user_name, NOW());
END;
