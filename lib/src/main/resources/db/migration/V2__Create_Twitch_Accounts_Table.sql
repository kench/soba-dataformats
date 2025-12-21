CREATE TABLE twitch_accounts (
    id INT PRIMARY KEY NOT NULL,
    user_name VARCHAR NOT NULL,
    display_name VARCHAR NOT NULL,
    user_type VARCHAR NOT NULL,
    broadcaster_type VARCHAR NOT NULL,
    description VARCHAR,
    created_at INT NOT NULL
);