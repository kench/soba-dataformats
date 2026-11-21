package org.seattleoba.data.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BevyTicketNumberUtilTest {
  private static final String BEVY_TICKET_NUMBER = "TTA25062005";
  private static final Integer RAW_TICKET_NUMBER = 62005;

  @Test
  void returnsRawTicketNumber() {
    assertEquals(RAW_TICKET_NUMBER, BevyTicketNumberUtil.toInteger(BEVY_TICKET_NUMBER));
  }
}
