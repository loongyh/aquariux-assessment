package com.aquariux.barry.model;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreationRequest {

    @NotNull(message = "Wallet id is required")
    private long walletId;

    @NotNull(message = "From currency is required")
    private String fromCurrency;

    @NotNull(message = "To currency is required")
    private String toCurrency;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

}
