package edu.dosw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for generating consistent API responses.
 * Provides methods to create standardized success and error responses.
 */
public class ResponseHandler {

    /**
     * Generates a standardized response with the given message, status, and optional data.
     *
     * @param message The response message
     * @param status The HTTP status code
     * @param responseObj The optional response data (can be null)
     * @return A ResponseEntity containing the formatted response
     */
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now());
        map.put("status", status.value());
        map.put("message", message);

        if (responseObj != null) {
            map.put("data", responseObj);
        }

        return new ResponseEntity<>(map, status);
    }

    /**
     * Generates a standardized error response with the given message and status.
     *
     * @param message The error message
     * @param status The HTTP status code
     * @return A ResponseEntity containing the error response
     */
    public static ResponseEntity<Object> generateErrorResponse(String message, HttpStatus status) {
        return generateResponse(message, status, null);
    }

    /**
     * Generates a standardized error response with a custom error object.
     *
     * @param message The error message
     * @param status The HTTP status code
     * @param error The detailed error information
     * @return A ResponseEntity containing the error response with details
     */
    public static ResponseEntity<Object> generateErrorResponse(String message, HttpStatus status, String error) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", error);
        return generateResponse(message, status, errorDetails);
    }
}
