package org.seattleoba.ticketing.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Bevy ticket representation.
 *
 * @param ticketNumber Bevy ticket number (example: TTA25057051)
 * @param orderNumber Bevy order number (example: TTE25065088)
 * @param purchaserName Ticket purchaser name. Corresponds to Twitch username.
 * @param ticketType Ticket type
 * @param purchaseDate Ticket purchase date
 * @param checkInDate Check-in date
 * @param accessCode Access code, if one was used.
 * @param price Price paid for ticket.
 */
public record BevyTicket(
        @JsonProperty("Ticket number") String ticketNumber,
        @JsonProperty("Order number") String orderNumber,
        @JsonProperty("Paid by (name)") String purchaserName,
        @JsonProperty("Ticket title") String ticketType,
        @JsonProperty("Paid date (UTC)") String purchaseDate,
        @JsonProperty("Checkin Date (UTC)") String checkInDate,
        @JsonProperty("Access code") String accessCode,
        @JsonProperty("Price") String price) {
}
