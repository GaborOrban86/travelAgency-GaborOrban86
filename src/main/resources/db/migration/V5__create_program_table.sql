CREATE TABLE program (
    id integer NOT NULL AUTO_INCREMENT,
    name varchar(255),
    description varchar(255),
    guide varchar(255),
    price integer,
    deleted boolean,
    travel_id integer,
    PRIMARY KEY (id)
);

    ALTER TABLE program ADD CONSTRAINT pr_travel FOREIGN KEY (travel_id) REFERENCES travel (id);
