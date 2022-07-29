package com.rbs.casestudy.transferservice.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionResponseTest {

    private static final long TRANSACTION_ID = 1L;
    private static final Account SOURCE_ACCOUNT = new Account();
    private static final Account DESTINATION_ACCOUNT = new Account();
    private static final BigDecimal AMOUNT = BigDecimal.ONE;
    private TransferResponse transferResponse;

    @BeforeEach
    public void setup() {
        transferResponse = new TransferResponse();
        transferResponse.setTransactionId(TRANSACTION_ID);
        transferResponse.setSourceAccount(SOURCE_ACCOUNT);
        transferResponse.setDestinationAccount(DESTINATION_ACCOUNT);
        transferResponse.setAmount(AMOUNT);
    }

    @Test
    public void test_transactionResponse_gettersAndSetters() {
        assertEquals(TRANSACTION_ID, transferResponse.getTransactionId());
        assertEquals(SOURCE_ACCOUNT, transferResponse.getSourceAccount());
        assertEquals(DESTINATION_ACCOUNT, transferResponse.getDestinationAccount());
        assertEquals(AMOUNT, transferResponse.getAmount());
    }
}
