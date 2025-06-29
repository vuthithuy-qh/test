/********************************************************************************
* SCRIPT TẠO DỮ LIỆU TEST - PHIÊN BẢN 9 (CUỐI CÙNG - TƯỜNG MINH)
*
* THAY ĐỔI:
* - Loại bỏ hoàn toàn thủ tục hệ thống sp_MSforeachtable.
* - Thay thế bằng các lệnh DELETE tường minh cho từng bảng.
* -> Đây là cách làm an toàn và chắc chắn nhất.
*
*********************************************************************************/

-- Đảm bảo các cài đặt phiên làm việc là đúng chuẩn
SET ANSI_NULLS ON;
SET QUOTED_IDENTIFIER ON;
GO

-- Bọc toàn bộ trong một TRANSACTION để đảm bảo an toàn
BEGIN TRANSACTION;
BEGIN TRY

    -- === PHẦN 1: DỌN DẸP DỮ LIỆU CŨ (PHIÊN BẢN TƯỜNG MINH) ===
    PRINT '--- Bat dau don dep du lieu cu...';
    
    -- Xóa theo thứ tự ngược của khóa ngoại để không bị lỗi
    DELETE FROM billing;
    DELETE FROM order_detail;
    DELETE FROM [order];
    DELETE FROM maintenance_detail;
    DELETE FROM maintenance;
    DELETE FROM car_image;
    DELETE FROM seller_profile;
    DELETE FROM technician_profile;
    DELETE FROM admin_activity_log;
    DELETE FROM admin_profile;
    DELETE FROM customer_profile;
    DELETE FROM car;
    DELETE FROM car_model;
    DELETE FROM manufacture;
    DELETE FROM car_type;
    DELETE FROM engine_type;
    DELETE FROM car_color;
    DELETE FROM store;
    DELETE FROM maintenance_item;
    DELETE FROM account; -- Xóa cả account để làm lại từ đầu

    PRINT '--- Don dep du lieu cu thanh cong.';

    -- === PHẦN 2: RESET BỘ ĐẾM ID VỀ 0 ===
    PRINT '--- Resetting ID counters...';
    DBCC CHECKIDENT ('billing', RESEED, 0);
    DBCC CHECKIDENT ('[order]', RESEED, 0);
    DBCC CHECKIDENT ('car', RESEED, 0);
    DBCC CHECKIDENT ('car_model', RESEED, 0);
    DBCC CHECKIDENT ('manufacture', RESEED, 0);
    DBCC CHECKIDENT ('car_type', RESEED, 0);
    DBCC CHECKIDENT ('engine_type', RESEED, 0);
    DBCC CHECKIDENT ('car_color', RESEED, 0);
    DBCC CHECKIDENT ('account', RESEED, 0);
    PRINT '--- Reset ID thanh cong.';


    -- === PHẦN 3: THÊM DỮ LIỆU TEST MỚI ===
    PRINT '--- Bat dau them du lieu test moi...';

    -- 3.1. TẠO TÀI KHOẢN
    SET IDENTITY_INSERT account ON;
    INSERT INTO account (id, username, password_hash, email, role, create_date) VALUES
    (1, 'thuy', '1234', 'thuy@gmail.com', 'customer', GETDATE()),
    (2, 'seller_test', '123', 'seller@shop.com', 'seller', GETDATE());
    SET IDENTITY_INSERT account OFF;

    -- 3.2. TẠO CUSTOMER PROFILE CHO 'thuy'
    INSERT INTO customer_profile(account_id, name, phone, address, gender, birthdate, loyalty_points, shipping_address)
    VALUES (1, 'Vu Thi Thuy', '0865104205', 'Thai Binh', 'Female', '2005-07-12', 100, 'Ha Noi, Viet Nam');

    -- 3.3. TẠO DỮ LIỆU NỀN TẢNG (CATALOG)
    INSERT INTO manufacture (name, country) VALUES ('Toyota', 'Japan'), ('Honda', 'Japan');
    INSERT INTO car_type (name) VALUES ('Sedan'), ('SUV');
    INSERT INTO engine_type (name, fuel_type, horsepower) VALUES ('2.5L I4', 'Gasoline', 203), ('1.5L Turbo', 'Gasoline', 190);
    INSERT INTO car_color (name, hex_code) VALUES ('Black', '#000000'), ('White', '#FFFFFF');
    
    -- 3.4. TẠO MẪU XE
    INSERT INTO car_model (name, year, manufacture_id, car_type_id, engine_type_id, color_id) VALUES
    ('Toyota Camry 2025', 2025, 1, 1, 1, 1),
    ('Honda CR-V 2024', 2024, 2, 2, 2, 2);

    -- 3.5. TẠO XE CỤ THỂ
    INSERT INTO car (vin, import_price, selling_price, import_date, status, car_model_id) VALUES
    ('VIN_TEST_001', 950000000, 1092500000, '2025-01-10', 'AVAILABLE', 1),
    ('VIN_TEST_002', 960000000, 1104000000, '2025-01-11', 'AVAILABLE', 1),
    ('VIN_TEST_003', 1100000000, 1265000000, '2024-11-20', 'AVAILABLE', 2);
    
    -- 3.6. TẠO CÁC ĐƠN HÀNG CHO 'thuy' (account_id = 1)
    DECLARE @customerId BIGINT = 1;
    DECLARE @sellerId BIGINT = 2;
    DECLARE @order_id INT;
    INSERT INTO [order] (customer_id, seller_id, sale_date, note, status) VALUES (@customerId, @sellerId, GETDATE(), 'Giao hang test 1.', 'Pending');
    SET @order_id = SCOPE_IDENTITY();
    INSERT INTO order_detail (order_id, car_id, single_price, discount) VALUES (@order_id, 1, 1092500000, 0);
    INSERT INTO billing (order_id, amount, payment_mothod, payment_status, payment_date) VALUES (@order_id, 1092500000, 'CASH', 'PAID', GETDATE());
    UPDATE car SET status = 'SOLD' WHERE id = 1;

    INSERT INTO [order] (customer_id, seller_id, sale_date, note, status) VALUES (@customerId, @sellerId, '2025-04-15', 'Giao hang test 2.', 'Completed');
    SET @order_id = SCOPE_IDENTITY();
    INSERT INTO order_detail (order_id, car_id, single_price, discount) VALUES (@order_id, 3, 1265000000, 0);
    INSERT INTO billing (order_id, amount, payment_mothod, payment_status, payment_date) VALUES (@order_id, 1265000000, 'CREDIT_CARD', 'PAID', '2025-04-15');
    UPDATE car SET status = 'SOLD' WHERE id = 3;

    PRINT '--- Them du lieu test moi thanh cong! ---';

    COMMIT TRANSACTION;
END TRY
BEGIN CATCH
    IF @@TRANCOUNT > 0
        ROLLBACK TRANSACTION;

    PRINT '--- DA CO LOI XAY RA, TOAN BO GIAO DICH DA DUOC HUY BO. ---';
    THROW;
END CATCH
GO