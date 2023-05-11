/* SQL DIALECT: PostgreSQL */
/***** CUSTOM DATA *****/
INSERT INTO Product_categories (name)               VALUES ('PC & tablets');
INSERT INTO Product_categories (name, parent_id)    VALUES ('PC Laptops',    (SELECT id FROM Product_categories WHERE name='PC & tablets'));

INSERT INTO Product_categories (name)               VALUES ('Hardware');
INSERT INTO Product_categories (name, parent_id)    VALUES ('Monitors',      (SELECT id FROM Product_categories WHERE name='Hardware'));

INSERT INTO Product_categories (name)               VALUES ('Mobile');
INSERT INTO Product_categories (name, parent_id)    VALUES ('Mobile-phones', (SELECT id FROM Product_categories WHERE name='Mobile'));

INSERT INTO Product_categories (name)               VALUES ('TV & HIFI');
INSERT INTO Product_categories (name, parent_id)    VALUES ('TV',            (SELECT id FROM Product_categories WHERE name='TV & HIFI'));


INSERT INTO Manufactures
(name, support_phone, support_mail)
VALUES
    ('Lenovo',  '12345678', 'lenovo@mail.com' ),
    ('HP',      '23456789', 'hp@mail.com'     ),
    ('Samsung', '34567891', 'samsung@mail.com'),
    ('Acer',    '45678912', 'acer@mail.com'   ),
    ('Dell',    '56789123', 'dell@mail.com'   ),
    ('Philips', '67891234', 'philips@mail.com'),
    ('AOC',     '78912345', 'aoc@mail.com'    ),
    ('Xiaomi',  '89123456', 'xiaomi@mail.com' ),
    ('Apple',   '91234567', 'apple@mail.com'  );


INSERT INTO Products
(name, serial_number, short_description, product_categories_id, manufacture_id)
VALUES
    ('Lenovo Ideapad 5 Pro 14" QHD touch',                 'LenovoIdeapad5Pro-1234',           'Radeon Graphics, Ryzen 5 5600U, 16 GB RAM, 512 GB SSD, Windows 11 Home',           (SELECT id FROM Product_categories WHERE name='PC Laptops'),      (SELECT id FROM Manufactures WHERE name='Lenovo') ),
    ('HP EliteBook 830 G8 13,3" Full HD',                  'HPEliteBook830-1234',              'Iris Xe, Core i5-1135G7, 16GB RAM, 256GB SSD, Windows 10 Pro',                     (SELECT id FROM Product_categories WHERE name='PC Laptops'),      (SELECT id FROM Manufactures WHERE name='HP')     ),
    ('Samsung Galaxy Book3 360 15,6" AMOLED FHD touch',    'SamsungBook3-1234',                'Iris Xe Graphics, Core i7-1360P, 16 GB RAM, 512 GB SSD, S Pen, Windows 11 Home',   (SELECT id FROM Product_categories WHERE name='PC Laptops'),      (SELECT id FROM Manufactures WHERE name='Samsung')),
    ('Acer Chromebook 314 14" HD',                         'AcerChromebook314-1234',           'Celeron N4020, 4 GB RAM, 32 GB eMMC, Google Chrome OS',                            (SELECT id FROM Product_categories WHERE name='PC Laptops'),      (SELECT id FROM Manufactures WHERE name='Acer')   ),
    ('Dell Latitude 3510 15,6" Full HD',                   'DellLatitude3510-1234',            'GeForce MX230, Core i5-10210U,16GB RAM, 256GB SSD, Windows 10 Pro',                (SELECT id FROM Product_categories WHERE name='PC Laptops'),      (SELECT id FROM Manufactures WHERE name='Dell')   ),
    ('Philips 40" monitor 40B1U5600',                      'Phillips40B1u5600-1234',           '3440x1440 IPS, 100hz, 4ms, 1000:1, Speakers, HDMI/DP/USB-C(100w)',                 (SELECT id FROM Product_categories WHERE name='Monitors'),        (SELECT id FROM Manufactures WHERE name='Philips')),
    ('AOC 27" skærm Q27P2Q',                               'AOCQ27P2Q-1234',                   '2560x1440 IPS, 75hz, 4ms, 1000:1, Speakers, VGA/HDMI/DP',                          (SELECT id FROM Product_categories WHERE name='Monitors'),        (SELECT id FROM Manufactures WHERE name='AOC')    ),
    ('Xiaomi Redmi Note 11 Pro 5G 128GB',                  'XiaomiRedmiNote11Pro128-1234',     'Smartphone, 6,67" FHD+ AMOLED DotDisplay-skærm, 108 MP pro-grade hovedkamera',     (SELECT id FROM Product_categories WHERE name='Mobile-phones'),   (SELECT id FROM Manufactures WHERE name='Xiaomi') ),
    ('iPhone 14 Pro 128GB',                                'iPhone14Pro128-1234',              'SmartPhone, 6,1" Super Retina XDR-skærm, 48+12+12MP kamera, IP68, 5G',             (SELECT id FROM Product_categories WHERE name='Mobile-phones'),   (SELECT id FROM Manufactures WHERE name='Apple')  ),
    ('Samsung 75" 4K Neo QLED TV QE75QN95B',               'Samsung754KNeoSLED-1234',          'Neo QLED, 4K, Quantum HDR 2000, Dolby Atmos, 4K 144 Hz Gaming TV',                 (SELECT id FROM Product_categories WHERE name='TV'),              (SELECT id FROM Manufactures WHERE name='Samsung')),
    ('Philips 55" 4K OLED+ Ambilight TV 55OLED907/12',     'Philips554KOLEDAmbilight-1234',    '4K, OLED+, UHD, Bowers&Wilkins, 120 Hz Gaming TV, 4-side Ambilight Android TV',    (SELECT id FROM Product_categories WHERE name='TV'),              (SELECT id FROM Manufactures WHERE name='Philips'));


