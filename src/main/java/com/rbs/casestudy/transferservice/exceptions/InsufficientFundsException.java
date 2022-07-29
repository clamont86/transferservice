package com.rbs.casestudy.transferservice.exceptions;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(Long accountNumber, BigDecimal amount, BigDecimal balance) {
        super("Insufficient Funds in account number: " + accountNumber + ". Current balance: £" + balance.toString() + ", Requested Transfer: £" + amount.toString());
    }
}
