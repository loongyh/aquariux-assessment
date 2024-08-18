package com.aquariux.barry.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aquariux.barry.model.Prices;
import com.aquariux.barry.model.external.Huobi;
import com.aquariux.barry.service.IFetchPricesService;
import com.aquariux.barry.utils.Constants.Source;

@Service
@RequiredArgsConstructor
@Slf4j
public class HuobiService implements IFetchPricesService {

    @Override
    public List<Prices> fetchPrices(String[] symbols) {
        log.info("Fetching prices for {} from {}", Arrays.toString(symbols), Source.HUOBI);
        RestTemplate restTemplate = new RestTemplate();

        return Arrays.stream(restTemplate.getForObject(Source.HUOBI_URL, Huobi.class).data())
            .filter(data -> Arrays.stream(symbols).anyMatch(data.symbol()::equalsIgnoreCase))
            .map(data -> Prices.builder()
                .symbol(data.symbol().toUpperCase())
                .bid(data.bid())
                .bidQty(data.bidSize())
                .bidSource(Source.HUOBI)
                .ask(data.ask().multiply(data.askSize()))
                .askQty(data.askSize())
                .askSource(Source.HUOBI)
            .build()).collect(Collectors.toList());
    }
}
