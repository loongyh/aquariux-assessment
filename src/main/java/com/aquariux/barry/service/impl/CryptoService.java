package com.aquariux.barry.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aquariux.barry.exceptions.CurrencyNotFoundException;
import com.aquariux.barry.exceptions.SymbolNotFoundException;
import com.aquariux.barry.exceptions.WalletNotFoundException;
import com.aquariux.barry.model.Prices;
import com.aquariux.barry.model.Transaction;
import com.aquariux.barry.model.Wallet;
import com.aquariux.barry.repository.PricesRepository;
import com.aquariux.barry.repository.TransactionsRepository;
import com.aquariux.barry.repository.WalletRepository;
import com.aquariux.barry.service.ICryptoService;
import com.aquariux.barry.utils.Constants;

@Service
@RequiredArgsConstructor
@Slf4j
public class CryptoService implements ICryptoService {
    
    @Autowired
    private PricesRepository pricesRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public List<Prices> getAllPrices() {
        return pricesRepository.findDistinctSymbols().map(pricesRepository::findFirstBySymbolOrderByIdDesc).toList();
    }

    @Override
    public Prices getPrices(String symbol) throws SymbolNotFoundException {
        Prices prices = pricesRepository.findFirstBySymbolOrderByIdDesc(symbol);
        if (prices == null) {
            throw new SymbolNotFoundException(symbol);
        }

        return prices;
    }

    @Override
    public Map<String, BigDecimal> getBalances(long walletId) throws WalletNotFoundException {
        Wallet wallet = walletRepository.findById(walletId).orElse(null);
        if (wallet == null) {
            throw new WalletNotFoundException(walletId);
        }

        Map<String, BigDecimal> balances = new HashMap<>();

        transactionsRepository.findAllByWallet(wallet).forEach(transaction -> {
            BigDecimal balance = balances.get(transaction.getCurrency());

            if (transaction.getEntry().equals(Constants.Currency.CR)) {
                if (balance == null) {
                    balance = transaction.getAmount();
                } else {
                    balance = balance.add(transaction.getAmount());
                }
            } else {
                if (balance == null) {
                    balance = transaction.getAmount().negate();
                } else {
                    balance = balance.subtract(transaction.getAmount());
                }
            }

            balances.put(transaction.getCurrency(), balance);
        });

        return balances;
    }

    @Override
    public List<Transaction> getTransactions(long walletId) throws WalletNotFoundException {
        Wallet wallet = walletRepository.findById(walletId).orElse(null);
        if (wallet == null) {
            throw new WalletNotFoundException(walletId);
        }

        return transactionsRepository.findAllByWallet(wallet).toList();
    }

    @Override
    public Transaction doTrade(long walletId, String fromCurrency, String toCurrency, BigDecimal amount, String memo) throws WalletNotFoundException, CurrencyNotFoundException {
        return null;
    }

}
