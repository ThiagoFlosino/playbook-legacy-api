package com.jfincore.dto;

import com.jfincore.entity.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for transaction responses.
 * This DTO is used to return transaction information to clients
 * without exposing the internal JPA entity structure.
 * 
 * @author J-FinCore Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDTO {
    
    /**
     * Unique identifier of the transaction
     */
    private UUID id;
    
    /**
     * Account identifier associated with the transaction
     */
    private String accountId;
    
    /**
     * Transaction amount
     */
    private BigDecimal amount;
    
    /**
     * Current status of the transaction
     */
    private TransactionStatus status;
    
    /**
     * Timestamp when the transaction was created
     */
    private LocalDateTime timestamp;
}