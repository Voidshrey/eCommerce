INSERT INTO product (name, description, price, image_url)
VALUES
('Laptop', 'High performance laptop', 75000.00, 'https://example.com/laptop.jpg'),
('Smartphone', 'Latest Android smartphone', 35000.00, 'https://example.com/smartphone.jpg'),
('Headphones', 'Noise cancelling headphones', 5000.00, 'https://example.com/headphones.jpg');

-- 1. Insert Users into the 'users' table
INSERT INTO users (username, email, password_hash, created_at) VALUES
('johndoe', 'john@example.com', '$2a$12$K3vW76...', NOW()),
('janedoe', 'jane@example.com', '$2a$12$R9bX12...', NOW());

-- 2. Insert Carts into the 'cart' table (Linked to user_id)
-- Assuming johndoe gets id=1 and janedoe gets id=2
INSERT INTO cart (user_id) VALUES
(1), -- Cart for johndoe (Will get cart id=1)
(2); -- Cart for janedoe (Will get cart id=2)

-- 3. Insert Cart Items into the 'cart_items' table
-- Linking Cart 1 (John) and Cart 2 (Jane) to your inserted Products (Laptop=1, Smartphone=2, Headphones=3)
INSERT INTO cart_items (cart_id, product_id, quantity) VALUES
(1, 1, 1), -- John has 1 Laptop in his cart
(1, 3, 2), -- John also has 2 Headphones in his cart
(2, 2, 1); -- Jane has 1 Smartphone in her cart