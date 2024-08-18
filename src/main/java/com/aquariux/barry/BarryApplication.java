package com.aquariux.barry;

import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.aquariux.barry.model.Prices;
import com.aquariux.barry.repository.PricesRepository;
import com.aquariux.barry.service.IFetchPricesService;
import com.aquariux.barry.utils.Constants.Symbol;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class BarryApplication {

    public static final String[] SYMBOLS = {Symbol.ETHUSDT, Symbol.BTCUSDT};

    @Autowired
    private PricesRepository pricesRepository;

    @Autowired
    private List<IFetchPricesService> fetchPricesServices;

    public static void main(String[] args) {
        SpringApplication.run(BarryApplication.class, args);
    }

    @Scheduled(fixedDelay = 10000)
    private void updatePricesTask() {
        List<Prices> allPrices = fetchPricesServices.stream()
            .map(fpSvc -> fpSvc.fetchPrices(SYMBOLS))
            .flatMap(List::stream).toList();
        
        pricesRepository.saveAll(Arrays.stream(SYMBOLS).map(symbol -> allPrices.stream()
            .filter(prices-> symbol.equalsIgnoreCase(prices.getSymbol()))
            .reduce(Prices.builder().symbol(symbol).build(), (bestPrices, prices) -> {
                if (prices.getAsk().compareTo(bestPrices.getAsk()) < 0) {
                    bestPrices.setAsk(prices.getAsk());
                    bestPrices.setAskQty(prices.getAskQty());
                    bestPrices.setAskSource(prices.getAskSource());
                }
                if (prices.getBid().compareTo(bestPrices.getBid()) > 0) {
                    bestPrices.setBid(prices.getBid());
                    bestPrices.setBidQty(prices.getBidQty());
                    bestPrices.setBidSource(prices.getBidSource());
                }
                return bestPrices;
        })).toList());
    }

}
