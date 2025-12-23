package com.jfincore.repository;

import com.jfincore.entity.Transaction;
import com.jfincore.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Transaction entity.
 * Provides data access methods for transaction persistence operations.
 * Extends JpaRepository to inherit standard CRUD operations.
 * 
 * @author J-FinCore Team
 * @version 1.0
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    
    /**
     * Finds all transactions for a specific account.
     * 
     * @param accountId the account identifier
     * @return list of transactions for the account
     */
    List<Transaction> findByAccountIdOrderByTimestampDesc(String accountId);
    
    /**
     * Finds all transactions with a specific status.
     * 
     * @param status the transaction status
     * @return list of transactions with the specified status
     */
    List<Transaction> findByStatusOrderByTimestampDesc(TransactionStatus status);
    
    /**
     * Finds transactions by account ID and status.
     * 
     * @param accountId the account identifier
     * @param status the transaction status
     * @return list of transactions matching the criteria
     */
    List<Transaction> findByAccountIdAndStatus(String accountId, TransactionStatus status);
    
    /**
     * Finds transactions created within a date range.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of transactions within the date range
     */
    List<Transaction> findByTimestampBetweenOrderByTimestampDesc(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Calculates the total amount for transactions of a specific account and status.
     * 
     * @param accountId the account identifier
     * @param status the transaction status
     * @return the total amount or zero if no transactions found
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.accountId = :accountId AND t.status = :status")
    BigDecimal calculateTotalAmountByAccountIdAndStatus(@Param("accountId") String accountId, @Param("status") TransactionStatus status);
    
    /**
     * Checks if an account exists in the system.
     * 
     * @param accountId the account identifier
     * @return true if the account has at least one transaction
     */
    boolean existsByAccountId(String accountId);
}