
-- load images
INSERT INTO picture (id, data)
values (1,''),(2,''),(3,'');



-- Load Account
INSERT INTO account (id,first_name,last_name,email,location,password,picture,type)
values  (1,'Lucas','owner','lucas@mail.com',20,'password hash',1,0),
        (2,'Carlos','renter','carlos@mail.com',20,'password hash',2,1);

