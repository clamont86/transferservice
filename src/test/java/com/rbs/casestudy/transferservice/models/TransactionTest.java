package com.rbs.casestudy.transferservice.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {

    private static final long ID = 123L;
    private static final long SOURCE_ACCOUNT_NUMBER = 456L;
    private static final long DESTINATION_ACCOUNT_NUMBER = 789L;
    private static final BigDecimal AMOUNT = BigDecimal.TEN;
    private Transaction transaction;

    @BeforeEach
    public void setup() {
        transaction = new Transaction();
        transaction.setId(ID);
        transaction.setSourceAccountNumber(SOURCE_ACCOUNT_NUMBER);
        transaction.setDestinationAccountNumber(DESTINATION_ACCOUNT_NUMBER);
        transaction.setAmount(AMOUNT);
    }

    @Test
    public void test_transaction_gettersAndSetters() {
        assertEquals(ID, transaction.getId());
        assertEquals(SOURCE_ACCOUNT_NUMBER, transaction.getSourceAccountNumber());
        assertEquals(DESTINATION_ACCOUNT_NUMBER, transaction.getDestinationAccountNumber());
        assertEquals(AMOUNT, transaction.getAmount());
    }
}
