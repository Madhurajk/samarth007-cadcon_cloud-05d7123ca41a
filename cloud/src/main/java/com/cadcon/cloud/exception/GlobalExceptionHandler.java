package com.cadcon.cloud.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = { InvalidInputException.class })
    public ResponseEntity<ExceptionResponse> handleInvalidInputException(InvalidInputException ex) {
        logger.error("Invalid Input Exception: ", ex.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST, LocalDateTime.now(),
                ex.getMessage(), ex.getLocalizedMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, exceptionResponse.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex) {
        logger.error("Error thrown : {} ", shortenedStackTrace(ex, 10));
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST, LocalDateTime.now(),
                ex.getMessage(), ex.getLocalizedMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, exceptionResponse.getStatus());
    }

    /**
     * Private method print only first maxLines of stacktrace
     * 
     * @param e
     * @param maxLines
     * @return
     */
    private String shortenedStackTrace(Exception e, int maxLines) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        String[] lines = writer.toString().split("\n");
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int i = 0; i < Math.min(lines.length, maxLines); i++) {
            sb.append(lines[i]).append("\n");
        }
        return sb.toString();
    }
}
