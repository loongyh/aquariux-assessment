package com.aquariux.barry.model.external;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HuobiData(
    String symbol,
    BigDecimal bid,
    BigDecimal bidSize,
    BigDecimal ask,
    BigDecimal askSize) { }
