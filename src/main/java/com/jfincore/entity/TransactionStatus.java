package com.jfincore.entity;

/**
 * Enum representing the possible statuses of a financial transaction.
 * 
 * @author J-FinCore Team
 * @version 1.0
 */
public enum TransactionStatus {
    /**
     * Transaction is pending and waiting for processing
     */
    PENDING,
    
    /**
     * Transaction has been approved and processed successfully
     */
    APPROVED,
    
    /**
     * Transaction has been rejected due to business rules or fraud detection
     */
    REJECTED
}