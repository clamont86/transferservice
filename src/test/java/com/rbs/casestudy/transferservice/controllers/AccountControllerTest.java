package com.rbs.casestudy.transferservice.controllers;

import com.rbs.casestudy.transferservice.models.Account;
import com.rbs.casestudy.transferservice.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountControllerTest {

    private static final long ACCOUNT_NUMBER_1 = 11111111L;
    private static final BigDecimal BALANCE_1 = new BigDecimal("1234.56");
    private static final long ACCOUNT_NUMBER_2 = 22222222L;
    private static final BigDecimal BALANCE_2 = new BigDecimal("-987.65");
    private static final long ACCOUNT_NUMBER_3 = 33333333L;
    private static final BigDecimal BALANCE_3 = new BigDecimal("100.00");
    private static final long UNKNOWN_ACCOUNT_NUMBER = 99999999L;
    private static final Account ACCOUNT_1 = new Account(ACCOUNT_NUMBER_1, BALANCE_1);
    private static final Account ACCOUNT_2 = new Account(ACCOUNT_NUMBER_2, BALANCE_2);
    private static final Account ACCOUNT_3 = new Account(ACCOUNT_NUMBER_3, BALANCE_3);
    private static final List<Account> ACCOUNTS_LIST = Arrays.asList(ACCOUNT_1, ACCOUNT_2);

    private ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);

    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountController controller;

    @BeforeEach
    public void setup() {
        when(accountRepository.findAll()).thenReturn(ACCOUNTS_LIST);
        when(accountRepository.findById(ACCOUNT_NUMBER_1)).thenReturn(Optional.of(ACCOUNT_1));
        when(accountRepository.save(accountArgumentCaptor.capture())).thenReturn(ACCOUNT_3);
    }


    @Test
    public void test_accountController_listAllAccounts() {
        List<Account> returnedAccounts = controller.listAllAccounts();

        verify(accountRepository, times(1)).findAll();
        assertEquals(ACCOUNTS_LIST, returnedAccounts);
    }

    @Test
    public void test_accountController_findByAccountNumber() {
        Account returnedAccount = controller.findByAccountNumber(ACCOUNT_NUMBER_1);

        verify(accountRepository, times(1)).findById(ACCOUNT_NUMBER_1);
        assertEquals(ACCOUNT_1, returnedAccount);
    }

    @Test
    public void test_accountController_findByAccountNumber_whenAccountNotFound() {
        RuntimeException thrownException = assertThrows(RuntimeException.class ,() -> controller.findByAccountNumber(UNKNOWN_ACCOUNT_NUMBER));

        assertEquals("No account found with accountNumber: 99999999", thrownException.getMessage());
    }

    @Test
    public void test_accountController_addNewAccount_persistsNewAccountAndReturns() {
        Account returnedAccount = controller.addNewAccount(ACCOUNT_3);

        verify(accountRepository, times(1)).save(ACCOUNT_3);
        assertEquals(ACCOUNT_NUMBER_3, returnedAccount.getAccountNumber());
        assertEquals(BALANCE_3, returnedAccount.getBalance());

        Account persistedAccount = accountArgumentCaptor.getValue();
        assertEquals(ACCOUNT_NUMBER_3, persistedAccount.getAccountNumber());
        assertEquals(BALANCE_3, persistedAccount.getBalance());
    }

    @Test
    public void test_accountController_updateBalance_updatesBalanceAndReturns() {
        when(accountRepository.save(accountArgumentCaptor.capture())).thenReturn(ACCOUNT_1);
        BigDecimal updatedBalance = new BigDecimal("1.00");
        Account returnedAccount = controller.updateBalance(new Account(ACCOUNT_NUMBER_1, updatedBalance), ACCOUNT_NUMBER_1);

        verify(accountRepository, times(1)).findById(ACCOUNT_NUMBER_1);
        verify(accountRepository, times(1)).save(accountArgumentCaptor.capture());

        assertEquals(ACCOUNT_NUMBER_1, returnedAccount.getAccountNumber());
        assertEquals(updatedBalance, returnedAccount.getBalance());

        Account persistedAccount = accountArgumentCaptor.getValue();
        assertEquals(ACCOUNT_NUMBER_1, persistedAccount.getAccountNumber());
        assertEquals(updatedBalance, persistedAccount.getBalance());
    }

    @Test
    public void test_accountController_updateBalance_whenAccountNotFound() {
        RuntimeException thrownException = assertThrows(RuntimeException.class ,() -> controller.updateBalance(ACCOUNT_1, UNKNOWN_ACCOUNT_NUMBER));

        assertEquals("No account found with accountNumber: 99999999", thrownException.getMessage());
        verify(accountRepository, times(0)).save(any(Account.class));
    }

    @Test
    public void test_accountController_deleteAccount_deletesRequestedAccount() {
        Long returnedAccountNumber = controller.deleteAccount(ACCOUNT_NUMBER_1);

        verify(accountRepository, times(1)).deleteById(ACCOUNT_NUMBER_1);
        assertEquals(ACCOUNT_NUMBER_1, returnedAccountNumber);
    }
}
