package com.aquariux.barry.service;

import java.util.List;

import com.aquariux.barry.model.Prices;

public interface IFetchPricesService {
    List<Prices> fetchPrices(String[] symbols);
}
