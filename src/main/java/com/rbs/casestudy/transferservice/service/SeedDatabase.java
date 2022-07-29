package com.rbs.casestudy.transferservice.service;

import com.rbs.casestudy.transferservice.models.Account;
import com.rbs.casestudy.transferservice.repositories.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class SeedDatabase {

    @Bean
    public CommandLineRunner initialiseDatabase(AccountRepository accountRepository) {
        return args -> {
            accountRepository.save(new Account(11111111L, new BigDecimal("1000.00")));
            accountRepository.save(new Account(22222222L, new BigDecimal("123.45")));
            accountRepository.save(new Account(33333333L, new BigDecimal("-50.00")));
            accountRepository.save(new Account(44444444L, new BigDecimal("10000.00")));
            accountRepository.save(new Account(55555555L, new BigDecimal("-85.65")));
        };
    }
}
