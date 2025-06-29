-- PHẦN 0: TẮT CONSTRAINT & XÓA DỮ LIỆU
EXEC sp_MSforeachtable 'ALTER TABLE ? NOCHECK CONSTRAINT ALL';
EXEC sp_MSforeachtable 'DELETE FROM ?';

-- PHẦN 1: RESET IDENTITY
DBCC CHECKIDENT ('billing', RESEED, 0);
DBCC CHECKIDENT ('[order]', RESEED, 0);
DBCC CHECKIDENT ('car', RESEED, 0);
DBCC CHECKIDENT ('car_model', RESEED, 0);
DBCC CHECKIDENT ('manufacture', RESEED, 0);
DBCC CHECKIDENT ('car_type', RESEED, 0);
DBCC CHECKIDENT ('engine_type', RESEED, 0);
DBCC CHECKIDENT ('car_color', RESEED, 0);
DBCC CHECKIDENT ('account', RESEED, 0);

-- PHẦN 2: FIX CONSTRAINT ENUM (chỉnh nếu bị giới hạn giá trị)
-- Nếu constraint đã tồn tại thì cần DROP, sau đó ADD lại:

-- Thêm constraint mới cho order.status
ALTER TABLE [order] DROP CONSTRAINT IF EXISTS CK__order__status;
ALTER TABLE [order]
ADD CONSTRAINT CK__order__status
CHECK (status IN ('PENDING', 'PROCESSING', 'SHIPPING', 'COMPLETED', 'CANCELLED'));

-- Thêm constraint mới cho billing.payment_status
ALTER TABLE billing DROP CONSTRAINT IF EXISTS CK__billing__payment_status;
ALTER TABLE billing
ADD CONSTRAINT CK__billing__payment_status
CHECK (payment_status IN ('PENDING', 'PAID', 'FAILED', 'SHIPPING', 'REFUNDED', 'CANCELLED'));

-- PHẦN 3: TẠO DỮ LIỆU TEST
-- 3.1 Tài khoản
SET IDENTITY_INSERT account ON;
INSERT INTO account (id, username, password_hash, email, role, create_date) VALUES
(1, 'thuy', '1234', 'thuy@gmail.com', 'customer', GETDATE()),
(2, 'seller_test', '123', 'seller@shop.com', 'seller', GETDATE());
SET IDENTITY_INSERT account OFF;

-- 3.2 Customer profile
INSERT INTO customer_profile(account_id, name, phone, address, gender, birthdate, loyalty_points, shipping_address)
VALUES (1, 'Vu Thi Thuy', '0865104205', 'Thai Binh', 'Female', '2005-07-12', 1500, 'Ha Noi, Viet Nam');

-- 3.3 Catalog
INSERT INTO manufacture (name, country) VALUES ('Toyota', 'Japan'), ('Honda', 'Japan'), ('Ford', 'USA'), ('Mercedes-Benz', 'Germany');
INSERT INTO car_type (name) VALUES ('Sedan'), ('SUV'), ('Hatchback'), ('Sport');
INSERT INTO engine_type (name, fuel_type, horsepower) VALUES ('2.0L I4', 'Gasoline', 180), ('1.5L Turbo', 'Gasoline', 190), ('3.0L V6', 'Gasoline', 350);
INSERT INTO car_color (name, hex_code) VALUES ('Black', '#000000'), ('White', '#FFFFFF'), ('Grey', '#808080'), ('Red', '#FF0000');

-- 3.4 Car Models
INSERT INTO car_model (name, year, manufacture_id, car_type_id, engine_type_id, color_id) VALUES
('Toyota Camry 2025', 2025, 1, 1, 1, 1),
('Honda CR-V 2024', 2024, 2, 2, 2, 2),
('Ford Ranger 2023', 2023, 3, 2, 3, 4),
('Mercedes C300', 2025, 4, 1, 3, 2);

