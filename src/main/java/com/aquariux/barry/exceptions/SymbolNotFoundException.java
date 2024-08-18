package com.aquariux.barry.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SymbolNotFoundException extends Exception {

    private final String symbol;

    @Override
    public String toString() {
        return "Symbol " + symbol + " not found";
    }
}
