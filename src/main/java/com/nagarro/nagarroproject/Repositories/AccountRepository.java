package com.nagarro.nagarroproject.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.nagarroproject.Entity.Account;
import org.springframework.stereotype.Repository;

public interface AccountRepository extends JpaRepository<Account, Long> {

   Optional<Account> findByAccountNumber(String accountNumber);
}
