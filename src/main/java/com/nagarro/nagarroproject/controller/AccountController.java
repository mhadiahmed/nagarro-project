package com.nagarro.nagarroproject.controller;

import com.nagarro.nagarroproject.Entity.Statement;
import com.nagarro.nagarroproject.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Simulating hardcoded user credentials
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String USER_USERNAME = "user";
    private static final String USER_PASSWORD = "user";

    // Simulating logged-in user (consider using session management)
    private String loggedInUsername;

    @GetMapping("/accounts/{accountId}/statements")
    public ResponseEntity<?> getStatements(
            @PathVariable Long accountId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
            @RequestParam(required = false) Double fromAmount,
            @RequestParam(required = false) Double toAmount) throws Exception {

        // Check if the user is logged in
        if (loggedInUsername == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        // Check if the user is allowed to perform the request
        if (!checkUserPermissions(loggedInUsername, fromDate, toDate, fromAmount, toAmount)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        try {
            // Perform the statements retrieval
            List<Statement> statements = accountService.getStatements(accountId, fromDate, toDate, fromAmount, toAmount);
            return ResponseEntity.ok().body(statements);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving statements");
        }
    }

    // Simulate user login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        if (loginUser(username, password)) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // Simulate user logout
    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        if (loggedInUsername != null) {
            loggedInUsername = null;
            return ResponseEntity.ok("Logout successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
    }

    // Simulate user login logic
    private boolean loginUser(String username, String password) {
        // Simulate hardcoded user credentials
        if ((ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) ||
            (USER_USERNAME.equals(username) && USER_PASSWORD.equals(password))) {
            loggedInUsername = username;
            return true;
        }
        return false;
    }

    // Check user permissions based on access control specifications
    private boolean checkUserPermissions(String username, Date fromDate, Date toDate, Double fromAmount, Double toAmount) {
        if (ADMIN_USERNAME.equals(username)) {
            // Admin can perform all requests
            return true;
        } else if (USER_USERNAME.equals(username)) {
            // User can only do a request without parameters
            return fromDate == null && toDate == null && fromAmount == null && toAmount == null;
        }
        return false;
    }
}
