package org.seattleoba.data.model;

/**
 * Twitch team membership
 *
 * @param userId Twitch channel ID
 * @param teamId Twitch team ID
 */
public record TwitchTeamMembership(
        Integer userId,
        Integer teamId) {
}
