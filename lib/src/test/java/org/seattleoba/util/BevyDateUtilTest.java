package org.seattleoba.util;

import org.junit.jupiter.api.Test;
import org.seattleoba.ticketing.util.BevyDateUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BevyDateUtilTest {
    private static final String BEVY_DATE = "2025-11-11 00:48:57+00:00";
    private static final Long UNIX_EPOCH_IN_SECONDS = 1762822137L;

    @Test
    public void returnsUnixEpochInSeconds() {
        assertEquals(UNIX_EPOCH_IN_SECONDS, BevyDateUtil.toUnixEpochInSeconds(BEVY_DATE));
    }

    @Test
    public void returnsBevyTime() {
        assertEquals(BEVY_DATE, BevyDateUtil.toBevyDate(UNIX_EPOCH_IN_SECONDS));
    }
}
