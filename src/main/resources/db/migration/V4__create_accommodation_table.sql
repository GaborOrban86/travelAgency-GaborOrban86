CREATE TABLE accommodation (
    id integer NOT NULL AUTO_INCREMENT,
    name varchar(255),
    type varchar(255),
    catering varchar(255),
    price integer,
    deleted boolean,
    travel_id integer,
    PRIMARY KEY (id)
);

    ALTER TABLE accommodation ADD CONSTRAINT ac_travel FOREIGN KEY (travel_id) REFERENCES travel (id);
