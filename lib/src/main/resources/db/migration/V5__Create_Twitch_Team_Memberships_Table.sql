CREATE TABLE twitch_team_memberships (
    team_id INT NOT NULL,
    user_id VARCHAR NOT NULL,
    FOREIGN KEY (team_id) REFERENCES twitch_teams (id),
    FOREIGN KEY (user_id) REFERENCES twitch_accounts (id)
);