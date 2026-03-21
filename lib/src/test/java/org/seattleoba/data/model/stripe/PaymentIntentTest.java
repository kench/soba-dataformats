package org.seattleoba.data.model.stripe;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentIntentTest {
    private final String PAYMENT_INTENT_JSON_FILE_NAME = "/stripe/payment-intent.json";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private PaymentIntent paymentIntent;

    @BeforeEach
    public void setup() throws IOException {
        paymentIntent = objectMapper.readValue(PaymentIntentTest.class.getResourceAsStream(PAYMENT_INTENT_JSON_FILE_NAME), PaymentIntent.class);
    }

    @Test
    public void parsesPaymentIntent() {
        assertEquals("pi_3MtwBwLkdIwHu7ix28a3tqPa", paymentIntent.id());
        assertEquals("cus_NffrFeUfNV2Hib", paymentIntent.customer());
        assertEquals("Description", paymentIntent.description());
        assertEquals("ch_3MmlLrLkdIwHu7ix0snN0B15", paymentIntent.latestCharge());
    }
}
