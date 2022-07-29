package com.rbs.casestudy.transferservice.controllers;

import com.rbs.casestudy.transferservice.models.Transaction;
import com.rbs.casestudy.transferservice.repositories.TransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    private TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @GetMapping("/transactions")
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @GetMapping("/transactions/sourceAccountNumber/{sourceAccountNumber}")
    public List<Transaction> findAllBySourceAccountNumber(@PathVariable Long sourceAccountNumber) { //TODO: validate
        return transactionRepository.findAllBySourceAccountNumber(sourceAccountNumber);
    }

    @GetMapping("/transactions/destinationAccountNumber/{destinationAccountNumber}")
    public List<Transaction> findAllByDestinationAccountNumber(@PathVariable Long destinationAccountNumber) { //TODO: validate
        return transactionRepository.findAllByDestinationAccountNumber(destinationAccountNumber);
    }

    @PostMapping("/transactions")
    public Transaction performTransaction(@RequestBody Transaction transaction) { //TODO: validate
        // TODO: perform the transaction
        // TODO: display updated balances
        return transactionRepository.save(transaction);
    }
}
