package com.aquariux.barry.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public final class Constants {

    public static final class Currency {
        public static final String BTC = "BTC";
        public static final String ETH = "ETH";
        public static final String USDT = "USDT";

        public static final List<String> getCurrencies() {
            return Arrays.stream(Currency.class.getDeclaredFields()).map(Field::getName).toList();
        }
    }
    
    public static final class Entry {
        public static final String CR = "CR";
        public static final String DR = "DR";
    }

    public static final class Source {
        public static final String BINANCE = "Binance";
        public static final String BINANCE_URL = "https://api.binance.com/api/v3/ticker/bookTicker";
        public static final String HUOBI = "Huobi";
        public static final String HUOBI_URL = "https://api.huobi.pro/market/tickers";
    }

    public static final class Symbol {
        public static final String ETHUSDT = "ETHUSDT";
        public static final String BTCUSDT = "BTCUSDT";

        public static final List<String> getSymbols() {
            return Arrays.stream(Symbol.class.getDeclaredFields()).map(Field::getName).toList();
        }
    }

}
