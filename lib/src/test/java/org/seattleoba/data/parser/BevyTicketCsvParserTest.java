package org.seattleoba.data.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.seattleoba.data.model.BevyTicket;

class BevyTicketCsvParserTest {
  private static final String TICKET_CSV_FILE_NAME = "/tickets.csv";
  private static final String TICKET_WITH_ACCESS_CODE_CSV_FILE_NAME =
      "/tickets-with-access-code.csv";

  private BevyTicketCsvParser bevyTicketCsvParser;

  @BeforeEach
  void setup() {
    bevyTicketCsvParser = new BevyTicketCsvParser();
  }

  @Test
  void parsesCsvFile() {
    final InputStream inputStream =
        BevyTicketCsvParserTest.class.getResourceAsStream(TICKET_CSV_FILE_NAME);
    final List<BevyTicket> tickets = bevyTicketCsvParser.parseCsvFile(inputStream);

    assertFalse(tickets.isEmpty());
    assertEquals(1, tickets.size());
    assertEquals("TTE25065088", tickets.getFirst().orderNumber());
    assertEquals("TTA25062005", tickets.getFirst().ticketNumber());
    assertEquals("Kenley", tickets.getFirst().purchaserName());
  }

  @Test
  void parsesCsvFileWithAccessCode() {
    final InputStream inputStream =
        BevyTicketCsvParserTest.class.getResourceAsStream(TICKET_WITH_ACCESS_CODE_CSV_FILE_NAME);
    final List<BevyTicket> tickets = bevyTicketCsvParser.parseCsvFile(inputStream);

    assertFalse(tickets.isEmpty());
    assertEquals(1, tickets.size());
    assertEquals("TTE25060033", tickets.getFirst().orderNumber());
    assertEquals("TTA25057051", tickets.getFirst().ticketNumber());
    assertEquals("Kenley", tickets.getFirst().purchaserName());
  }
}
