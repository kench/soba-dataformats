CREATE TABLE bevy_tickets (
    id INT PRIMARY KEY NOT NULL,
    ticket_id VARCHAR NOT NULL,
    order_id VARCHAR NOT NULL,
    event_id INT NOT NULL,
    purchaser_name VARCHAR NOT NULL,
    title VARCHAR NOT NULL,
    paid_on INT NOT NULL,
    checked_in_at INT,
    access_code VARCHAR,
    price DECIMAL
);