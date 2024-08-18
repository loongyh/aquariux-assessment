package com.aquariux.barry.model.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Huobi(HuobiData[] data) { }
