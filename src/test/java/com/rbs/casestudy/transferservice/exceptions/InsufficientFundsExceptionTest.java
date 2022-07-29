package com.rbs.casestudy.transferservice.exceptions;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InsufficientFundsExceptionTest {
    private InsufficientFundsException insufficientFundsException;

    @Test
    public void test_insufficientFundsException() {
        insufficientFundsException = new InsufficientFundsException(1L, BigDecimal.TEN, BigDecimal.ZERO);

        assertEquals("Insufficient Funds in account number: 1. Current balance: £0, Requested Transfer: £10", insufficientFundsException.getMessage());
    }
}
