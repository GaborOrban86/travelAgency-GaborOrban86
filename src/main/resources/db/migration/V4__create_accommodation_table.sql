CREATE TABLE accommodation (
    id integer NOT NULL AUTO_INCREMENT,
    name varchar(255),
    type integer,
    catering integer,
    travel_id integer,
    price integer NOT NULL,
    deleted boolean,
    PRIMARY KEY (id)
);