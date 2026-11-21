package org.seattleoba.data.model.stripe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerTest {
  private static final String CUSTOMER_JSON_FILE_NAME = "/stripe/customer.json";

  private final ObjectMapper objectMapper = new ObjectMapper();

  private Customer customer;

  @BeforeEach
  void setup() throws IOException {
    customer =
        objectMapper.readValue(
            CustomerTest.class.getResourceAsStream(CUSTOMER_JSON_FILE_NAME), Customer.class);
  }

  @Test
  void parsesCustomer() {
    assertEquals("cus_NffrFeUfNV2Hib", customer.id());
    assertEquals("soba@example.com", customer.email());
    assertEquals("SeattleOBA", customer.name());
  }
}
