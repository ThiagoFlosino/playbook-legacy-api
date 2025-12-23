package com.jfincore.mapper;

import com.jfincore.dto.TransactionRequestDTO;
import com.jfincore.dto.TransactionResponseDTO;
import com.jfincore.entity.Transaction;
import org.springframework.stereotype.Component;

/**
 * Mapper component responsible for converting between Transaction entities and DTOs.
 * This class follows the mapper pattern to ensure proper separation between
 * internal entity representation and external API contracts.
 * 
 * @author J-FinCore Team
 * @version 1.0
 */
@Component
public class TransactionMapper {
    
    /**
     * Converts a TransactionRequestDTO to a Transaction entity.
     * The entity will be created with PENDING status and the current timestamp
     * will be automatically set by JPA.
     * 
     * @param requestDTO the request DTO containing transaction data
     * @return a new Transaction entity
     * @throws IllegalArgumentException if requestDTO is null
     */
    public Transaction toEntity(TransactionRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("TransactionRequestDTO cannot be null");
        }
        
        return new Transaction(
            requestDTO.getAccountId(),
            requestDTO.getAmount()
        );
    }
    
    /**
     * Converts a Transaction entity to a TransactionResponseDTO.
     * 
     * @param entity the transaction entity
     * @return a TransactionResponseDTO containing all transaction data
     * @throws IllegalArgumentException if entity is null
     */
    public TransactionResponseDTO toResponseDTO(Transaction entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Transaction entity cannot be null");
        }
        
        return new TransactionResponseDTO(
            entity.getId(),
            entity.getAccountId(),
            entity.getAmount(),
            entity.getStatus(),
            entity.getTimestamp()
        );
    }
}