package com.aquariux.barry.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WalletNotFoundException extends Exception {

    private final long walletId;

    @Override
    public String toString() {
        return "Wallet " + walletId + " not found";
    }
}
