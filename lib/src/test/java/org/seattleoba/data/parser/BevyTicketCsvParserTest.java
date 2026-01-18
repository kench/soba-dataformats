package org.seattleoba.data.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.seattleoba.data.model.BevyTicket;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BevyTicketCsvParserTest {
    private final String TICKET_CSV_FILE_NAME = "/tickets.csv";
    private final String TICKET_WITH_ACCESS_CODE_CSV_FILE_NAME = "/tickets-with-access-code.csv";

    private BevyTicketCsvParser bevyTicketCsvParser;

    @BeforeEach
    public void setup() {
        bevyTicketCsvParser = new BevyTicketCsvParser();
    }

    @Test
    public void parsesCsvFile() {
        final InputStream inputStream = BevyTicketCsvParserTest.class
                .getResourceAsStream(TICKET_CSV_FILE_NAME);
        final List<BevyTicket> tickets = bevyTicketCsvParser.parseCsvFile(inputStream);

        assertFalse(tickets.isEmpty());
        assertEquals(1, tickets.size());
        assertEquals("TTE25065088", tickets.getFirst().orderNumber());
        assertEquals("TTA25062005", tickets.getFirst().ticketNumber());
        assertEquals("Kenley", tickets.getFirst().purchaserName());
    }

    @Test
    public void parsesCsvFileWithAccessCode() {
        final InputStream inputStream = BevyTicketCsvParserTest.class
                .getResourceAsStream(TICKET_WITH_ACCESS_CODE_CSV_FILE_NAME);
        final List<BevyTicket> tickets = bevyTicketCsvParser.parseCsvFile(inputStream);

        assertFalse(tickets.isEmpty());
        assertEquals(1, tickets.size());
        assertEquals("TTE25060033", tickets.getFirst().orderNumber());
        assertEquals("TTA25057051", tickets.getFirst().ticketNumber());
        assertEquals("Kenley", tickets.getFirst().purchaserName());
    }
}
