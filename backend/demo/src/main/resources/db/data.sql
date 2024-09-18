-- 使用者範例
INSERT INTO users (phone_number, password, user_name) VALUES
('1234567890', '$2a$10$rPiEAgSMjUFrVxIYhJxQm.0gWa9eFVMFL6CIoHZtjQGjb4gMbDIw2', 'allen'),

-- 書籍範例
INSERT INTO books (isbn, name, author, introduction) VALUES
('9781234567897', 'Spring Boot in Action', '何艾倫', 'A comprehensive guide to Spring Boot'),
('9789876543210', 'Effective Java', '何何艾艾倫倫', 'Best practices for the Java platform');

-- 庫存範例
INSERT INTO inventory (isbn, status) VALUES
('9781234567897', 'available'),
('9789876543210', 'available');

-- 借閱記錄範例
INSERT INTO borrowing_records (user_id, inventory_id, borrowing_time, return_time) VALUES
(1, 1, '2023-01-01 10:00:00', '2023-01-15 14:30:00'),
