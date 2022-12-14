package com.rbs.casestudy.transferservice.service;

import com.rbs.casestudy.transferservice.exceptions.AccountNotFoundException;
import com.rbs.casestudy.transferservice.exceptions.InsufficientFundsException;
import com.rbs.casestudy.transferservice.models.Account;
import com.rbs.casestudy.transferservice.models.Transaction;
import com.rbs.casestudy.transferservice.models.TransferResponse;
import com.rbs.casestudy.transferservice.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class MoneyTransferService {

    private static final String SOURCE_FIELD = "Source";
    private static final String DESTINATION_FIELD = "Destination";
    private final AccountRepository accountRepository;

    public MoneyTransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public TransferResponse performTransaction(Transaction transaction) {
        Account sourceAccount = findAccount(transaction.getSourceAccountNumber(), SOURCE_FIELD);
        Account destinationAccount = findAccount(transaction.getDestinationAccountNumber(), DESTINATION_FIELD);

        if (isInsufficientFundsInSourceAccount(sourceAccount.getBalance(), transaction.getAmount())) {
            throw new InsufficientFundsException(sourceAccount.getAccountNumber(), transaction.getAmount(), sourceAccount.getBalance());
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(transaction.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(transaction.getAmount()));

        accountRepository.saveAll(Arrays.asList(sourceAccount, destinationAccount));
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setSourceAccount(sourceAccount);
        transferResponse.setDestinationAccount(destinationAccount);
        transferResponse.setAmount(transaction.getAmount());
        return transferResponse;
    }

    private Account findAccount(Long accountNumber, String field) {
        return accountRepository.findById(accountNumber).orElseThrow(
                () -> new AccountNotFoundException(accountNumber, field)
        );
    }

    private boolean isInsufficientFundsInSourceAccount(BigDecimal balance, BigDecimal amount) {
        return balance.compareTo(amount) < 0;
    }
}
