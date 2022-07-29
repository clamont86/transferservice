package com.rbs.casestudy.transferservice.services;

import com.rbs.casestudy.transferservice.models.Account;
import com.rbs.casestudy.transferservice.models.Transaction;
import com.rbs.casestudy.transferservice.models.TransferResponse;
import com.rbs.casestudy.transferservice.repositories.AccountRepository;
import com.rbs.casestudy.transferservice.service.MoneyTransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MoneyTransferServiceTest {

    private static final long SOURCE_ACCOUNT_NUMBER = 11111111L;
    private static final long DESTINATION_ACCOUNT_NUMBER = 22222222L;
    private static final BigDecimal AMOUNT = new BigDecimal("100.00");
    private static final BigDecimal SOURCE_ACCOUNT_BALANCE = new BigDecimal("200.00");
    private static final BigDecimal DESTINATION_ACCOUNT_BALANCE = new BigDecimal("500.00");
    private static final BigDecimal SOURCE_BALANCE_AFTER_TRANSFER = new BigDecimal("100.00");
    private static final BigDecimal DESTINATION_BALANCE_AFTER_TRANSFER = new BigDecimal("600.00");
    private static final long TRANSACTION_ID = 1L;
    private static final long UNRECOGNISED_ACCOUNT_NUMBER = 2L;
    private final ArgumentCaptor<List<Account>> accountArgumentCaptor = ArgumentCaptor.forClass(List.class);

    private Account sourceAccount;
    private Account destinationAccount;
    private Transaction transaction;

    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private MoneyTransferService service;

    @BeforeEach
    public void setup() {
        sourceAccount = new Account(SOURCE_ACCOUNT_NUMBER, SOURCE_ACCOUNT_BALANCE);
        destinationAccount = new Account(DESTINATION_ACCOUNT_NUMBER, DESTINATION_ACCOUNT_BALANCE);

        transaction = new Transaction();
        transaction.setId(TRANSACTION_ID);
        transaction.setSourceAccountNumber(SOURCE_ACCOUNT_NUMBER);
        transaction.setDestinationAccountNumber(DESTINATION_ACCOUNT_NUMBER);
        transaction.setAmount(AMOUNT);

        when(accountRepository.findById(SOURCE_ACCOUNT_NUMBER)).thenReturn(Optional.ofNullable(sourceAccount));
        when(accountRepository.findById(DESTINATION_ACCOUNT_NUMBER)).thenReturn(Optional.ofNullable(destinationAccount));
    }

    @Test
    public void test_transferMoneyService_perform_happyPath() {
        TransferResponse response = service.performTransaction(transaction);

        assertAll("repository calls",
                () -> verify(accountRepository, times(1)).findById(SOURCE_ACCOUNT_NUMBER),
                () -> verify(accountRepository, times(1)).findById(DESTINATION_ACCOUNT_NUMBER),
                () -> verify(accountRepository, times(1)).saveAll(accountArgumentCaptor.capture())
        );

        Account persistedSourceAccount = accountArgumentCaptor.getValue().get(0);
        Account persistedDestinationAccount = accountArgumentCaptor.getValue().get(1);

        assertAll("updated balances",
                () -> assertEquals(SOURCE_BALANCE_AFTER_TRANSFER, sourceAccount.getBalance()),
                () -> assertEquals(DESTINATION_BALANCE_AFTER_TRANSFER, destinationAccount.getBalance())
        );

        assertAll("response",
                () -> assertEquals(AMOUNT, response.getAmount()),
                () -> assertEquals(persistedSourceAccount, response.getSourceAccount()),
                () -> assertEquals(persistedDestinationAccount, response.getDestinationAccount())
        );
    }


    @Test
    public void test_transferMoneyService_perform_sourceAccountNotFound() {
        transaction.setSourceAccountNumber(UNRECOGNISED_ACCOUNT_NUMBER);
        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> service.performTransaction(transaction));

        assertAll("repository calls",
                () -> verify(accountRepository, times(1)).findById(UNRECOGNISED_ACCOUNT_NUMBER),
                () -> verify(accountRepository, times(0)).findById(DESTINATION_ACCOUNT_NUMBER),
                () -> verify(accountRepository, times(0)).saveAll(any(List.class))
        );

        assertEquals("not found", thrownException.getMessage());
    }

    @Test
    public void test_transferMoneyService_perform_destinationAccountNotFound() {
        transaction.setDestinationAccountNumber(UNRECOGNISED_ACCOUNT_NUMBER);
        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> service.performTransaction(transaction));

        assertAll("repository calls",
                () -> verify(accountRepository, times(1)).findById(SOURCE_ACCOUNT_NUMBER),
                () -> verify(accountRepository, times(1)).findById(UNRECOGNISED_ACCOUNT_NUMBER),
                () -> verify(accountRepository, times(0)).saveAll(any(List.class))
        );

        assertEquals("not found", thrownException.getMessage());
    }
}
