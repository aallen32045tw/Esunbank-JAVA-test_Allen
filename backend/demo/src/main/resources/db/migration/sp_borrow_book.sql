CREATE PROCEDURE sp_borrow_book(
    IN p_user_id INT,
    IN p_inventory_id INT
)
BEGIN
    DECLARE book_status VARCHAR(20);
    DECLARE is_borrowed INT;
    
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'An error occurred while borrowing the book.';
    END;

    START TRANSACTION;
    
    SELECT status INTO book_status FROM inventory WHERE id = p_inventory_id;
    
    SELECT COUNT(*) INTO is_borrowed 
    FROM borrowing_records 
    WHERE user_id = p_user_id AND inventory_id = p_inventory_id AND return_time IS NULL;

    IF book_status = 'available' AND is_borrowed = 0 THEN
        UPDATE inventory SET status = 'borrowed' WHERE id = p_inventory_id;
        INSERT INTO borrowing_records (user_id, inventory_id, borrowing_time)
        VALUES (p_user_id, p_inventory_id, NOW());
        COMMIT;
    ELSE
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Book is not available or already borrowed by this user.';
    END IF;
END