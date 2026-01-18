package org.seattleoba.data.model;

/**
 * Twitch team information
 *
 * @param id Twitch team ID
 * @param name Twitch team name
 * @param displayName Twitch team display name
 */
public record TwitchTeam(
        Integer id,
        String name,
        String displayName) {
}
