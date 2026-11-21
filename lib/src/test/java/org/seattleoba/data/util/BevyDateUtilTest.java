package org.seattleoba.data.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BevyDateUtilTest {
  private static final String BEVY_DATE = "2025-11-11 00:48:57+00:00";
  private static final Long UNIX_EPOCH_IN_SECONDS = 1762822137L;

  @Test
  void returnsUnixEpochInSeconds() {
    assertEquals(UNIX_EPOCH_IN_SECONDS, BevyDateUtil.toUnixEpochInSeconds(BEVY_DATE));
  }

  @Test
  void returnsBevyTime() {
    assertEquals(BEVY_DATE, BevyDateUtil.toBevyDate(UNIX_EPOCH_IN_SECONDS));
  }
}
