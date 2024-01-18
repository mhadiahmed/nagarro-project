package com.nagarro.nagarroproject.service;

import org.springframework.stereotype.Service;

import com.nagarro.nagarroproject.Entity.Statement;
import com.nagarro.nagarroproject.Repositories.AccountRepository;
import com.nagarro.nagarroproject.Repositories.StatementRepository;
import java.security.MessageDigest;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

// ... imports ...

@Service
public class StatementService {

    private final StatementRepository statementRepository;

    private final AccountRepository accountRepository;

    public StatementService(StatementRepository statementRepository, AccountRepository accountRepository) {
        this.statementRepository = statementRepository;
        this.accountRepository = accountRepository;
    }

    public List<Statement> getStatements(Long accountId, Date fromDate, Date toDate, Double fromAmount, Double toAmount) throws Exception {
        // Validation logic for parameters

        // Retrieve statements based on criteria
        List<Statement> statements = statementRepository.findByAccountAndDateFieldBetweenAndAmountBetween(
                accountId, fromDate, toDate, fromAmount, toAmount);

        // Hash account numbers before returning
        for (Statement statement : statements) {
            statement.getAccount().setAccountNumber(hashAccountNumber(statement.getAccount().getAccountNumber()));
        }

        return statements;
    }

    public List<Statement> getStatementsByAccountId(Long accountId) throws Exception {
        // Retrieve statements based on account id
        List<Statement> statements = statementRepository.findByAccount(accountRepository.findById(accountId).orElseThrow());

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
}

