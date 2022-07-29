package com.rbs.casestudy.transferservice.repositories;

import com.rbs.casestudy.transferservice.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
