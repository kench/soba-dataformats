package org.seattleoba.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.seattleoba.ticketing.jdbi.TwitchAccountDao;
import org.seattleoba.ticketing.model.TwitchAccount;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TwitchAccountDaoTest {
    private static final String JDBI_URL = "jdbc:sqlite:/tmp/soba-test/sample.db";
    private static final String TWITCH_USER_BROADCASTER_TYPE = "partner";
    private static final Long TWITCH_USER_CREATED_AT = Instant.now().toEpochMilli();
    private static final String TWITCH_USER_DESCRIPTION = "Seattle Online Broadcasters Association";
    private static final String TWITCH_USER_DISPLAY_NAME = "SeattleOBA";
    private static final Integer TWITCH_USER_ID = 114732661;
    private static final String TWITCH_USER_NAME = "seattleoba";
    private static final String TWITCH_USER_TYPE = "";

    private Jdbi jdbi;

    @BeforeEach
    public void setup() {
        jdbi = Jdbi.create(JDBI_URL);
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    @Test
    public void insertsRowIntoTable() {
        final Instant instant = Instant.now();
        final Integer userId = Math.toIntExact(TWITCH_USER_ID + instant.getEpochSecond());
        final String userName = TWITCH_USER_NAME + instant.getEpochSecond();
        final String displayName = TWITCH_USER_DISPLAY_NAME + instant.getEpochSecond();
        final TwitchAccountDao dao = jdbi.onDemand(TwitchAccountDao.class);

        assertDoesNotThrow(() ->
                dao.insert(userId, userName, displayName, TWITCH_USER_TYPE, TWITCH_USER_BROADCASTER_TYPE, TWITCH_USER_DESCRIPTION, TWITCH_USER_CREATED_AT));
        final List<TwitchAccount> output = dao.findByUserId(userId);
        final List<TwitchAccount> findByUserNameOutput = dao.findByUserName(userName);

        assertFalse(output.isEmpty());
        assertEquals(output, findByUserNameOutput);
        assertEquals(userId, output.getFirst().id());
        assertEquals(userName, output.getFirst().userName());
        assertEquals(displayName, output.getFirst().displayName());
        assertEquals(TWITCH_USER_TYPE, output.getFirst().userType());
        assertEquals(TWITCH_USER_BROADCASTER_TYPE, output.getFirst().broadcasterType());
        assertEquals(TWITCH_USER_DESCRIPTION, output.getFirst().description());
        assertEquals(TWITCH_USER_CREATED_AT, output.getFirst().createdAt());
    }
}
