CREATE TABLE bevy_tickets (
    number INT PRIMARY KEY NOT NULL,
    event_id INT NOT NULL,
    purchaser_name VARCHAR NOT NULL,
    title VARCHAR NOT NULL,
    paid_on INT NOT NULL,
    checked_in_at INT,
    access_code VARCHAR,
    price DECIMAL
);