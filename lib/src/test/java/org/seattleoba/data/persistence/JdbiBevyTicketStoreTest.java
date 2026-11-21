package org.seattleoba.data.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Random;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.seattleoba.data.jdbi.BevyTicketDao;
import org.seattleoba.data.jdbi.TwitchTicketsDao;
import org.seattleoba.data.util.BevyTicketNumberUtil;

class JdbiBevyTicketStoreTest {
  private static final String JDBI_URL = "jdbc:sqlite:/tmp/soba-test/sample.db";

  private static final Integer EVENT_ID = 42;
  private static final String ACCESS_CODE = "ACCESS-CODE";
  private static final String ORDER_NUMBER = "TTE25065088";
  private static final String PURCHASE_DATE = "2025-11-11 00:48:57+00:00";
  private static final String PURCHASER_NAME = "Kenley";
  private static final String TICKET_NUMBER = "TTA25062005";
  private static final String SECOND_TICKET_NUMBER = "TTA24047055";
  private static final String TICKET_TYPE = "General Admission";
  private static final Integer TWITCH_ID = 84;

  private BevyTicketStore bevyTicketStore;
  private TwitchTicketsDao twitchTicketsDao;

  @BeforeEach
  void setup() {
    final Jdbi jdbi;
    jdbi = Jdbi.create(JDBI_URL);
    jdbi.installPlugin(new SqlObjectPlugin());
    bevyTicketStore = new JdbiBevyTicketStore(jdbi.onDemand(BevyTicketDao.class));
    twitchTicketsDao = jdbi.onDemand(TwitchTicketsDao.class);
  }

  @Test
  void persistsBevyTicket() {
    final org.seattleoba.data.model.BevyTicket bevyTicket =
        new org.seattleoba.data.model.BevyTicket(
            TICKET_NUMBER,
            ORDER_NUMBER,
            PURCHASER_NAME,
            TICKET_TYPE,
            PURCHASE_DATE,
            null,
            ACCESS_CODE,
            "0");

    assertDoesNotThrow(() -> bevyTicketStore.insertBevyTicket(EVENT_ID, bevyTicket));
    final org.seattleoba.data.model.BevyTicket result =
        bevyTicketStore.getTicketByNumber(EVENT_ID, BevyTicketNumberUtil.toInteger(TICKET_NUMBER));
    assertEquals(TICKET_NUMBER, result.ticketNumber());
    assertEquals(ORDER_NUMBER, result.orderNumber());
    assertEquals(PURCHASER_NAME, result.purchaserName());
    assertEquals(TICKET_TYPE, result.ticketType());
    assertEquals(PURCHASE_DATE, result.purchaseDate());
    assertNull(result.checkInDate());
    assertEquals(ACCESS_CODE, result.accessCode());
  }

  @Test
  void retrievesTicketByTwitchId() {
    final Integer ticketNumber = BevyTicketNumberUtil.toInteger(SECOND_TICKET_NUMBER);
    final Random random = new Random();
    final Integer eventId = random.nextInt();
    final Integer twitchId = random.nextInt();
    final org.seattleoba.data.model.BevyTicket bevyTicket =
        new org.seattleoba.data.model.BevyTicket(
            SECOND_TICKET_NUMBER,
            ORDER_NUMBER,
            PURCHASER_NAME,
            TICKET_TYPE,
            PURCHASE_DATE,
            null,
            ACCESS_CODE,
            "0");
    bevyTicketStore.insertBevyTicket(eventId, bevyTicket);
    twitchTicketsDao.insert(twitchId, eventId, ticketNumber);

    assertEquals(
        SECOND_TICKET_NUMBER,
        bevyTicketStore.getTicketByTwitchId(eventId, twitchId).ticketNumber());
    assertFalse(bevyTicketStore.getTicketsForTwitchId(twitchId).isEmpty());
  }
}
