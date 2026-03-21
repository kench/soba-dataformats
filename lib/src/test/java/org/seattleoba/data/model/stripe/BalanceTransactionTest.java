package org.seattleoba.data.model.stripe;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BalanceTransactionTest {
    private final String BALANCE_TRANSACTION_JSON_FILE_NAME = "/stripe/balance-transaction.json";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private BalanceTransaction balanceTransaction;

    @BeforeEach
    public void setup() throws IOException {
        balanceTransaction = objectMapper.readValue(CustomerTest.class.getResourceAsStream(BALANCE_TRANSACTION_JSON_FILE_NAME), BalanceTransaction.class);
    }

    @Test
    public void parsesBalanceTransaction() {
        assertEquals("txn_1MiN3gLkdIwHu7ixxapQrznl", balanceTransaction.id());
        assertEquals(-400, balanceTransaction.amount());
        assertEquals("usd", balanceTransaction.currency());
        assertEquals("Description", balanceTransaction.description());
        assertEquals(0, balanceTransaction.fee());
        assertEquals(-400, balanceTransaction.net());
        assertEquals("tr_1MiN3gLkdIwHu7ixNCZvFdgA", balanceTransaction.source());
        assertEquals("available", balanceTransaction.status());
        assertEquals("transfer", balanceTransaction.type());
    }
}
