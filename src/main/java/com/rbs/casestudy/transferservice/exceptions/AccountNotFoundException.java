package com.rbs.casestudy.transferservice.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long accountNumber) {
        super("No account found with accountNumber: " + accountNumber);
    }

    public AccountNotFoundException(Long accountNumber, String field) {
        super(field + " account not found with accountNumber: " + accountNumber);
    }
}