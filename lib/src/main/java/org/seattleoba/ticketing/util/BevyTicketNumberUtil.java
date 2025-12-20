package org.seattleoba.ticketing.util;

/**
 * Bevy ticket number conversion utility class
 */
public final class BevyTicketNumberUtil {
    private static final String BEVY_TICKET_PREFIX = "TTA";
    private static final Integer TICKET_NUMBER_BASE = 25000000;

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
            final int ticketNumberSuffix = Integer.parseInt(ticketNumber.substring(3));
            return ticketNumberSuffix - TICKET_NUMBER_BASE;
        } else {
            throw new IllegalArgumentException("Invalid Bevy ticket number: "+ ticketNumber);
        }
    }

    /**
     * Converts the raw ticket number stored in the Bevy ticket QR code to the human-readable representation.
     *
     * @param ticketNumber raw Bevy ticket number
     * @return Human-readable Bevy ticket number
     */
    public static String toString(final int ticketNumber) {
        return "TTA" + (TICKET_NUMBER_BASE + ticketNumber);
    }
}
