package com.nagarro.nagarroproject.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.nagarroproject.Entity.Account;
import com.nagarro.nagarroproject.Entity.Statement;
import com.nagarro.nagarroproject.Repositories.AccountRepository;
import com.nagarro.nagarroproject.Repositories.StatementRepository;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
// ... imports ...
import java.time.ZoneId;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final StatementRepository statementRepository;
    

    // ... constructor ...
    @Autowired
    public AccountService(AccountRepository accountRepository, StatementRepository statementRepository) {
        this.accountRepository = accountRepository;
        this.statementRepository = statementRepository;
    }

    public List<Statement> getStatements(Long accountId, Date fromDate, Date toDate, Double fromAmount, Double toAmount) throws Exception {
        // Validation logic for parameters

        // Retrieve the Account entity
        Account account = accountRepository.findById(accountId).orElseThrow(/* Exception for invalid accountId */);

        // Retrieve statements based on criteria
        List<Statement> statements = statementRepository.findByAccountAndDateFieldBetweenAndAmountBetween(
                account, fromDate, toDate, fromAmount, toAmount);

        // Handle default search for three months back
        if (statements.isEmpty() && fromDate == null && toDate == null && fromAmount == null && toAmount == null) {
            Date threeMonthsBack = calculateThreeMonthsBackDate();
            statements = statementRepository.findByAccountAndDateFieldAfter(account, threeMonthsBack);
        }

        // Hash account numbers before returning
        for (Statement statement : statements) {
            statement.getAccount().setAccountNumber(hashAccountNumber(statement.getAccount().getAccountNumber()));
        }

        return statements;
    }

 
    public String hashAccountNumber(String accountNumber) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(accountNumber.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    public Date calculateThreeMonthsBackDate() {
        LocalDate today = LocalDate.now();
        LocalDate threeMonthsBack = today.minusMonths(3);
        return Date.from(threeMonthsBack.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
