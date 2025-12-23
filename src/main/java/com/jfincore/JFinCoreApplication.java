package com.jfincore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for J-FinCore.
 * This is the entry point for the Spring Boot payment processing application.
 * 
 * The application is designed as a monolithic enterprise system for processing
 * financial transactions with proper layered architecture, error handling,
 * and business rule validation.
 * 
 * @author J-FinCore Team
 * @version 1.0
 */
@SpringBootApplication
public class JFinCoreApplication {
    
    /**
     * Main method that starts the J-FinCore application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(JFinCoreApplication.class, args);
    }
}