CREATE TABLE traveller (
    id integer NOT NULL AUTO_INCREMENT,
    name varchar(255),
    email varchar(255),
    birthday date,
    age bigint,
    all_fees integer,
    deleted boolean,
    travel_id integer,
    PRIMARY KEY (id)
);

    ALTER TABLE traveller ADD CONSTRAINT fr_travel FOREIGN KEY (travel_id) REFERENCES travel (id);