-- 3.5 Car
INSERT INTO car (vin, import_price, selling_price, import_date, status, car_model_id) VALUES
('VIN_SOLD_01', 950000000, 1092000000, '2025-01-10', 'AVAILABLE', 1),
('VIN_SOLD_02', 1100000000, 1265000000, '2024-11-20', 'AVAILABLE', 2),
('VIN_SOLD_03', 1300000000, 1500000000, '2025-02-01', 'AVAILABLE', 4),
('VIN_SOLD_04', 1320000000, 1520000000, '2025-02-05', 'AVAILABLE', 4),
('VIN_SOLD_05', 980000000, 1130000000, '2025-03-10', 'AVAILABLE', 1),
('VIN_AVAIL_01', 960000000, 1100000000, '2025-01-11', 'AVAILABLE', 1),
('VIN_AVAIL_02', 1120000000, 1288000000, '2024-11-22', 'AVAILABLE', 2),
('VIN_AVAIL_03', 800000000, 950000000, '2023-05-15', 'AVAILABLE', 3);

-- 3.6 Orders
DECLARE @customerId INT = 1;
DECLARE @sellerId INT = 2;
DECLARE @order_id INT;

-- Order 1: PENDING
INSERT INTO [order] (customer_id, seller_id, sale_date, note, status) VALUES (@customerId, @sellerId, GETDATE(), 'Don hang dang cho xu ly', 'PENDING');
SET @order_id = SCOPE_IDENTITY();
INSERT INTO order_detail (order_id, car_id, single_price) VALUES (@order_id, 1, (SELECT selling_price FROM car WHERE id=1));
INSERT INTO billing (order_id, amount, payment_method, payment_status) VALUES (@order_id, (SELECT selling_price FROM car WHERE id=1), 'CASH', 'PENDING');

-- Order 2: PROCESSING
INSERT INTO [order] (customer_id, seller_id, sale_date, note, status) VALUES (@customerId, @sellerId, DATEADD(day, -1, GETDATE()), 'Don hang dang dong goi', 'PROCESSING');
SET @order_id = SCOPE_IDENTITY();
INSERT INTO order_detail (order_id, car_id, single_price) VALUES (@order_id, 2, (SELECT selling_price FROM car WHERE id=2));
INSERT INTO billing (order_id, amount, payment_method, payment_status) VALUES (@order_id, (SELECT selling_price FROM car WHERE id=2), 'CREDIT_CARD', 'PAID');

-- Order 3: SHIPPING
INSERT INTO [order] (customer_id, seller_id, sale_date, note, status) VALUES (@customerId, @sellerId, DATEADD(day, -5, GETDATE()), 'Don hang dang giao', 'SHIPPING');
SET @order_id = SCOPE_IDENTITY();
INSERT INTO order_detail (order_id, car_id, single_price) VALUES (@order_id, 3, (SELECT selling_price FROM car WHERE id=3));
INSERT INTO billing (order_id, amount, payment_method, payment_status) VALUES (@order_id, (SELECT selling_price FROM car WHERE id=3), 'E_WALLET', 'PAID');

-- Order 4: COMPLETED
INSERT INTO [order] (customer_id, seller_id, sale_date, note, status) VALUES (@customerId, @sellerId, '2025-05-20', 'Don hang da hoan thanh', 'COMPLETED');
SET @order_id = SCOPE_IDENTITY();
INSERT INTO order_detail (order_id, car_id, single_price) VALUES (@order_id, 4, (SELECT selling_price FROM car WHERE id=4));
INSERT INTO billing (order_id, amount, payment_method, payment_status) VALUES (@order_id, (SELECT selling_price FROM car WHERE id=4), 'CASH', 'PAID');

-- Order 5: CANCELLED
INSERT INTO [order] (customer_id, seller_id, sale_date, note, status) VALUES (@customerId, @sellerId, '2025-04-10', 'Don hang da bi huy', 'CANCELLED');
SET @order_id = SCOPE_IDENTITY();
INSERT INTO order_detail (order_id, car_id, single_price) VALUES (@order_id, 5, (SELECT selling_price FROM car WHERE id=5));
INSERT INTO billing (order_id, amount, payment_method, payment_status) VALUES (@order_id, (SELECT selling_price FROM car WHERE id=5), 'BANK_TRANSFER', 'REFUNDED');

-- 3.7 Cập nhật trạng thái xe đã bán
UPDATE car SET status = 'SOLD' WHERE id IN (1, 2, 3, 4, 5);

-- PHẦN 4: BẬT LẠI CONSTRAINT
EXEC sp_MSforeachtable 'ALTER TABLE ? WITH CHECK CHECK CONSTRAINT ALL';
