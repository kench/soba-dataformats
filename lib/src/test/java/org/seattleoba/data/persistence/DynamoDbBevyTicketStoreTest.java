package org.seattleoba.data.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.seattleoba.data.dynamodb.bean.BevyTicket;
import org.seattleoba.data.util.BevyTicketNumberUtil;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.dynamodb.services.local.embedded.DynamoDBEmbedded;

import static org.junit.jupiter.api.Assertions.*;

public class DynamoDbBevyTicketStoreTest {
    private static final Integer EVENT_ID = 42;
    private static final String ACCESS_CODE = "ACCESS-CODE";
    private static final String ORDER_NUMBER = "TTE25065088";
    private static final String PURCHASE_DATE = "2025-11-11 00:48:57+00:00";
    private static final String PURCHASER_NAME = "Kenley";
    private static final String TICKET_NUMBER = "TTA25062005";
    private static final String TICKET_TYPE = "General Admission";
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private DynamoDbTable<BevyTicket> bevyTicketsTable;
    private BevyTicketStore bevyTicketStore;

    @BeforeEach
    public void setup() {
        dynamoDbEnhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(DynamoDBEmbedded.create().dynamoDbClient())
                .build();
        bevyTicketsTable = dynamoDbEnhancedClient.table("BevyTickets", TableSchema.fromBean(BevyTicket.class));
        bevyTicketsTable.createTable();
        bevyTicketStore = new DynamoDbBevyTicketStore(bevyTicketsTable);
    }

    @Test
    public void persistsBevyTicket() {
        final org.seattleoba.data.model.BevyTicket bevyTicket = new org.seattleoba.data.model.BevyTicket(
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
}
