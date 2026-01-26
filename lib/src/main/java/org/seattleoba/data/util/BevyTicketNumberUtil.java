package org.seattleoba.data.util;

/**
 * Bevy ticket number conversion utility class
 */
public final class BevyTicketNumberUtil {
    private static final String BEVY_TICKET_PREFIX = "TTA";

    private BevyTicketNumberUtil() {
        //
    }

    /**
     * Converts the Bevy ticket number to integer form stored within the ticket QR code
     *
     * @param ticketNumber String ticket number beginning with TTA
     * @return raw ticket number
     */
    public static int toInteger(final String ticketNumber) {
        // Bevy ticket QR codes have the format: (768:62005)
        // 768 is the event ID in Bevy and 62005 is the last 5 digits of the ticket ID.
        if (ticketNumber.startsWith(BEVY_TICKET_PREFIX)) {
            return Integer.parseInt(ticketNumber.substring(6));
        } else {
            throw new IllegalArgumentException("Invalid Bevy ticket number: "+ ticketNumber);
        }
    }
}
