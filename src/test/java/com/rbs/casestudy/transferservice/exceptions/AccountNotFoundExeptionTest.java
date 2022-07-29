package com.rbs.casestudy.transferservice.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountNotFoundExeptionTest {

    private AccountNotFoundException accountNotFoundException;

    @Test
    public void test_accountNotFoundExecption_withAccountNumberOnly() {
        accountNotFoundException = new AccountNotFoundException(1L);

        assertEquals("No account found with accountNumber: 1", accountNotFoundException.getMessage());
    }

    @Test
    public void test_accountNotFoundExecption_withAccountNumberAndField() {
        accountNotFoundException = new AccountNotFoundException(1L, "foo");

        assertEquals("foo account not found with accountNumber: 1", accountNotFoundException.getMessage());
    }
}
