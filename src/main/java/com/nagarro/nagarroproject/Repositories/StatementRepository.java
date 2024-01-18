package com.nagarro.nagarroproject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.nagarroproject.Entity.Account;
import com.nagarro.nagarroproject.Entity.Statement;

import java.util.Date;
import java.util.List;
// ... imports ...

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {


    List<Statement> findByAccountAndDateFieldAfter(Account account, Date date);
    List<Statement> findByAccount(Account account);

    List<Statement> findByAccountAndDateFieldBetweenAndAmountBetween(Account account, Date fromDate, Date toDate,
            Double fromAmount, Double toAmount);
    List<Statement> findByAccountAndDateFieldBetweenAndAmountBetween(Long accountId, Date fromDate, Date toDate,
            Double fromAmount, Double toAmount); 
}
