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
public class TransactionTest {

    private static final long ID = 123L;
    private static final long SOURCE_ACCOUNT_NUMBER = 456L;
    private static final long DESTINATION_ACCOUNT_NUMBER = 789L;
    private static final BigDecimal AMOUNT = BigDecimal.TEN;

    @Autowired
    private TestHelpers<Transaction> testHelpers;

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

    @Test
    public void test_transaction_withNullSourceAccountNumber() {
        transaction.setSourceAccountNumber(null);

        List<ValidationError> errors = testHelpers.getValidationMessages(transaction);

        assertEquals(1, errors.size());
        assertEquals("sourceAccountNumber", errors.get(0).getField());
        assertEquals("must not be null", errors.get(0).getMessage());
    }

    @Test
    public void test_transaction_withSourceAccountNumberTooLow() {
        transaction.setSourceAccountNumber(0L);

        List<ValidationError> errors = testHelpers.getValidationMessages(transaction);

        assertEquals(1, errors.size());
        assertEquals("sourceAccountNumber", errors.get(0).getField());
        assertEquals("must be greater than or equal to 1", errors.get(0).getMessage());
    }

    @Test
    public void test_transaction_withSourceAccountNumberTooHigh() {
        transaction.setSourceAccountNumber(100000000L);

        List<ValidationError> errors = testHelpers.getValidationMessages(transaction);

        assertEquals(1, errors.size());
        assertEquals("sourceAccountNumber", errors.get(0).getField());
        assertEquals("must be less than or equal to 99999999", errors.get(0).getMessage());
    }

    @Test
    public void test_transaction_withNullDestinationAccountNumber() {
        transaction.setDestinationAccountNumber(null);

        List<ValidationError> errors = testHelpers.getValidationMessages(transaction);

        assertEquals(1, errors.size());
        assertEquals("destinationAccountNumber", errors.get(0).getField());
        assertEquals("must not be null", errors.get(0).getMessage());
    }

    @Test
    public void test_transaction_withDestinationAccountNumberTooLow() {
        transaction.setDestinationAccountNumber(0L);

        List<ValidationError> errors = testHelpers.getValidationMessages(transaction);

        assertEquals(1, errors.size());
        assertEquals("destinationAccountNumber", errors.get(0).getField());
        assertEquals("must be greater than or equal to 1", errors.get(0).getMessage());
    }

    @Test
    public void test_transaction_withDestinationAccountNumberTooHigh() {
        transaction.setDestinationAccountNumber(100000000L);

        List<ValidationError> errors = testHelpers.getValidationMessages(transaction);

        assertEquals(1, errors.size());
        assertEquals("destinationAccountNumber", errors.get(0).getField());
        assertEquals("must be less than or equal to 99999999", errors.get(0).getMessage());
    }


    @Test
    public void test_transaction_withNullAmount() {
        transaction.setAmount(null);

        List<ValidationError> errors = testHelpers.getValidationMessages(transaction);

        assertEquals(1, errors.size());
        assertEquals("amount", errors.get(0).getField());
        assertEquals("must not be null", errors.get(0).getMessage());
    }

    @Test
    public void test_transaction_withAmountTooLow() {
        transaction.setAmount(BigDecimal.ZERO);

        List<ValidationError> errors = testHelpers.getValidationMessages(transaction);

        assertEquals(1, errors.size());
        assertEquals("amount", errors.get(0).getField());
        assertEquals("must be greater than or equal to 1", errors.get(0).getMessage());
    }
}
