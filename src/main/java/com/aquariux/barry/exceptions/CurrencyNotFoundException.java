package com.aquariux.barry.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CurrencyNotFoundException extends Exception {

    private final String currency;

    @Override
    public String toString() {
        return "Currency " + currency + " not found";
    }
}
