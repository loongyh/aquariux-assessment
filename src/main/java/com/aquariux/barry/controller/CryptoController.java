package com.aquariux.barry.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aquariux.barry.exceptions.SymbolNotFoundException;
import com.aquariux.barry.exceptions.WalletNotFoundException;
import com.aquariux.barry.model.Prices;
import com.aquariux.barry.model.Transaction;
import com.aquariux.barry.model.TransactionCreationRequest;
import com.aquariux.barry.service.ICryptoService;

@Tag(name = "Aquariux Technical Test", description = "Crypto trading functions")
@RequestMapping("/crypto")
@RequiredArgsConstructor
@RestController
@Slf4j
@CrossOrigin(origins = "*")
public class CryptoController {

    @Autowired
    ICryptoService cryptoService;
    
    @Operation(summary = "Get latest best aggregated prices for all symbols")
    @GetMapping("/prices")
    public ResponseEntity<List<Prices>> getAllPrices() {
        log.info("Getting all prices");
        return ResponseEntity.ok(cryptoService.getAllPrices());
    }

    @Operation(summary = "Get latest best aggregated prices for symbol")
    @GetMapping("/prices/{symbol}")
    public ResponseEntity<Prices> getPrices(@PathVariable String symbol) throws SymbolNotFoundException {
        log.info("Getting prices for {}", symbol);
        return ResponseEntity.ok(cryptoService.getPrices(symbol));
    }

    @Operation(summary = "Perform a trade based on the latest best aggregated price")
    @PostMapping("/trade")
    ResponseEntity<Transaction> doTrade(@RequestBody @Valid TransactionCreationRequest request) {
        log.info("Attempting to convert from {} to {} with amount {} for wallet {}",
            request.getFromCurrency(), request.getToCurrency(), request.getAmount(), request.getWalletId());
        return null;
    }

    @Operation(summary = "Get balances for wallet id")
    @GetMapping("/balances/{walletId}")
    public ResponseEntity<Map<String, BigDecimal>> getBalances(@PathVariable long walletId) throws WalletNotFoundException {
        log.info("Getting balances for wallet {}", walletId);
        return ResponseEntity.ok(cryptoService.getBalances(walletId));
    }

    @Operation(summary = "Get transactions for wallet id")
    @GetMapping("/transactions/{walletId}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable long walletId) throws WalletNotFoundException {
        log.info("Getting transactions for wallet {}", walletId);
        return ResponseEntity.ok(cryptoService.getTransactions(walletId));
    }

}
