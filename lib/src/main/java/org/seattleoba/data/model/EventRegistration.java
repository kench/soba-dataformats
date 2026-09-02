package org.seattleoba.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Event registration entry.
 *
 * @param ticketNumber Bevy ticket number (example: TTA25057051)
 * @param orderNumber Bevy order number (example: TTE25065088)
 * @param ticketType Ticket type
 * @param purchaseDate Ticket purchase date
 * @param purchaserName Ticket purchaser name. Corresponds to Twitch display name.
 * @param userName Twitch user name
 * @param broadcasterType Twitch broadcaster type
 * @param userType Twitch user account type
 */
public record EventRegistration(
        @JsonProperty("Ticket number") String ticketNumber,
        @JsonProperty("Order number") String orderNumber,
        @JsonProperty("Ticket title") String ticketType,
        @JsonProperty("Paid date (UTC)") String purchaseDate,
        @JsonProperty("Paid by (name)") String purchaserName,
        @JsonProperty("User name") String userName,
        @JsonProperty("Broadcaster type") String broadcasterType,
        @JsonProperty("User type") String userType) {
}
