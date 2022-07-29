package com.rbs.casestudy.transferservice.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {

    private static final long ACCOUNT_NUMBER = 123L;
    private static final BigDecimal BALANCE = BigDecimal.TEN;
    private Account account;

    @BeforeEach
    public void setup() {
        account = new Account();
        account.setAccountNumber(ACCOUNT_NUMBER);
        account.setBalance(BALANCE);
    }

    @Test
    public void test_account_gettersAndSetters() {
        assertEquals(ACCOUNT_NUMBER, account.getAccountNumber());
        assertEquals(BALANCE, account.getBalance());
    }

    @Test
    public void test_account_constructor() {
        Account constructedAccount = new Account(ACCOUNT_NUMBER, BALANCE);

        assertEquals(ACCOUNT_NUMBER, constructedAccount.getAccountNumber());
        assertEquals(BALANCE, constructedAccount.getBalance());
    }
}
