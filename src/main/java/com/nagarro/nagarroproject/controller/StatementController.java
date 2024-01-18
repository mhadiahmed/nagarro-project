package com.nagarro.nagarroproject.controller;

import com.nagarro.nagarroproject.Entity.Statement;
import com.nagarro.nagarroproject.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class StatementController {

    @Autowired
    private StatementService statementService;

    // Simulating logged-in user (consider using session management)
    private String loggedInUsername;

    @GetMapping("/statements/{accountId}")
    public ResponseEntity<?> getStatementsByAccountId(@PathVariable Long accountId) throws Exception {
        // Check if the user is allowed to perform the request
        if (!checkUserPermissions(loggedInUsername, null, null, null, null)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        try {
            List<Statement> statements = statementService.getStatementsByAccountId(accountId);
            return ResponseEntity.ok().body(statements);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving statements");
        }
    }

    @GetMapping("/statements")
    public ResponseEntity<?> getStatements(
            @RequestParam(required = false) Long accountId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam(required = false) Double fromAmount,
            @RequestParam(required = false) Double toAmount) throws Exception {

        // Check if the user is allowed to perform the request
        if (!checkUserPermissions(loggedInUsername, fromDate, toDate, fromAmount, toAmount)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        try {
            List<Statement> statements = statementService.getStatements(accountId, fromDate, toDate, fromAmount, toAmount);
            return ResponseEntity.ok().body(statements);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving statements");
        }
    }

    // Check user permissions based on access control specifications
    private boolean checkUserPermissions(String username, Date fromDate, Date toDate, Double fromAmount, Double toAmount) {
        // Assuming both 'admin' and 'user' have the same permissions for statements
        return username != null;
    }
}
