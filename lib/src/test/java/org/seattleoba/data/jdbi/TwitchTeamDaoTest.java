package org.seattleoba.data.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.seattleoba.data.model.TwitchTeam;

import static org.junit.jupiter.api.Assertions.*;

public class TwitchTeamDaoTest {
    private static final String TEAM_DISPLAY_NAME = "Seattle Online Broadcasters Association";
    private static final Integer TEAM_ID = 3685;
    private static final String TEAM_NAME = "soba";
    private static final String JDBI_URL = "jdbc:sqlite:/tmp/soba-test/sample.db";

    private Jdbi jdbi;

    @BeforeEach
    public void setup() {
        jdbi = Jdbi.create(JDBI_URL);
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    @Test
    public void insertsRowIntoTable() {
        final TwitchTeam team = new TwitchTeam(TEAM_ID, TEAM_NAME, TEAM_DISPLAY_NAME);
        final TwitchTeamDao dao = jdbi.onDemand(TwitchTeamDao.class);

        assertDoesNotThrow(() -> dao.insert(TEAM_ID, TEAM_NAME, TEAM_DISPLAY_NAME));

        assertTrue(dao.findByTeamId(TEAM_ID).isPresent());
        assertEquals(team, dao.findByTeamId(TEAM_ID).get());
        assertTrue(dao.findByTeamName(TEAM_NAME).isPresent());
        assertEquals(team, dao.findByTeamName(TEAM_NAME).get());
    }
}
