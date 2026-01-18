package org.seattleoba.data.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Bevy date conversion utility methods.
 */
public final class BevyDateUtil {
    private BevyDateUtil() {
        //
    }

    /**
     * Converts the date found in the Bevy CSV export to the Unix epoch in seconds
     *
     * @param date Bevy formatted date
     * @return
     */
    public static Long toUnixEpochInSeconds(final String date) {
        // 2025-11-11 00:48:57+00:00
        final String isoOffsetDateTime = date.replace(' ', 'T');
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ISO_OFFSET_DATE_TIME
                .withZone(ZoneOffset.UTC);
        return LocalDateTime.parse(isoOffsetDateTime, dateTimeFormatter)
                .atZone(ZoneOffset.UTC)
                .toInstant()
                .getEpochSecond();
    }

    /**
     * Returns the date as formatted in the Bevy CSV export.
     *
     * @param date Unix epoch in seconds
     * @return
     */
    public static String toBevyDate(final Long date) {
        // 2025-11-11 00:48:57+00:00
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ISO_OFFSET_DATE_TIME
                .withZone(ZoneOffset.UTC);
        return dateTimeFormatter
                .format(Instant.ofEpochSecond(date))
                .replace('T', ' ')
                .replace("Z", "+00:00");
    }
}
