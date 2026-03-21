package org.seattleoba.data.model.stripe;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {
    private final String CUSTOMER_JSON_FILE_NAME = "/stripe/customer.json";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Customer customer;

    @BeforeEach
    public void setup() throws IOException {
        customer = objectMapper.readValue(CustomerTest.class.getResourceAsStream(CUSTOMER_JSON_FILE_NAME), Customer.class);
    }

    @Test
    public void parsesCustomer() {
        assertEquals("cus_NffrFeUfNV2Hib", customer.id());
        assertEquals("soba@example.com", customer.email());
        assertEquals("SeattleOBA", customer.name());
    }
}
