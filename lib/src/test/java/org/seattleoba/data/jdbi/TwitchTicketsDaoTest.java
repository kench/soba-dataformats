package org.seattleoba.data.jdbi;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.Instant;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TwitchTicketsDaoTest {
  private static final String JDBI_URL = "jdbc:sqlite:/tmp/soba-test/sample.db";

  private Jdbi jdbi;

  @BeforeEach
  void setup() {
    jdbi = Jdbi.create(JDBI_URL);
    jdbi.installPlugin(new SqlObjectPlugin());
  }

  @Test
  void insertsRowIntoTable() {
    final Integer userId = Math.toIntExact(Instant.now().getEpochSecond() % 999000000);
    final Integer eventId = Math.toIntExact(Instant.now().getEpochSecond() % 100);
    final Integer ticketId = Math.toIntExact(Instant.now().toEpochMilli() & 100000);
    final TwitchTicketsDao dao = jdbi.onDemand(TwitchTicketsDao.class);

    assertDoesNotThrow(() -> dao.insert(userId, eventId, ticketId));

    assertEquals(userId, dao.getUserForTicket(ticketId));
    assertEquals(userId, dao.getUserForEventAndTicket(eventId, ticketId));
    assertFalse(dao.getTicketsForUser(userId).isEmpty());
    assertEquals(ticketId, dao.getTicketsForUser(userId).getFirst());
    assertEquals(ticketId, dao.getTicketsForUserAndEvent(userId, eventId).getFirst());
  }
}
