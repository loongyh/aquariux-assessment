package com.aquariux.barry.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class SymbolNotFoundException extends Exception {

    private final String currency1;
    private String currency2;

    @Override
    public String toString() {
        if (currency2 == null) return "Symbol not found for " + currency1;

        return "Symbol not found for " + currency1 + " and " + currency2;
    }
}
