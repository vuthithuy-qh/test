-- file: insert_car_images.sql

-- Đảm bảo tên bảng và cột đúng với database của bạn
-- Nếu cột 'id' là IDENTITY (tự tăng), không cần liệt kê nó trong danh sách cột
-- Nếu không phải IDENTITY, bạn phải cung cấp giá trị ID duy nhất cho mỗi dòng
-- INSERT INTO [CarShop].[dbo].[car_image] (id, car_id, image_url, description) VALUES (...);

-- Images for car_model_id 1 (Toyota Camry 2025), linked to car_id 1, 5, 6
INSERT INTO [CarShop].[dbo].[car_image] (car_id, image_url, description)
VALUES (1, 'https://images.dealer.com/ddc/vehicles/2025/Toyota/Camry/Sedan/color/Midnight%20Black%20Metallic-218-26,28,33-640-en_US.jpg', 'Front view of Toyota Camry 2025');

INSERT INTO [CarShop].[dbo].[car_image] (car_id, image_url, description)
VALUES (1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRLXGe31ao40Br7Nc3m1lOEuF7-tMHb6dWaEA&s', 'Interior view of Toyota Camry 2025');

-- Car ID: 5, car_model_id: 1 (Toyota Camry 2025)
INSERT INTO [CarShop].[dbo].[car_image] (car_id, image_url, description)
VALUES (5, 'https://toyota-thaihoa.com.vn/wp-content/uploads/2022/03/AE10F9E59B774DC4A63487A1419E1534.png', 'Side profile of Toyota Camry 2025');

-- Car ID: 6, car_model_id: 1 (Toyota Camry 2025)
INSERT INTO [CarShop].[dbo].[car_image] (car_id, image_url, description)
VALUES (6, 'https://hips.hearstapps.com/hmg-prod/images/2025-toyota-camry-xse-awd-123-66993cc94cc40.jpg?crop=0.676xw:0.506xh;0.101xw,0.399xh&resize=1200:*', 'Rear view of Toyota Camry 2025');


-- Images for car_model_id 2 (Honda CR-V 2024), linked to car_id 2, 7
INSERT INTO [CarShop].[dbo].[car_image] (car_id, image_url, description)
VALUES (2, 'https://hondagiaiphong.net/images/2024/crv_2024_hybrit/blanco.png', 'Front view of Honda CR-V 2024');

INSERT INTO [CarShop].[dbo].[car_image] (car_id, image_url, description)
VALUES (2, '/Images_car/honda_crv_2024_interior.jpg', 'Interior of Honda CR-V 2024');

-- Car ID: 7, car_model_id: 2 (Honda CR-V 2024)
INSERT INTO [CarShop].[dbo].[car_image] (car_id, image_url, description)
VALUES (7, 'https://otohondakimthanh.vn/wp-content/uploads/2023/10/F2_copy.jpg', 'Side view of Honda CR-V 2024');


-- Images for car_model_id 3 (Ford Ranger 2023), linked to car_id 8
INSERT INTO [CarShop].[dbo].[car_image] (car_id, image_url, description)
VALUES (8, 'https://cms-i.autodaily.vn/du-lieu/2021/12/20/2023-ford-ranger-raptor-red-copy.jpg', 'Front view of Ford Ranger 2023');

INSERT INTO [CarShop].[dbo].[car_image] (car_id, image_url, description)
VALUES (8, '/Images_car/ford_ranger_2023_bed.jpg', 'Cargo bed view of Ford Ranger 2023');


-- Images for car_model_id 4 (Mercedes C300 2025), linked to car_id 3, 4
INSERT INTO [CarShop].[dbo].[car_image] (car_id, image_url, description)
VALUES (3, 'https://mercedesphumyhung.com.vn/wp-content/uploads/2024/11/Mercedes-C300-AMG.jpg', 'Front view of Mercedes C300 2025');

INSERT INTO [CarShop].[dbo].[car_image] (car_id, image_url, description)
VALUES (3, '/Images_car/mercedes_c300_2025_dash.jpg', 'Dashboard of Mercedes C300 2025');

-- Car ID: 4, car_model_id: 4 (Mercedes C300 2025)
INSERT INTO [CarShop].[dbo].[car_image] (car_id, image_url, description)
VALUES (4, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaXzHgMQOgVrQc0DHB4zVcELpTvX1ORdNXnA&s', 'Rear view of Mercedes C300 2025');

INSERT INTO [CarShop].[dbo].[car_image] (car_id, image_url, description)
VALUES (4, '/Images_car/mercedes_c300_2025_wheel.jpg', 'Wheel detail of Mercedes C300 2025');