package com.jfincore.exception;

/**
 * Custom business exception for J-FinCore application.
 * This exception is thrown when business rules are violated or
 * when specific business logic errors occur during transaction processing.
 * 
 * @author J-FinCore Team
 * @version 1.0
 */
public class CustomBusinessException extends RuntimeException {
    
    private final String errorCode;
    
    /**
     * Constructs a new CustomBusinessException with the specified detail message.
     * 
     * @param message the detail message
     */
    public CustomBusinessException(String message) {
        super(message);
        this.errorCode = "BUSINESS_ERROR";
    }
    
    /**
     * Constructs a new CustomBusinessException with the specified detail message and error code.
     * 
     * @param message the detail message
     * @param errorCode the specific error code
     */
    public CustomBusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    /**
     * Constructs a new CustomBusinessException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause
     */
    public CustomBusinessException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "BUSINESS_ERROR";
    }
    
    /**
     * Constructs a new CustomBusinessException with the specified detail message, error code and cause.
     * 
     * @param message the detail message
     * @param errorCode the specific error code
     * @param cause the cause
     */
    public CustomBusinessException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    /**
     * Gets the error code associated with this exception.
     * 
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }
}