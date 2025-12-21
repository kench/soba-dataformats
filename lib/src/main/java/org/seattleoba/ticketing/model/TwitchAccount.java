package org.seattleoba.ticketing.model;

/**
 * Twitch account information.
 *
 * @param id
 * @param userName Username. Lowercase.
 * @param displayName Display name.
 * @param userType User account type.
 * @param broadcasterType Broadcaster type
 * @param description Description
 * @param createdAt Account creation date
 */
public record TwitchAccount(
        Integer id,
        String userName,
        String displayName,
        String userType,
        String broadcasterType,
        String description,
        Long createdAt) {
}
