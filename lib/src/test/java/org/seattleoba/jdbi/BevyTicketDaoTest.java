package org.seattleoba.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.seattleoba.ticketing.jdbi.BevyTicketDao;
import org.seattleoba.ticketing.model.BevyTicket;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BevyTicketDaoTest {
    private static final String ACCESS_CODE = "TESTING";
    private static final Integer EVENT_ID = 42;
    private static final String JDBI_URL = "jdbc:sqlite:/tmp/soba-test/sample.db";
    private static final BigDecimal PRICE = new BigDecimal("4.99");
    private static final String PURCHASER_NAME = "Test Purchaser";
    private static final String TICKET_TYPE = "Test Ticket Type";

    private Jdbi jdbi;

    @BeforeEach
    public void setup() {
        jdbi = Jdbi.create(JDBI_URL);
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    @Test
    public void insertsRowIntoTable() {
        final Instant instant = Instant.now();
        final int ticketNumber = Math.toIntExact(instant.getEpochSecond() % 100000);
        final Long purchaseDate = instant.minusSeconds(60).getEpochSecond();
        final Long checkInDate = instant.plusSeconds(120).getEpochSecond();
        final BevyTicketDao dao = jdbi.onDemand(BevyTicketDao.class);

        assertDoesNotThrow(() ->
                dao.insert(EVENT_ID, ticketNumber, PURCHASER_NAME, TICKET_TYPE, purchaseDate, checkInDate, ACCESS_CODE, PRICE));
        final List<BevyTicket> output = dao.findByTicketNumber(ticketNumber);

        assertFalse(output.isEmpty());
        assertEquals(PURCHASER_NAME, output.getFirst().purchaserName());
        assertEquals(ACCESS_CODE, output.getFirst().accessCode());
        assertEquals(PRICE.toString(), output.getFirst().price());
    }
}
