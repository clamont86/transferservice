package com.rbs.casestudy.transferservice.controllers;

import com.rbs.casestudy.transferservice.exceptions.AccountNotFoundException;
import com.rbs.casestudy.transferservice.models.Account;
import com.rbs.casestudy.transferservice.repositories.AccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

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
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    @PostMapping("/accounts")
    public Account addNewAccount(@RequestBody Account account) { //TODO: Validate
        return accountRepository.save(account);
    }

    @PutMapping("/accounts/{accountNumber}")
    public Account updateBalance(@RequestBody Account account, @PathVariable Long accountNumber) { //TODO: valid
        Account retrievedAccount = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber)); //TODO: create unique exception
        retrievedAccount.setBalance(account.getBalance());
        try {
            return accountRepository.save(account);
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/accounts/{accountNumber}")
    public Long deleteAccount(@PathVariable Long accountNumber) {
        accountRepository.deleteById(accountNumber);
        return accountNumber;
    }
}
