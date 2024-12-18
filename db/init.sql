CREATE DATABASE FinalProj_DBMS;

USE FinalProj_DBMS;

-- Create the Users table
CREATE TABLE users (
    customer_id CHAR(3) PRIMARY KEY,
    username VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(50) NOT NULL,
    address TEXT,
    phone_number VARCHAR(11)
);

-- Create the Yarns table
CREATE TABLE yarns (
    product_id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    material VARCHAR(30),
    hook_size VARCHAR(20),
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL
);

-- Create the Crochet Tools table
CREATE TABLE crochet_tools (
    product_id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL
);

-- Create the Crocheted Items table
CREATE TABLE crocheted_items (
    product_id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    color VARCHAR(50),
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL
);

-- Create the Orders table
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id CHAR(3) NOT NULL,
    product_id VARCHAR(10) NOT NULL,
    product_type ENUM('tool', 'yarn', 'item') NOT NULL,
    username VARCHAR(20) NOT NULL,
    name VARCHAR(30) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO yarns (product_id, name, material, hook_size, price, stock)
VALUES
	('Y01', 'Cotton Yarn', '100% cotton fibers', '4.0 mm = 6.0 mm', 25.00, 10),
    ('Y02', 'Velvet Yarn', 'Polyester or nylon', '5.0 mm - 6.0 mm', 70.00, 20),
    ('Y03', 'Indophil Yarn', 'Polyester or cotton', '4.0 mm - 5.5 mm', 50.00, 5),
    ('Y04', 'Acrylic Yarn', '100% acrylic fibers', '4.0 mm - 5.5 mm', 20.00, 10),
    ('Y05', 'Polyester Yarn', '100% polyester fibers', '4.0 mm - 6.0 mm',  60.00, 5),
    ('Y06', 'Mohair Yarn', '70-100% mohair', '4.0 mm - 5.5 mm', 80.00, 5),
    ('Y07', 'Chunky Yarn', 'Wool, acrylic, and polyester', '8.0 mm - 11.5 mm', 55.00, 15),
    ('Y08', 'Chenille Yarn', 'Polyester, nylon, or cotton', '4.0 mm - 5.5 mm', 70.00, 10),
    ('Y09', 'T-Shirt Yarn', 'Recycled fabric strips', '9.0 mm - 11.5 mm', 150.00, 3);
    
INSERT INTO crochet_tools (product_id, name, description, price, stock)
VALUES
	('T01', 'Crochet Hook', 'A basic tool for crocheting', 12.00, 20),
    ('T02', 'Scissor', 'Small, sharp scissor used for cutting yarn', 6.00, 10),
    ('T03', 'Stitch Marker', 'Small clips used to mark stitches', 1.00, 50),
    ('T04', 'Tapestry Needle', 'A blunt-ended needle used to weave in yarn', 2.00, 15),
    ('T05', 'Measuring Tape', 'Used to measure yarn, gauge, or finished projects', 7.00, 5),
    ('T06', 'Yarn Winder', 'A tool to wind yarn into a neat ball', 40.00, 5),
    ('T07', 'Row Counter', 'A tool for keeping track of rows completed', 20.00, 5),
    ('T08', 'Stuffing (Polyfill)', 'A fluffy material used to stuff projects', 10.00, 10),
    ('T09', 'Safety Eyes and Nose', 'A durable plastic eyes and nose with secure backings', 10.00, 50),
    ('T10', 'Buttons', 'Decorative or functional fasteners', 1.00, 20);
    
INSERT INTO crocheted_items (product_id, name, color, price, stock)
VALUES
	('I01', 'Tote Bag', 'Off-white', 750.00, 10),
    ('I02', 'Headband', 'Light Blue', 100.00, 20),
    ('I03', 'Beanie', 'Black', 150.00, 5),
    ('I04', 'Coaster', 'Red', 60, 10),
    ('I05', 'Scarf', 'Navy Blue', 450.00, 5),
    ('I06', 'Bear Plushie', 'White', 500.00, 5),
    ('I07', 'Flower Keychain', 'Yellow', 75.00, 15),
    ('I08', 'Tank Top', 'Light Orange', 300.00, 10),
    ('I09', 'Sweater', 'Beige', 1200.00, 3);