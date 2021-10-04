
-- Load User
INSERT INTO account (id,first_name,last_name,email,location,password,picture,type)
values  (1,'Lucas','owner','lucas@mail.com',20,'password hash',null,0);

-- Load Article
INSERT INTO article (id,title,description,price_per_day,owner_id)
values (1,'Moto','moto para andar',123.0,1);

-- Load Picture
INSERT INTO picture (id, data)
VALUES (1,CAST('test' AS VARBINARY(10))),
       (2,CAST('test' AS VARBINARY(10)));

-- Load Article_picture
INSERT INTO article_picture (picture_id,article_id)
VALUES (1,1);