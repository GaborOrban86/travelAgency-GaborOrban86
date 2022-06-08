CREATE TABLE program (
    id integer NOT NULL AUTO_INCREMENT,
    name varchar(255),
    description varchar(255),
    guide varchar(255),
    price integer NOT NULL,
    travel_id integer,
    deleted boolean,
    PRIMARY KEY (id)
);