INSERT INTO Discounts
(name, start_date, end_date)
VALUES
    ('Spring sale', now(), '01-06-2023');


INSERT INTO Price_history -- No discounts
(price, wholesale_price, time_of_creation, product_id)
VALUES
    (6490,     5495,   now(),  1 ),
    (11990,    9990,   now(),  2 ),
    (12995,    10995,  now(),  3 ),
    (2399,     1399,   now(),  4 ),
    (4990,     2990,   now(),  5 ),
    (4299,     3299,   now(),  6 ),
    (1790,     890,    now(),  7 ),
    (2890,     1890,   now(),  8 ),
    (9399,     6399,   now(),  9 ),
    (29990,    22990,  now(),  10),
    (19990,    14999,  now(),  11);

INSERT INTO Price_history -- With discounts
(price, wholesale_price, time_of_creation, product_id, discount_id)
VALUES
    (5990, 5495, now(), 1, 1);

INSERT INTO Specification_names
(name)
VALUES
    ('Operating system'       ),
    ('CPU'                    ),
    ('CPU cores'              ),
    ('RAM'                    ),
    ('RAM speed'              ),
    ('Storage name'           ),
    ('Storage amount'         ),
    ('Monitor size'           ),
    ('Monitor resolution'     ),
    ('Monitor refresh rate'   ),
    ('Graphic processor'      ),
    ('Dedicated graphics card'),
    ('Graphics card memory'   ),
    ('Battery capacity'       );


