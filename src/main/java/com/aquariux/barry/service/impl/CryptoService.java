package com.aquariux.barry.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aquariux.barry.exceptions.AmountExceededException;
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
        Wallet wallet = getWallet(walletId);
        Map<String, BigDecimal> balances = new HashMap<>();

        transactionsRepository.findAllByWallet(wallet).forEach(transaction -> {
            BigDecimal balance = balances.get(transaction.getCurrency());

            if (transaction.getEntry().equals(Constants.Entry.CR)) {
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
        return transactionsRepository.findAllByWallet(getWallet(walletId)).toList();
    }

    @Override
    public List<Transaction> doCurrencyConversion(long walletId, String fromCurrency, String toCurrency, BigDecimal amount)
        throws AmountExceededException, CurrencyNotFoundException, SymbolNotFoundException, WalletNotFoundException {
        for (String currency : new String[]{fromCurrency, toCurrency}) {
            if (!Constants.Currency.getCurrencies().contains(currency)) {
                throw new CurrencyNotFoundException(currency); 
            }
        }

        for (String symbol : Constants.Symbol.getSymbols()) {
            if (symbol.contains(fromCurrency) && symbol.contains(toCurrency)) {
                Prices prices = getPrices(symbol);
                Wallet wallet = getWallet(walletId);
                Map<String, BigDecimal> balances = getBalances(walletId);
                List<Transaction> transactions = new ArrayList<>();

                if (Constants.Currency.USDT.equals(fromCurrency)) {
                    BigDecimal limit = prices.getAsk().multiply(prices.getAskQty());

                    if (balances.get(fromCurrency) == null || amount.compareTo(balances.get(fromCurrency)) > 0) {
                        throw new AmountExceededException(amount, balances.get(fromCurrency), "wallet balance");
                    }

                    if (amount.compareTo(limit) > 0) {
                        throw new AmountExceededException(amount, limit, "ask limit");
                    }

                    transactions.add(Transaction.builder()
                        .wallet(wallet)
                        .currency(fromCurrency)
                        .entry(Constants.Entry.DR)
                        .amount(amount)
                        .memo("Debiting transaction for buying " + toCurrency)
                    .build());
                    transactions.add(Transaction.builder()
                        .wallet(wallet)
                        .currency(toCurrency)
                        .entry(Constants.Entry.CR)
                        .amount(amount.divide(prices.getAsk(), 8, RoundingMode.HALF_EVEN))
                        .memo("Crediting transaction for buying " + toCurrency)
                    .build());
                } else {
                    BigDecimal limit = prices.getBidQty();
                    BigDecimal toAmount = amount.divide(prices.getBid(), 8, RoundingMode.HALF_EVEN);

                    if (balances.get(fromCurrency) == null || toAmount.compareTo(balances.get(fromCurrency)) > 0) {
                        throw new AmountExceededException(toAmount, balances.get(fromCurrency), "wallet balance");
                    }

                    if (toAmount.compareTo(limit) > 0) {
                        throw new AmountExceededException(amount, limit, "bid limit");
                    }

                    transactions.add(Transaction.builder()
                        .wallet(wallet)
                        .currency(fromCurrency)
                        .entry(Constants.Entry.DR)
                        .amount(toAmount)
                        .memo("Debiting transaction for selling " + toCurrency)
                    .build());
                    transactions.add(Transaction.builder()
                        .wallet(wallet)
                        .currency(toCurrency)
                        .entry(Constants.Entry.CR)
                        .amount(amount)
                        .memo("Crediting transaction for selling " + toCurrency)
                    .build());
                }

                return transactionsRepository.saveAll(transactions);
            }
        }

        throw new SymbolNotFoundException(fromCurrency, toCurrency);
    }

    private Wallet getWallet(long walletId) throws WalletNotFoundException {
        Wallet wallet = walletRepository.findById(walletId).orElse(null);
        if (wallet == null) {
            throw new WalletNotFoundException(walletId);
        }

        return wallet;
    }

}
