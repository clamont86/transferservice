package com.rbs.casestudy.transferservice.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerErrorHandlersTest {

    private static final long ACCOUNT_NUMBER = 1L;
    private static final InsufficientFundsException INSUFFICIENT_FUNDS_EXCEPTION = new InsufficientFundsException(ACCOUNT_NUMBER, BigDecimal.TEN, BigDecimal.ZERO);
    private static final AccountNotFoundException ACCOUNT_NOT_FOUND_EXCEPTION = new AccountNotFoundException(ACCOUNT_NUMBER);

    private ControllerErrorHandlers controllerErrorHandlers;

    @BeforeEach
    public void setup() {
        controllerErrorHandlers = new ControllerErrorHandlers();
    }

    @Test
    public void test_controllerErrorHandlers_accountNotFoundHandler() {
        String message = controllerErrorHandlers.accountNotFoundHandler(ACCOUNT_NOT_FOUND_EXCEPTION);

        assertEquals("No account found with accountNumber: 1", message);
    }

    @Test
    public void test_controllerErrorHandlers_insufficientFundsHandler() {
        String message = controllerErrorHandlers.insufficientFundsHandler(INSUFFICIENT_FUNDS_EXCEPTION);

        assertEquals("Insufficient Funds in account number: 1. Current balance: £0, Requested Transfer: £10", message);
    }
}