INSERT INTO Specifications
(product_id, specification_names_id, specification_value)
VALUES
    ((SELECT id FROM Products WHERE name='Lenovo Ideapad 5 Pro 14" QHD touch'), (SELECT id FROM Specification_names WHERE name='CPU'), 'AMD Ryzen 5600U'),
    ((SELECT id FROM Products WHERE name='Lenovo Ideapad 5 Pro 14" QHD touch'), (SELECT id FROM Specification_names WHERE name='RAM'), '16GB'),
    ((SELECT id FROM Products WHERE name='HP EliteBook 830 G8 13,3" Full HD'), (SELECT id FROM Specification_names WHERE name='CPU'), 'Intel Core i5 (Gen 11) 1135G7'),
    ((SELECT id FROM Products WHERE name='HP EliteBook 830 G8 13,3" Full HD'), (SELECT id FROM Specification_names WHERE name='RAM'), '16GB'),
    ((SELECT id FROM Products WHERE name='Samsung Galaxy Book3 360 15,6" AMOLED FHD touch'), (SELECT id FROM Specification_names WHERE name='CPU'), 'Intel Core i7 (Gen 13) 1360P'),
    ((SELECT id FROM Products WHERE name='Samsung Galaxy Book3 360 15,6" AMOLED FHD touch'), (SELECT id FROM Specification_names WHERE name='RAM'), '16GB'),
    ((SELECT id FROM Products WHERE name='Acer Chromebook 314 14" HD'), (SELECT id FROM Specification_names WHERE name='CPU'), 'Intel Celeron N4020'),
    ((SELECT id FROM Products WHERE name='Acer Chromebook 314 14" HD'), (SELECT id FROM Specification_names WHERE name='RAM'), '4GB'),
    ((SELECT id FROM Products WHERE name='Dell Latitude 3510 15,6" Full HD'), (SELECT id FROM Specification_names WHERE name='CPU'), 'Intel Core i5 (Gen 10) 10210U'),
    ((SELECT id FROM Products WHERE name='Dell Latitude 3510 15,6" Full HD'), (SELECT id FROM Specification_names WHERE name='RAM'), '16GB'),
    ((SELECT id FROM Products WHERE name='Dell Latitude 3510 15,6" Full HD'), (SELECT id FROM Specification_names WHERE name='Dedicated graphics card'), 'GeForce MX230'),
    ((SELECT id FROM Products WHERE name='Philips 40" monitor 40B1U5600'), (SELECT id FROM Specification_names WHERE name='Monitor size'), '40'),
    ((SELECT id FROM Products WHERE name='Philips 40" monitor 40B1U5600'), (SELECT id FROM Specification_names WHERE name='Monitor resolution'), '3440x1440'),
    ((SELECT id FROM Products WHERE name='AOC 27" skærm Q27P2Q'), (SELECT id FROM Specification_names WHERE name='Monitor size'), '27'),
    ((SELECT id FROM Products WHERE name='AOC 27" skærm Q27P2Q'), (SELECT id FROM Specification_names WHERE name='Monitor resolution'), '2560x1440'),
    ((SELECT id FROM Products WHERE name='Xiaomi Redmi Note 11 Pro 5G 128GB'), (SELECT id FROM Specification_names WHERE name='Storage amount'), '128GB'),
    ((SELECT id FROM Products WHERE name='Xiaomi Redmi Note 11 Pro 5G 128GB'), (SELECT id FROM Specification_names WHERE name='RAM'), '6GB'),
    ((SELECT id FROM Products WHERE name='iPhone 14 Pro 128GB'), (SELECT id FROM Specification_names WHERE name='Storage amount'), '128GB'),
    ((SELECT id FROM Products WHERE name='iPhone 14 Pro 128GB'), (SELECT id FROM Specification_names WHERE name='RAM'), '6GB'),
    ((SELECT id FROM Products WHERE name='Samsung 75" 4K Neo QLED TV QE75QN95B'), (SELECT id FROM Specification_names WHERE name='Monitor size'), '75'),
    ((SELECT id FROM Products WHERE name='Samsung 75" 4K Neo QLED TV QE75QN95B'), (SELECT id FROM Specification_names WHERE name='Monitor refresh rate'), '144'),
    ((SELECT id FROM Products WHERE name='Philips 55" 4K OLED+ Ambilight TV 55OLED907/12'), (SELECT id FROM Specification_names WHERE name='Monitor size'), '55'),
    ((SELECT id FROM Products WHERE name='Philips 55" 4K OLED+ Ambilight TV 55OLED907/12'), (SELECT id FROM Specification_names WHERE name='Monitor refresh rate'), '120');
