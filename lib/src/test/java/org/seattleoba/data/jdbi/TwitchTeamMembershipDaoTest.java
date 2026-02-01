package org.seattleoba.data.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.seattleoba.data.model.TwitchTeamMembership;

import static org.junit.jupiter.api.Assertions.*;

public class TwitchTeamMembershipDaoTest {
    private static final Integer USER_ID = 70376773;
    private static final Integer TEAM_ID = 3685;
    private static final String JDBI_URL = "jdbc:sqlite:/tmp/soba-test/sample.db";

    private Jdbi jdbi;

    @BeforeEach
    public void setup() {
        jdbi = Jdbi.create(JDBI_URL);
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    @Test
    public void insertsRowIntoTable() {
        final TwitchTeamMembership membership = new TwitchTeamMembership(USER_ID, TEAM_ID);
        final TwitchTeamMembershipDao dao = jdbi.onDemand(TwitchTeamMembershipDao.class);

        assertDoesNotThrow(() -> dao.insert(TEAM_ID, USER_ID));

        assertEquals(membership, dao.findByTeamId(TEAM_ID).getFirst());
        assertEquals(membership, dao.findByUserId(USER_ID).getFirst());
    }
}
