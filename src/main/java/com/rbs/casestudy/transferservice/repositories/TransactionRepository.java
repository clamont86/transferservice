package com.rbs.casestudy.transferservice.repositories;

import com.rbs.casestudy.transferservice.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllBySourceAccountNumber(Long sourceAccountNumber);
    List<Transaction> findAllByDestinationAccountNumber(Long destinationAccountNumber);
}
