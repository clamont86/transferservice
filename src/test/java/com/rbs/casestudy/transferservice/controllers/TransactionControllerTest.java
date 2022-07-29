package com.rbs.casestudy.transferservice.controllers;

import com.rbs.casestudy.transferservice.models.Transaction;
import com.rbs.casestudy.transferservice.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionControllerTest {

    private static final List<Transaction> ALL_TRANSACTIONS = new ArrayList<>();
    private static final Transaction TRANSACTION = new Transaction();
    private static final long SOURCE_ACCOUNT_NUMBER = 123L;
    private static final long DESTINATION_ACCOUNT_NUMBER = 456L;
    private ArgumentCaptor<Transaction> transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);

    @Mock
    private TransactionRepository transactionRepository;

    private TransactionController controller;

    @BeforeEach
    public void setup() {
        controller = new TransactionController(transactionRepository);

        when(transactionRepository.save(transactionArgumentCaptor.capture())).thenReturn(TRANSACTION);
        when(transactionRepository.findAll()).thenReturn(ALL_TRANSACTIONS);
        when(transactionRepository.findAllBySourceAccountNumber(SOURCE_ACCOUNT_NUMBER)).thenReturn(ALL_TRANSACTIONS);
        when(transactionRepository.findAllByDestinationAccountNumber(DESTINATION_ACCOUNT_NUMBER)).thenReturn(ALL_TRANSACTIONS);
    }

    @Test
    public void test_transactionController_findAll() {
        List<Transaction> response = controller.findAllTransactions();
        verify(transactionRepository, times(1)).findAll();
        assertEquals(ALL_TRANSACTIONS, response);
    }

    @Test
    public void test_transactionController_findAllBySourceAccountNumber() {
        List<Transaction> response = controller.findAllBySourceAccountNumber(SOURCE_ACCOUNT_NUMBER);
        verify(transactionRepository, times(1)).findAllBySourceAccountNumber(SOURCE_ACCOUNT_NUMBER);
        assertEquals(ALL_TRANSACTIONS, response);
    }

    @Test
    public void test_transactionController_findAllByDestinationAccountNumber() {
        List<Transaction> response = controller.findAllByDestinationAccountNumber(DESTINATION_ACCOUNT_NUMBER);
        verify(transactionRepository, times(1)).findAllByDestinationAccountNumber(DESTINATION_ACCOUNT_NUMBER);
        assertEquals(ALL_TRANSACTIONS, response);
    }

    @Test
    public void test_transactionController_performTransaction() {
        Transaction response = controller.performTransaction(TRANSACTION);
        verify(transactionRepository, times(1)).save(TRANSACTION);
        assertEquals(TRANSACTION, response);
        assertEquals(TRANSACTION, transactionArgumentCaptor.getValue());
    }
}
