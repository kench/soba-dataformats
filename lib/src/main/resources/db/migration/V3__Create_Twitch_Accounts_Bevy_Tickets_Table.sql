CREATE TABLE twitch_accounts_bevy_tickets (
    twitch_id INT NOT NULL,
    bevy_ticket_id INT NOT NULL,
    FOREIGN KEY (twitch_id) REFERENCES twitch_accounts (id),
    FOREIGN KEY (bevy_ticket_id) REFERENCES bevy_tickets (id)
);