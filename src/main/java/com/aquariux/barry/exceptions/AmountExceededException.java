package com.aquariux.barry.exceptions;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AmountExceededException extends Exception {

    private final BigDecimal amount;
    private final BigDecimal limit;
    private final String type;

    @Override
    public String toString() {
        return "Amount " + amount + " is over the " + type + " of " + limit;
    }
}
