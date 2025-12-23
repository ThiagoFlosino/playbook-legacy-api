package com.jfincore.controller;

import com.jfincore.dto.TransactionRequestDTO;
import com.jfincore.dto.TransactionResponseDTO;
import com.jfincore.entity.TransactionStatus;
import com.jfincore.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for transaction management operations.
 * Provides endpoints for creating, retrieving, and querying transactions
 * in the J-FinCore payment processing system.
 * 
 * @author J-FinCore Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    
    private final TransactionService transactionService;
    
    /**
     * Creates and processes a new transaction.
     * 
     * @param requestDTO the transaction request containing account ID and amount
     * @return ResponseEntity containing the processed transaction details
     */
    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @Valid @RequestBody TransactionRequestDTO requestDTO) {
        
        log.info("Received transaction request for account: {}, amount: {}", 
                requestDTO.getAccountId(), requestDTO.getAmount());
        
        TransactionResponseDTO responseDTO = transactionService.processTransaction(requestDTO);
        
        log.info("Transaction created successfully with ID: {}", responseDTO.getId());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    
    /**
     * Retrieves a specific transaction by its ID.
     * 
     * @param transactionId the unique transaction identifier
     * @return ResponseEntity containing the transaction details
     */
    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponseDTO> getTransaction(
            @PathVariable UUID transactionId) {
        
        log.info("Retrieving transaction with ID: {}", transactionId);
        
        TransactionResponseDTO responseDTO = transactionService.getTransactionById(transactionId);
        
        return ResponseEntity.ok(responseDTO);
    }
    
    /**
     * Retrieves all transactions for a specific account.
     * 
     * @param accountId the account identifier
     * @return ResponseEntity containing a list of transactions for the account
     */
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByAccount(
            @PathVariable String accountId) {
        
        log.info("Retrieving transactions for account: {}", accountId);
        
        List<TransactionResponseDTO> transactions = transactionService.getTransactionsByAccountId(accountId);
        
        log.info("Found {} transactions for account: {}", transactions.size(), accountId);
        
        return ResponseEntity.ok(transactions);
    }
    
    /**
     * Retrieves all transactions with a specific status.
     * 
     * @param status the transaction status to filter by
     * @return ResponseEntity containing a list of transactions with the specified status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByStatus(
            @PathVariable TransactionStatus status) {
        
        log.info("Retrieving transactions with status: {}", status);
        
        List<TransactionResponseDTO> transactions = transactionService.getTransactionsByStatus(status);
        
        log.info("Found {} transactions with status: {}", transactions.size(), status);
        
        return ResponseEntity.ok(transactions);
    }
    
    /**
     * Retrieves all transactions in the system.
     * This endpoint should be used with caution in production environments
     * due to potential performance implications.
     * 
     * @return ResponseEntity containing a list of all transactions
     */
    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions() {
        
        log.info("Retrieving all transactions");
        
        List<TransactionResponseDTO> transactions = transactionService.getAllTransactions();
        
        log.info("Found {} total transactions", transactions.size());
        
        return ResponseEntity.ok(transactions);
    }
}