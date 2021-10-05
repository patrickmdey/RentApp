CREATE TABLE IF NOT EXISTS picture
(
    id   SERIAL NOT NULL,
    data BYTEA  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS category
(
    id          SERIAL,
    description VARCHAR(1000) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS account
(
    id         SERIAL  NOT NULL,
    first_name VARCHAR(1000) NOT NULL,
    last_name
               VARCHAR(1000)
                       NOT
                           NULL,
    email
               VARCHAR(1000)
                       NOT
                           NULL
        UNIQUE,
    location
               INTEGER
                       NOT
                           NULL,
    password
               VARCHAR(1000)
                       NOT
                           NULL,
    picture
               INT,
    type
               INT
                       NOT
                           NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (picture) REFERENCES picture (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS article
(
    id            SERIAL,
    title         VARCHAR(1000) NOT NULL,
    description   VARCHAR(1000) NOT NULL,
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
    message    VARCHAR(1000)            NOT NULL,
    state       INT                 NOT NULL,
    article_id INT                NOT NULL,
    renter_id  INT                NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (article_id) REFERENCES article (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (renter_id) REFERENCES account (id) ON UPDATE CASCADE ON DELETE CASCADE,
    UNIQUE (start_date, end_date, article_id, renter_id)
);

CREATE TABLE IF NOT EXISTS review
(
    id         SERIAL    NOT NULL,
    rating     INT       NOT NULL,
    message    VARCHAR(1000)   NOT NULL,
    article_id INT       NOT NULL,
    renter_id  INT       NOT NULL,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (renter_id) REFERENCES account (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (article_id) REFERENCES article (id) ON UPDATE CASCADE ON DELETE CASCADE
);

