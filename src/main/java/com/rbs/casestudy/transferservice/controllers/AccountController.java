package com.rbs.casestudy.transferservice.controllers;

import com.rbs.casestudy.transferservice.models.Account;
import com.rbs.casestudy.transferservice.repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/accounts")
    public List<Account> listAllAccounts() {
        return accountRepository.findAll();
    }


    @GetMapping("/accounts/{accountNumber}")
    public Account findByAccountNumber(@PathVariable Long accountNumber) {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found")); //TODO: create unique exception
    }

    @PostMapping("/accounts")
    public Account addNewAccount(@RequestBody Account account) { //TODO: Validate
        try {
            return accountRepository.save(account);
        } catch (Exception e) {
            //TODO: handle exception
            logger.error("Error while creating new account", e); //TODO: remove log
            throw e;
        }
    }

    @PutMapping("/accounts/{accountNumber}")
    public Account updateBalance(@RequestBody Account account, @PathVariable Long accountNumber) { //TODO: valid
        Account retrievedAccount = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found")); //TODO: create unique exception
        retrievedAccount.setBalance(account.getBalance());
        try {
            return accountRepository.save(account);
        } catch (Exception e) {
            //TODO: handle exception
            logger.error("Error while creating new account", e);
            throw e;
        }
    }

    @DeleteMapping("/accounts/{accountNumber}")
    public Long deleteAccount(@PathVariable Long accountNumber) {
        try {
            accountRepository.deleteById(accountNumber);
            return accountNumber;
        } catch (Exception e) {
            logger.error("Error while deleting account " + accountNumber, e);
            throw e;
        }
    }
}
