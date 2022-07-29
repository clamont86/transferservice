package com.rbs.casestudy.transferservice.models;

import com.rbs.casestudy.transferservice.helpers.TestHelpers;
import com.rbs.casestudy.transferservice.helpers.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AccountTest {

    private static final long ACCOUNT_NUMBER = 123L;
    private static final BigDecimal BALANCE = BigDecimal.TEN;
    private Account account;

    @Autowired
    private TestHelpers<Account> testHelpers;

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

    @Test
    public void test_account_withNullAccountNumber() {
        account.setAccountNumber(null);

        List<ValidationError> errors = testHelpers.getValidationMessages(account);

        assertEquals(1, errors.size());
        assertEquals("accountNumber", errors.get(0).getField());
        assertEquals("must not be null", errors.get(0).getMessage());
    }

    @Test
    public void test_account_withAccountNumberTooLow() {
        account.setAccountNumber(0L);

        List<ValidationError> errors = testHelpers.getValidationMessages(account);

        assertEquals(1, errors.size());
        assertEquals("accountNumber", errors.get(0).getField());
        assertEquals("must be greater than or equal to 1", errors.get(0).getMessage());
    }

    @Test
    public void test_account_withAccountNumberTooHigh() {
        account.setAccountNumber(100000000L);

        List<ValidationError> errors = testHelpers.getValidationMessages(account);

        assertEquals(1, errors.size());
        assertEquals("accountNumber", errors.get(0).getField());
        assertEquals("must be less than or equal to 99999999", errors.get(0).getMessage());
    }

    @Test
    public void test_account_withNegativeBalance() {
        account.setBalance(new BigDecimal("-0.01"));

        List<ValidationError> errors = testHelpers.getValidationMessages(account);

        assertEquals(1, errors.size());
        assertEquals("balance", errors.get(0).getField());
        assertEquals("must be greater than or equal to 0", errors.get(0).getMessage());
    }

    @Test
    public void test_account_withNullBalance() {
        account.setBalance(null);

        List<ValidationError> errors = testHelpers.getValidationMessages(account);

        assertEquals(1, errors.size());
        assertEquals("balance", errors.get(0).getField());
        assertEquals("must not be null", errors.get(0).getMessage());
    }
}
