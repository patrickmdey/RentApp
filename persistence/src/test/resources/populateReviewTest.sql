
-- load images
INSERT INTO picture (id, data)
values (1,''),(2,''),(3,'');


-- load user
INSERT INTO account (id,first_name,last_name,email,location,password,picture,type)
values  (1,'Lucas','owner','lucas@mail.com',20,'password hash',1,0),
        (2,'Carlos','renter','carlos@mail.com',20,'password hash',2,1);

-- load article
INSERT INTO article (id,title,description,price_per_day,owner_id)
values (1,'Moto','moto para andar',123.0,1),
       (2,'Auto','auto rapido',9000.0,1),
       (3,'Heladera','muy fria',1000.0,1),
       (4,'Soldador','para arreglar cosas',50.0,1);

-- load review
INSERT INTO review (id, rating, message, article_id,renter_id, created_at  )
VALUES             (1 ,   5   , 'test1',     1     ,     2   , '2021-10-06'),
                   (2 ,   4   , 'test2',     1     ,     2   , '2021-10-07'),
                   (3 ,   3   , 'test3',     1     ,     2   , '2021-10-08');