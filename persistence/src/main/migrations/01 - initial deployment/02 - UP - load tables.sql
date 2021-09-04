INSERT INTO category (description)
VALUES ('Technology'), ('Camping'), ('Cars'), ('Kitchen'), ('Tools'), ('Sailing'), ('Travel');

INSERT INTO article (title, description, price_per_day, owner_id)
VALUES  ('Mountain bike Futura Lynce R29 ',
        'un sistema de suspensión delantera la hace más liviana que aquellas que tienen doble suspensión. Esto se traduce en más velocidad, mejor nivel y mantenimiento sencillo.',
        700, 1),
       ('Playstation 5',
        'Guardá tus apps, fotos, videos y mucho más en el disco duro, que cuenta con una capacidad de 825 GB.', 1500, 1),
       ('Caña Shimano Solara Baitcast 2', 'Ideal para la pesca de taruchas-truchas y dorados con artificiales ', 500, 1);

INSERT INTO article_category (category_id, article_id) VALUES (7,1), (2,2), (6,3);