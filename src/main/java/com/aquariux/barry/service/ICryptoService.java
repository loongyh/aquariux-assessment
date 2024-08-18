package com.aquariux.barry.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.aquariux.barry.exceptions.CurrencyNotFoundException;
import com.aquariux.barry.exceptions.SymbolNotFoundException;
import com.aquariux.barry.exceptions.WalletNotFoundException;
import com.aquariux.barry.model.Prices;
import com.aquariux.barry.model.Transaction;

public interface ICryptoService {
    List<Prices> getAllPrices();
    Prices getPrices(String symbol) throws SymbolNotFoundException;
    Map<String, BigDecimal> getBalances(long walletId) throws WalletNotFoundException;
    List<Transaction> getTransactions(long walletId) throws WalletNotFoundException;
    Transaction doTrade(long walletId, String fromCurrency, String toCurrency, BigDecimal amount, String memo)
        throws WalletNotFoundException, CurrencyNotFoundException;
}
