package com.aquariux.barry.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aquariux.barry.model.Prices;
import com.aquariux.barry.repository.PricesRepository;
import com.aquariux.barry.service.ICryptoService;

@Service
@RequiredArgsConstructor
@Slf4j
public class CryptoService implements ICryptoService {
    
    @Autowired
    private PricesRepository pricesRepository;

    @Override
    public List<Prices> getAllPrices() {
        log.info("Getting all prices");
        return pricesRepository.findDistinctSymbols().map(pricesRepository::findFirstBySymbolOrderByIdDesc).toList();
    }

    @Override
    public Prices getPrices(String symbol) {
        log.info("Getting prices for {}", symbol);
        return pricesRepository.findFirstBySymbolOrderByIdDesc(symbol);
    }

}
