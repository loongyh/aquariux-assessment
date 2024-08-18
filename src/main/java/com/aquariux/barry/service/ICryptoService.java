package com.aquariux.barry.service;

import java.util.List;

import com.aquariux.barry.model.Prices;

public interface ICryptoService {
    List<Prices> getAllPrices();
    Prices getPrices(String symbol);
}
