CREATE TABLE travel (
    id integer NOT NULL AUTO_INCREMENT,
    start_date date,
    end_date date,
    days integer,
    whole_price integer,
    full_income integer,
    deleted boolean,
    destination_id integer,
    PRIMARY KEY (id)
);

    ALTER TABLE travel ADD CONSTRAINT fk_destination FOREIGN KEY (destination_id) REFERENCES destination (id);
