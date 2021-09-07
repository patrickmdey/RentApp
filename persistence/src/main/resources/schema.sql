
CREATE TABLE IF NOT EXISTS picture
(
    id   SERIAL NOT NULL,
    data BYTEA  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS category
(
    id          SERIAL,
    description VARCHAR NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS account
(
    id         SERIAL  NOT NULL,
    first_name VARCHAR NOT NULL,
    last_name  VARCHAR NOT NULL,
    email      VARCHAR NOT NULL UNIQUE,
    location   VARCHAR NOT NULL,
    password   VARCHAR NOT NULL,
    picture    INT,
    type       INT     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (picture) REFERENCES picture (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS article
(
    id            SERIAL,
    title         VARCHAR NOT NULL,
    description   VARCHAR NOT NULL,
    price_per_day decimal NOT NULL,
    owner_id      INT     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (owner_id) REFERENCES account (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS article_picture
(
    picture_id INT NOT NULL,
    article_id INT NOT NULL,
    PRIMARY KEY (picture_id, article_id),
    FOREIGN KEY (picture_id) REFERENCES picture (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (article_id) REFERENCES article (id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS article_category
(
    category_id INT NOT NULL,
    article_id  INT NOT NULL,
    PRIMARY KEY (category_id, article_id),
    FOREIGN KEY (article_id) REFERENCES article (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category (id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS rent_proposal
(
    id         SERIAL             NOT NULL,
    start_date DATE               NOT NULL,
    end_date   DATE               NOT NULL,
    message    VARCHAR            NOT NULL,
    approved   BOOL DEFAULT FALSE NOT NULL,
    article_id INT                NOT NULL,
    renter_id  INT                NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (article_id) REFERENCES article (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (renter_id) REFERENCES account (id) ON UPDATE CASCADE ON DELETE CASCADE,
    UNIQUE (start_date, end_date, article_id, renter_id)
);


--Cargado de datos
INSERT INTO account (first_name, last_name, email, location, password, picture, type)
 VALUES('Master', 'User', 'paw2021b3@gmail.com', 'Palermo', 'root', null, 0);

INSERT INTO category (description)
VALUES ('Technology'), ('Camping'), ('Cars'), ('Kitchen'), ('Tools'), ('Sailing'), ('Travel');

INSERT INTO article (title, description, price_per_day, owner_id)
VALUES  ('Mountain bike Futura Lynce R29 ',
         'un sistema de suspensión delantera la hace más liviana que aquellas que tienen doble suspensión. Esto se traduce en más velocidad, mejor nivel y mantenimiento sencillo.',
         700, 1),
        ('Playstation 5',
         'Guardá tus apps, fotos, videos y mucho más en el disco duro, que cuenta con una capacidad de 825 GB.', 1500, 1),
        ('Caña Shimano Solara Baitcast 2', 'Ideal para la pesca de taruchas-truchas y dorados con artificiales ', 500, 1);

INSERT INTO article_category (category_id, article_id) VALUES (7,1), (1,2), (6,3);