package com.aquariux.barry.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aquariux.barry.model.Prices;
import com.aquariux.barry.service.ICryptoService;

@Tag(name = "Crypto", description = "Crypto trading functions")
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
        return ResponseEntity.ok(cryptoService.getAllPrices());
    }

    @Operation(summary = "Get latest best aggregated prices for symbol")
    @GetMapping("/prices/{symbol}")
    public ResponseEntity<Prices> getPrices(@PathVariable String symbol) {
        return ResponseEntity.ok(cryptoService.getPrices(symbol));
    }



}
