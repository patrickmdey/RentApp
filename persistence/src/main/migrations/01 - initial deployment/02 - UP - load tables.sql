begin;

INSERT INTO Users (id, first_name, last_name, email, location, password, photo, type)
VALUES (DEFAULT, 'Lucas', 'Dellisola', 'lucas@mail.com', 'Ezeiza', 'NOPASS', null, 1);

INSERT INTO Article_Categories (id, description)
VALUES  (DEFAULT, 'Technology'),
        (DEFAULT, 'Camping'),
        (DEFAULT, 'Cars'),
        (DEFAULT, 'Kitchen'),
        (DEFAULT, 'Tools'),
        (DEFAULT, 'Sailing'),
        (DEFAULT, 'Travel');

INSERT INTO Articles (id, title, description, price_per_day, id_owner, id_category)
VALUES (DEFAULT, 'Carpa de Verano', 'Esta carpa te va a cuidar cuando vayas de campamento', 1000, 1, 2),
       (DEFAULT, 'Camara Digital', 'Super camara full HD 4K color ', 5000, 1, 1),
       (DEFAULT, 'Sierra Electrica', 'Sierra electrica 1000 wats. Te faena hasta al vecino.', 500, 1, 5),
       (DEFAULT, 'Taxi habilitado en capital', 'Alquilo mi taxi para quien se quiera hacer unos mangos durante el finde. Tiene todo al dia y hasta la licencia habilitada en CABA', 900, 1, 3);


commit;
