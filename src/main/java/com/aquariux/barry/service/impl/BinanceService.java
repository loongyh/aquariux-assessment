package com.aquariux.barry.service.impl;

import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aquariux.barry.model.Prices;
import com.aquariux.barry.model.external.Binance;
import com.aquariux.barry.service.IFetchPricesService;
import com.aquariux.barry.utils.Constants.Source;

@Service
@RequiredArgsConstructor
@Slf4j
public class BinanceService implements IFetchPricesService {

    @Override
    public List<Prices> fetchPrices(List<String> symbols) {
        log.info("Fetching prices for {} from {}", symbols, Source.BINANCE);
        RestTemplate restTemplate = new RestTemplate();

        return Arrays.stream(restTemplate.getForObject(Source.BINANCE_URL, Binance[].class))
            .filter(data -> symbols.stream().anyMatch(data.symbol()::equalsIgnoreCase))
            .map(data -> Prices.builder()
                .symbol(data.symbol().toUpperCase())
                .bid(data.bidPrice())
                .bidQty(data.bidQty())
                .bidSource(Source.BINANCE)
                .ask(data.askPrice())
                .askQty(data.askQty())
                .askSource(Source.BINANCE)
            .build()).toList();
    }
}
