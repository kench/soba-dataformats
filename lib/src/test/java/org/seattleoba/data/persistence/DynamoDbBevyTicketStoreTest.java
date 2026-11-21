package org.seattleoba.data.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.seattleoba.data.dynamodb.bean.BevyTicket;
import org.seattleoba.data.dynamodb.bean.EventRegistration;
import org.seattleoba.data.util.BevyTicketNumberUtil;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.dynamodb.services.local.embedded.DynamoDBEmbedded;

class DynamoDbBevyTicketStoreTest {
  private static final Integer EVENT_ID = 42;
  private static final String ACCESS_CODE = "ACCESS-CODE";
  private static final String ORDER_NUMBER = "TTE25065088";
  private static final String PURCHASE_DATE = "2025-11-11 00:48:57+00:00";
  private static final String PURCHASER_NAME = "Kenley";
  private static final String TICKET_NUMBER = "TTA25062005";
  private static final String TICKET_TYPE = "General Admission";
  private static final String CHECK_IN_DATE = "2025-11-15 18:30:00+00:00";
  private static final Integer TWITCH_ID = 123456;
  private DynamoDbEnhancedClient dynamoDbEnhancedClient;
  private DynamoDbTable<BevyTicket> bevyTicketsTable;
  private DynamoDbTable<EventRegistration> eventRegistrationTable;
  private BevyTicketStore bevyTicketStore;

  @BeforeEach
  void setup() {
    dynamoDbEnhancedClient =
        DynamoDbEnhancedClient.builder()
            .dynamoDbClient(DynamoDBEmbedded.create().dynamoDbClient())
            .build();
    bevyTicketsTable =
        dynamoDbEnhancedClient.table("BevyTickets", TableSchema.fromBean(BevyTicket.class));
    bevyTicketsTable.createTable();
    eventRegistrationTable =
        dynamoDbEnhancedClient.table(
            "EventRegistrations", TableSchema.fromBean(EventRegistration.class));
    eventRegistrationTable.createTable();
    bevyTicketStore = new DynamoDbBevyTicketStore(bevyTicketsTable, eventRegistrationTable);
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
  void findsTicketsRegisteredToTwitchAccount() {
    final org.seattleoba.data.model.BevyTicket bevyTicket =
        new org.seattleoba.data.model.BevyTicket(
            TICKET_NUMBER,
            ORDER_NUMBER,
            PURCHASER_NAME,
            TICKET_TYPE,
            PURCHASE_DATE,
            CHECK_IN_DATE,
            ACCESS_CODE,
            "12.5");
    bevyTicketStore.insertBevyTicket(EVENT_ID, bevyTicket);
    final EventRegistration eventRegistration = new EventRegistration();
    eventRegistration.setId(BevyTicketNumberUtil.toInteger(TICKET_NUMBER));
    eventRegistration.setEventId(EVENT_ID);
    eventRegistration.setTwitchId(TWITCH_ID);
    eventRegistrationTable.putItem(eventRegistration);

    final org.seattleoba.data.model.BevyTicket byTwitchId =
        bevyTicketStore.getTicketByTwitchId(EVENT_ID, TWITCH_ID);
    final List<org.seattleoba.data.model.BevyTicket> forTwitchId =
        bevyTicketStore.getTicketsForTwitchId(TWITCH_ID);

    assertEquals(TICKET_NUMBER, byTwitchId.ticketNumber());
    assertEquals(CHECK_IN_DATE, byTwitchId.checkInDate());
    assertEquals("12.5", byTwitchId.price());
    assertEquals(List.of(byTwitchId), forTwitchId);
  }

  @Test
  void readsTicketWithoutOptionalFields() {
    final BevyTicket ticket = new BevyTicket();
    ticket.setEventId(EVENT_ID);
    ticket.setId(1);
    ticket.setTicketId(TICKET_NUMBER);
    bevyTicketsTable.putItem(ticket);

    final org.seattleoba.data.model.BevyTicket result =
        bevyTicketStore.getTicketByNumber(EVENT_ID, 1);

    assertNull(result.purchaseDate());
    assertNull(result.checkInDate());
    assertNull(result.price());
  }
}
