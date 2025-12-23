package com.jfincore.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Standardized error response structure following RFC 7807 Problem Details.
 * This class provides a consistent format for all error responses
 * returned by the J-FinCore API.
 * 
 * @author J-FinCore Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    /**
     * Timestamp when the error occurred
     */
    private LocalDateTime timestamp;
    
    /**
     * HTTP status code
     */
    private Integer status;
    
    /**
     * Brief error title
     */
    private String error;
    
    /**
     * Detailed error message
     */
    private String message;
    
    /**
     * Application-specific error code
     */
    private String errorCode;
    
    /**
     * Request path where the error occurred
     */
    private String path;
    
    /**
     * Field validation errors (for validation exceptions)
     */
    private Map<String, String> validationErrors;
}