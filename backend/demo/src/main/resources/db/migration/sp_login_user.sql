CREATE PROCEDURE `sp_login_user`(
    IN p_phone_number VARCHAR(20)
)
BEGIN
    SELECT id, phone_number, password, user_name, last_login_time
    FROM users
    WHERE phone_number = p_phone_number;
    
    UPDATE users SET last_login_time = NOW() WHERE phone_number = p_phone_number;
END