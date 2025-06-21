-- ENUM cho account_role_enum (SQL Server không hỗ trợ ENUM, dùng CHECK)
CREATE TABLE account_role_enum (
    role VARCHAR(50) PRIMARY KEY
);
INSERT INTO account_role_enum VALUES ('customer'), ('seller'), ('admin'), ('technician');

-- Bảng manufacture
CREATE TABLE manufacture (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(255),
    country VARCHAR(255),
    website VARCHAR(255),
    contact_email VARCHAR(255),
    contact_phone VARCHAR(255),
    address VARCHAR(MAX)
);

-- Bảng car_type
CREATE TABLE car_type (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(100) UNIQUE,
    description VARCHAR(MAX)
);

-- Bảng engine_type
CREATE TABLE engine_type (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(100) UNIQUE,
    fuel_type VARCHAR(50),
    horsepower INT,
    description VARCHAR(MAX)
);

-- Bảng car_color
CREATE TABLE car_color (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(100) UNIQUE,
    hex_code VARCHAR(7),
    description VARCHAR(MAX)
);

-- Bảng car_model
CREATE TABLE car_model (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(255),
    year INT,
    manufacture_id INT,
    car_type_id INT,
    engine_type_id INT,
    color_id INT,
    FOREIGN KEY (manufacture_id) REFERENCES manufacture(id),
    FOREIGN KEY (car_type_id) REFERENCES car_type(id),
    FOREIGN KEY (engine_type_id) REFERENCES engine_type(id),
    FOREIGN KEY (color_id) REFERENCES car_color(id)
);

-- Bảng car
CREATE TABLE car (
    id INT IDENTITY(1,1) PRIMARY KEY,
    vin VARCHAR(100) UNIQUE,
    import_price DECIMAL(15,2),
    import_date DATE,
    status VARCHAR(50),
    car_model_id INT,
    FOREIGN KEY (car_model_id) REFERENCES car_model(id)
);

-- Bảng account
CREATE TABLE account (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(100) UNIQUE,
    password_hash VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    role VARCHAR(50) CHECK (role IN ('customer', 'seller', 'admin', 'technician')),
    create_date DATETIME DEFAULT GETDATE(),
    update_date DATETIME,
    delete_date DATETIME
);

-- Bảng customer_profile
CREATE TABLE customer_profile (
    account_id INT PRIMARY KEY,
    name VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(MAX),
    gender VARCHAR(10),
    birthdate DATE,
    loyalty_points INT DEFAULT 0,
    shipping_address VARCHAR(MAX),
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE
);

-- Bảng store
CREATE TABLE store (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(100),
    address VARCHAR(MAX)
);

-- Bảng seller_profile
CREATE TABLE seller_profile (
    account_id INT PRIMARY KEY,
    employee_code VARCHAR(50),
    store_id INT,
    total_sales DECIMAL(15,2) DEFAULT 0.0,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
    FOREIGN KEY (store_id) REFERENCES store(id)
);

-- Bảng technician_profile
CREATE TABLE technician_profile (
    account_id INT PRIMARY KEY,
    specialization VARCHAR(MAX),
    certifications VARCHAR(MAX),
    maintenance_count INT DEFAULT 0,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE
);

-- Bảng admin_profile
CREATE TABLE admin_profile (
    account_id INT PRIMARY KEY,
    full_name VARCHAR(100),
    phone VARCHAR(20),
    last_login DATETIME,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE
);

-- Bảng admin_activity_log
CREATE TABLE admin_activity_log (
    id INT IDENTITY(1,1) PRIMARY KEY,
    admin_id INT,
    action VARCHAR(MAX),
    timestamp DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (admin_id) REFERENCES account(id) ON DELETE SET NULL
);

-- Bảng order
CREATE TABLE [order] (
    id INT IDENTITY(1,1) PRIMARY KEY,
    customer_id INT,
    seller_id INT,
    sale_date DATE,
    note VARCHAR(MAX),
    FOREIGN KEY (customer_id) REFERENCES account(id) ON DELETE SET NULL,
    FOREIGN KEY (seller_id) REFERENCES account(id)
);

-- Bảng order_detail
CREATE TABLE order_detail (
    order_id INT,
    car_id INT,
    single_price DECIMAL(15,2),
    discount DECIMAL(5,2),
    PRIMARY KEY (order_id, car_id),
    FOREIGN KEY (order_id) REFERENCES [order](id) ON DELETE CASCADE,
    FOREIGN KEY (car_id) REFERENCES car(id)
);

-- Bảng billing
CREATE TABLE billing (
    id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    payment_method VARCHAR(50),
    payment_status VARCHAR(50) DEFAULT 'paid',
    payment_date DATETIME DEFAULT GETDATE(),
    transaction_reference VARCHAR(100),
    FOREIGN KEY (order_id) REFERENCES [order](id) ON DELETE CASCADE
);

-- Bảng maintenance
CREATE TABLE maintenance (
    id INT IDENTITY(1,1) PRIMARY KEY,
    car_id INT,
    service_date DATE,
    description VARCHAR(MAX),
    cost DECIMAL(15,2),
    performed_by INT,
    type VARCHAR(100),
    FOREIGN KEY (car_id) REFERENCES car(id),
    FOREIGN KEY (performed_by) REFERENCES account(id) ON DELETE SET NULL
);

-- Bảng maintenance_item
CREATE TABLE maintenance_item (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(MAX),
    unit_price DECIMAL(10,2) NOT NULL,
    unit VARCHAR(50)
);

-- Bảng maintenance_detail
CREATE TABLE maintenance_detail (
    maintenance_id INT,
    item_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (maintenance_id, item_id),
    FOREIGN KEY (maintenance_id) REFERENCES maintenance(id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES maintenance_item(id)
);

-- Bảng car_image
CREATE TABLE car_image (
    id INT IDENTITY(1,1) PRIMARY KEY,
    car_id INT,
    image_url VARCHAR(MAX),
    description VARCHAR(255),
    FOREIGN KEY (car_id) REFERENCES car(id) ON DELETE CASCADE
);
