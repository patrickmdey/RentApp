-- Load users

INSERT INTO account (id,first_name,last_name,email,location,password,picture,type)
values  (1,'Lucas','owner','lucas@mail.com',20,'password hash',null,0),
        (2,'Carlos','renter','carlos@mail.com',20,'password hash',null,1);

-- Load articles
INSERT INTO article (id,title,description,price_per_day,owner_id)
values (1,'Moto','moto para andar',123.0,1);

-- Load category

INSERT INTO category (id,description)
values (1,'Category.Technology'),
       (2,'Category.Camping'),
       (3,'Category.Cars'),
       (4,'Category.Kitchen'),
       (5,'Category.Tools'),
       (6,'Category.travel'),
       (7,'Category.Sailing');

-- Load Article_category

INSERT INTO article_category (category_id,article_id)
VALUES (1,1),
       (2,1),
       (6,1);
