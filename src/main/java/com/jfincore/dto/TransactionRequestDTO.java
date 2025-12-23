package com.jfincore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object for transaction creation requests.
 * This DTO is used to receive transaction data from clients and contains
 * validation annotations to ensure data integrity.
 * 
 * @author J-FinCore Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO {
    
    /**
     * Account identifier for the transaction.
     * Must not be null or blank.
     */
    @NotBlank(message = "Account ID is required and cannot be blank")
    private String accountId;
    
    /**
     * Transaction amount.
     * Must be a positive value and not null.
     */
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
}