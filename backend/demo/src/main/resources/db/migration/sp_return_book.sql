CREATE PROCEDURE sp_return_book(
    IN p_user_id INT,
    IN p_inventory_id INT
)
BEGIN
    DECLARE rows_affected INT;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'An error occurred while returning the book.';
    END;

    START TRANSACTION;
    
    UPDATE borrowing_records 
    SET return_time = NOW()
    WHERE user_id = p_user_id AND inventory_id = p_inventory_id AND return_time IS NULL;
    
    SET rows_affected = ROW_COUNT();
    
    IF rows_affected = 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'No matching borrowing record found or book already returned.';
    ELSE
        UPDATE inventory SET status = 'available' WHERE id = p_inventory_id;
        COMMIT;
    END IF;
END