package com.jfincore.service;

import com.jfincore.dto.TransactionRequestDTO;
import com.jfincore.dto.TransactionResponseDTO;
import com.jfincore.entity.Transaction;
import com.jfincore.entity.TransactionStatus;
import com.jfincore.exception.CustomBusinessException;
import com.jfincore.mapper.TransactionMapper;
import com.jfincore.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of TransactionService interface.
 * Handles all business logic related to transaction processing including
 * fraud detection simulation and status management.
 * 
 * @author J-FinCore Team
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TransactionServiceImpl implements TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    
    // Business rule constants
    private static final BigDecimal MAX_TRANSACTION_AMOUNT = new BigDecimal("10000.00");
    private static final BigDecimal SUSPICIOUS_AMOUNT_THRESHOLD = new BigDecimal("5000.00");
    
    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionResponseDTO processTransaction(TransactionRequestDTO requestDTO) {
        log.info("Starting transaction processing for account: {}, amount: {}", 
                requestDTO.getAccountId(), requestDTO.getAmount());
        
        // Validate business rules
        validateTransactionRules(requestDTO);
        
        // Create and persist transaction entity
        Transaction transaction = transactionMapper.toEntity(requestDTO);
        transaction = transactionRepository.save(transaction);
        
        log.info("Transaction created with ID: {}", transaction.getId());
        
        // Simulate fraud detection (the intentional bottleneck)
        boolean fraudCheckPassed = simulateFraudCheck(transaction);
        
        // Update transaction status based on fraud check result
        if (fraudCheckPassed) {
            transaction.setStatus(TransactionStatus.APPROVED);
            log.info("Transaction {} approved after fraud check", transaction.getId());
        } else {
            transaction.setStatus(TransactionStatus.REJECTED);
            log.warn("Transaction {} rejected by fraud check", transaction.getId());
        }
        
        // Save final transaction state
        transaction = transactionRepository.save(transaction);
        
        log.info("Transaction processing completed for ID: {}, final status: {}", 
                transaction.getId(), transaction.getStatus());
        
        return transactionMapper.toResponseDTO(transaction);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public TransactionResponseDTO getTransactionById(UUID transactionId) {
        log.info("Retrieving transaction by ID: {}", transactionId);
        
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new CustomBusinessException(
                        "Transaction not found with ID: " + transactionId, 
                        "TRANSACTION_NOT_FOUND"));
        
        return transactionMapper.toResponseDTO(transaction);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> getTransactionsByAccountId(String accountId) {
        log.info("Retrieving transactions for account: {}", accountId);
        
        List<Transaction> transactions = transactionRepository.findByAccountIdOrderByTimestampDesc(accountId);
        
        return transactions.stream()
                .map(transactionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> getTransactionsByStatus(TransactionStatus status) {
        log.info("Retrieving transactions by status: {}", status);
        
        List<Transaction> transactions = transactionRepository.findByStatusOrderByTimestampDesc(status);
        
        return transactions.stream()
                .map(transactionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> getAllTransactions() {
        log.info("Retrieving all transactions");
        
        List<Transaction> transactions = transactionRepository.findAll();
        
        return transactions.stream()
                .map(transactionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Validates business rules for transaction processing.
     * 
     * @param requestDTO the transaction request to validate
     * @throws CustomBusinessException if validation fails
     */
    private void validateTransactionRules(TransactionRequestDTO requestDTO) {
        // Check maximum transaction amount
        if (requestDTO.getAmount().compareTo(MAX_TRANSACTION_AMOUNT) > 0) {
            throw new CustomBusinessException(
                    "Transaction amount exceeds maximum limit of " + MAX_TRANSACTION_AMOUNT,
                    "AMOUNT_EXCEEDS_LIMIT");
        }
        
        // Check for negative or zero amounts (additional validation beyond Bean Validation)
        if (requestDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CustomBusinessException(
                    "Transaction amount must be positive",
                    "INVALID_AMOUNT");
        }
    }
    
    /**
     * Simulates a synchronous call to a legacy fraud detection system.
     * This method intentionally includes a Thread.sleep to simulate the latency
     * of calling an external fraud detection service.
     * 
     * In a real-world scenario, this would be replaced with actual fraud detection logic
     * or a call to an external fraud detection API.
     * 
     * @param transaction the transaction to check for fraud
     * @return true if the transaction passes fraud checks, false otherwise
     */
    private boolean simulateFraudCheck(Transaction transaction) {
        log.info("Initiating fraud check for transaction: {}", transaction.getId());
        
        try {
            // Simulação de chamada síncrona a sistema legado de fraude
            // This simulates the latency of calling a legacy fraud detection system
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("Fraud check interrupted for transaction: {}", transaction.getId(), e);
            Thread.currentThread().interrupt();
            throw new CustomBusinessException(
                    "Fraud check process was interrupted",
                    "FRAUD_CHECK_INTERRUPTED", e);
        }
        
        // Simple fraud detection logic based on amount
        // Transactions above the suspicious threshold have a 30% chance of being flagged as fraud
        boolean isFraudulent = false;
        if (transaction.getAmount().compareTo(SUSPICIOUS_AMOUNT_THRESHOLD) > 0) {
            // Simulate some fraud detection logic (30% rejection rate for high amounts)
            isFraudulent = Math.random() < 0.3;
        }
        
        log.info("Fraud check completed for transaction: {}, result: {}", 
                transaction.getId(), !isFraudulent ? "PASSED" : "FAILED");
        
        return !isFraudulent;
    }
}