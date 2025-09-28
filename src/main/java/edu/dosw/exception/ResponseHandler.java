package edu.dosw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

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

    public static ResponseEntity generateErrorResponse(String message, HttpStatus status) {
        return generateResponse(message, status, null);
    }

    public static ResponseEntity<Object> generateErrorResponse(String message, HttpStatus status, String error) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", error);
        return generateResponse(message, status, errorDetails);
    }
}
