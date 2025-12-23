package com.jfincore.service;

import com.jfincore.dto.TransactionRequestDTO;
import com.jfincore.dto.TransactionResponseDTO;
import com.jfincore.entity.TransactionStatus;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for transaction processing operations.
 * Defines the contract for all transaction-related business operations
 * in the J-FinCore system.
 * 
 * @author J-FinCore Team
 * @version 1.0
 */
public interface TransactionService {
    
    /**
     * Processes a new transaction request.
     * This method handles the complete transaction workflow including
     * validation, fraud checking, and status determination.
     * 
     * @param requestDTO the transaction request containing account and amount information
     * @return the processed transaction response
     * @throws com.jfincore.exception.CustomBusinessException if business rules are violated
     */
    TransactionResponseDTO processTransaction(TransactionRequestDTO requestDTO);
    
    /**
     * Retrieves a transaction by its unique identifier.
     * 
     * @param transactionId the unique transaction identifier
     * @return the transaction response DTO
     * @throws com.jfincore.exception.CustomBusinessException if transaction is not found
     */
    TransactionResponseDTO getTransactionById(UUID transactionId);
    
    /**
     * Retrieves all transactions for a specific account.
     * Results are ordered by timestamp in descending order (newest first).
     * 
     * @param accountId the account identifier
     * @return list of transactions for the account
     */
    List<TransactionResponseDTO> getTransactionsByAccountId(String accountId);
    
    /**
     * Retrieves all transactions with a specific status.
     * Results are ordered by timestamp in descending order (newest first).
     * 
     * @param status the transaction status to filter by
     * @return list of transactions with the specified status
     */
    List<TransactionResponseDTO> getTransactionsByStatus(TransactionStatus status);
    
    /**
     * Retrieves all transactions in the system.
     * Results are ordered by timestamp in descending order (newest first).
     * 
     * @return list of all transactions
     */
    List<TransactionResponseDTO> getAllTransactions();
}