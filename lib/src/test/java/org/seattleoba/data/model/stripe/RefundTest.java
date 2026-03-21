package org.seattleoba.data.model.stripe;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RefundTest {
    private final String REFUND_JSON_FILE_NAME = "/stripe/refund.json";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Refund refund;

    @BeforeEach
    public void setup() throws IOException {
        refund = objectMapper.readValue(RefundTest.class.getResourceAsStream(REFUND_JSON_FILE_NAME), Refund.class);
    }

    @Test
    public void parsesRefund() {
        assertEquals("re_1Nispe2eZvKYlo2Cd31jOCgZ", refund.id());
        assertEquals("ch_1NirD82eZvKYlo2CIvbtLWuY", refund.charge());
        assertEquals("pi_1GszsK2eZvKYlo2CfhZyoZLp", refund.paymentIntent());
    }
}
