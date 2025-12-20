package org.seattleoba.util;

import org.junit.jupiter.api.Test;
import org.seattleoba.ticketing.util.BevyTicketNumberUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BevyTicketNumberUtilTest {
    private static final String BEVY_TICKET_NUMBER = "TTA25062005";
    private static final Integer RAW_TICKET_NUMBER = 62005;

    @Test
    public void returnsRawTicketNumber() {
        assertEquals(RAW_TICKET_NUMBER, BevyTicketNumberUtil.toInteger(BEVY_TICKET_NUMBER));
    }

    @Test
    public void returnBevyTicketNumber() {
        assertEquals(BEVY_TICKET_NUMBER, BevyTicketNumberUtil.toString(RAW_TICKET_NUMBER));
    }
}
