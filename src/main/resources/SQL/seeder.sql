USE Auto_Connect_db;
#password = P@ssword1
INSERT INTO users (id, first_name, last_name, email, username, password, profile_picture, is_mechanic, certification,
                   description, address_street, address_city, address_state, address_zip)
VALUES (1, 'user_first', 'user_last', 'user@email.com', 'user',
        '$2a$10$NbPL//se8FlAhzkTMRhtXuLxzi5t.kTwrOlvxTWOkI1OdSMWri/ni', '/img/image.jpeg', 0, null, null,
        '4050 Premier st', 'Dallas', 'Texas', 75023),
       (2, 'mechanic_first', 'mechanic_last', 'mechanic@email.com', 'mechanic',
        '$2a$10$NbPL//se8FlAhzkTMRhtXuLxzi5t.kTwrOlvxTWOkI1OdSMWri/ni', '/img/image.jpeg', 1, null, null,
        '3953 Central Expy', 'Plano', 'Texas', 75023),
       (3, 'savanna', 'user', 'savanna@email.com', 'savanna',
        '$2a$10$NbPL//se8FlAhzkTMRhtXuLxzi5t.kTwrOlvxTWOkI1OdSMWri/ni', '/img/image.jpeg', 0, null, null,
        ' 700 W Parker Rd', 'Plano', 'Texas', 75075),
       (4, 'amir', 'mechanic', 'amir@email.com', 'amir', '$2a$10$NbPL//se8FlAhzkTMRhtXuLxzi5t.kTwrOlvxTWOkI1OdSMWri/ni',
        '/img/image.jpeg', 1, null, null, '4250 Forest Ln suite b', 'Garland', 'Texas', 75042);


INSERT INTO appointments(id, title, description, price, date, receiver_id, requester_id)
VALUES (1, 'Oil Change', 'Oil Change for A-Team', 20.0, '2023-07-05', 1, 2),
       (2, 'Brake', 'Brake Maintenance', 129.99, '2023-07-24', 4, 3);

INSERT INTO posts (id, title, body, car_year, car_make, car_body, user_id)
VALUES (1, '#1 in our town!', 'We did maintenance together and did great job!', 2019, 'Honda', 'Civic', 1),
       (2, 'Worst Guy Ever', 'He overcharge my repair, not recommended!', 2018, 'Ford', 'Mustang', 3);

INSERT INTO comments(id, content, post_id, author_id)
VALUES (1, 'You did great', 1, 2),
       (2, 'Thanks!', 2, 1);

INSERT INTO messages (id, message, recipient_id, sender_id)
VALUES (1, 'Hello', 1, 2),
       (2, 'Hello Back', 2, 1);


INSERT INTO review (id, rating, body, mechanic_id, user_id)
VALUES (1, 5, 'Highly Recommended to work with him', 2, 1),
       (2, 1, 'Not Recommended. I wish I can give 0 star!', 4, 3);







