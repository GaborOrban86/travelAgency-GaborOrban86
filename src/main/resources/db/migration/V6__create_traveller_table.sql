CREATE TABLE traveller (
    id integer NOT NULL AUTO_INCREMENT,
    name varchar(255),
    email varchar(255),
    birthday date,
    age bigint NOT NULL,
    travel_id integer,
    all_fees integer NOT NULL,
    PRIMARY KEY (id)
);