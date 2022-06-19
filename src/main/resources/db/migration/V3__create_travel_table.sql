CREATE TABLE travel (
    id integer NOT NULL AUTO_INCREMENT,
    start_date date,
    end_date date,
    days integer,
    accommodation_id integer,
    destination_id integer,
    whole_price integer NOT NULL,
    full_income integer,
    deleted boolean,
    PRIMARY KEY (id)
);