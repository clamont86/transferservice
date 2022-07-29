package com.rbs.casestudy.transferservice.controllers;

import com.rbs.casestudy.transferservice.models.Transaction;
import com.rbs.casestudy.transferservice.models.TransferResponse;
import com.rbs.casestudy.transferservice.repositories.TransactionRepository;
import com.rbs.casestudy.transferservice.service.MoneyTransferService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TransactionController {

    private TransactionRepository transactionRepository;
    private MoneyTransferService moneyTransferService;

    public TransactionController(TransactionRepository transactionRepository, MoneyTransferService moneyTransferService) {
        this.transactionRepository = transactionRepository;
        this.moneyTransferService = moneyTransferService;
    }

    @GetMapping("/transactions")
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @GetMapping("/transactions/sourceAccountNumber/{sourceAccountNumber}")
    public List<Transaction> findAllBySourceAccountNumber(@PathVariable Long sourceAccountNumber) {
        return transactionRepository.findAllBySourceAccountNumber(sourceAccountNumber);
    }

    @GetMapping("/transactions/destinationAccountNumber/{destinationAccountNumber}")
    public List<Transaction> findAllByDestinationAccountNumber(@PathVariable Long destinationAccountNumber) {
        return transactionRepository.findAllByDestinationAccountNumber(destinationAccountNumber);
    }

    @PostMapping("/transactions")
    public TransferResponse performTransaction(@RequestBody @Valid Transaction transaction) {
        TransferResponse response = moneyTransferService.performTransaction(transaction);
        Transaction persistedTransaction = transactionRepository.save(transaction);
        response.setTransactionId(persistedTransaction.getId());
        return response;
    }
}
