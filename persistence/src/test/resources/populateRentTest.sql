-- load images
INSERT INTO picture (id, data)
values (1,''),(2,''),(3,''),(4,''),(5,''),(6,''),(7,''),(8,''),(9,'');

-- users
INSERT INTO account (id,first_name,last_name,email,location,password,picture,type)
values  (1,'Lucas','owner','lucas@mail.com',20,'password hash',1,0),
        (2,'Carlos','renter','carlos@mail.com',20,'password hash',2,1);

-- category
INSERT INTO category (id,description, picture)
values (1,'Category.Technology',3),
       (2,'Category.Camping',4),
       (3,'Category.Cars',5),
       (4,'Category.Kitchen',6),
       (5,'Category.Tools',7),
       (6,'Category.travel',8),
       (7,'Category.Sailing',9);

-- article
INSERT INTO article (id,title,description,price_per_day,owner_id)
values (1,'Moto','moto para andar',123.0,1),
       (2,'Auto','auto rapido',9000.0,1),
       (3,'Heladera','muy fria',1000.0,1),
       (4,'Soldador','para arreglar cosas',50.0,1);

-- category_Article
INSERT INTO article_category (category_id,article_id)
VALUES (1,1),
       (6,1),
       (3,2),
       (4,3),
       (5,4),
       (1,4);

-- rent_proposal
INSERT INTO rent_proposal (id,start_date,end_date,message,state,article_id,renter_id)
values (1,'2021-10-05', '2021-11-01','can I rent 1', 0,1,2),
       (2,'2021-10-05', '2021-11-01','can I rent 2', 1,2,2),
       (3,'2021-10-05', '2021-11-01','can I rent 3', 1,3,2);