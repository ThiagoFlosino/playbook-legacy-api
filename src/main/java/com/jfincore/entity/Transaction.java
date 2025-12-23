package com.jfincore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * JPA Entity representing a financial transaction in the J-FinCore system.
 * This entity encapsulates all the necessary information for processing payments.
 * 
 * @author J-FinCore Team
 * @version 1.0
 */
@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    
    /**
     * Unique identifier for the transaction using UUID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    
    /**
     * Account identifier associated with this transaction
     */
    @Column(name = "account_id", nullable = false, length = 50)
    private String accountId;
    
    /**
     * Transaction amount with precision for financial calculations
     */
    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
    
    /**
     * Current status of the transaction
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private TransactionStatus status;
    
    /**
     * Timestamp when the transaction was created (automatically set)
     */
    @CreationTimestamp
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;
    
    /**
     * Constructor for creating a new transaction with basic information
     * 
     * @param accountId the account identifier
     * @param amount the transaction amount
     */
    public Transaction(String accountId, BigDecimal amount) {
        this.accountId = accountId;
        this.amount = amount;
        this.status = TransactionStatus.PENDING;
    }
}