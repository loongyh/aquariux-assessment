package com.aquariux.barry.model.external;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Binance(
    String symbol,
    BigDecimal bidPrice,
    BigDecimal bidQty,
    BigDecimal askPrice,
    BigDecimal askQty) { }
