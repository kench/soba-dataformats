package org.seattleoba.data.model.stripe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChargeTest {
  private static final String CHARGE_JSON_FILE_NAME = "/stripe/charge.json";

  private final ObjectMapper objectMapper = new ObjectMapper();

  private Charge charge;

  @BeforeEach
  void setup() throws IOException {
    charge =
        objectMapper.readValue(
            ChargeTest.class.getResourceAsStream(CHARGE_JSON_FILE_NAME), Charge.class);
  }

  @Test
  void parsesCharge() {
    assertEquals("ch_3MmlLrLkdIwHu7ix0snN0B15", charge.id());
    assertEquals("cus_NffrFeUfNV2Hib", charge.customer());
    assertEquals("pi_3MtwBwLkdIwHu7ix28a3tqPa", charge.paymentIntent());
  }
}
