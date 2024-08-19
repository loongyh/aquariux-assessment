package com.aquariux.barry.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CryptoAdvice {

    @ExceptionHandler(AmountExceededException.class)
    public final ResponseEntity<String> handleAmountExceededException(AmountExceededException exception) {
        log.warn(exception.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.toString());
    }

    @ExceptionHandler(CurrencyNotFoundException.class)
    public final ResponseEntity<String> handleCurrencyNotFoundException(CurrencyNotFoundException exception) {
        log.warn(exception.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.toString());
    }

    @ExceptionHandler(SymbolNotFoundException.class)
    public final ResponseEntity<String> handleSymbolNotFoundException(SymbolNotFoundException exception) {
        log.warn(exception.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.toString());
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public final ResponseEntity<String> handleWalletNotFoundException(WalletNotFoundException exception) {
        log.warn(exception.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.toString());
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<String> handleRuntimeException(RuntimeException exception) {
        log.error("An unexpected error occurred: {}", exception.getMessage());

        String error = "An unexpected error occurred";
        if (exception.getMessage().equals("Not implemented")) {
            error = "Please implement this endpoint";
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
