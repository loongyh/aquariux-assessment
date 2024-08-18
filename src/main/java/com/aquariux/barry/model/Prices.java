package com.aquariux.barry.model;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prices")
public class Prices {

    @Id
    @SequenceGenerator(name = "prices_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prices_seq")
    private long id;

    private String symbol;

    @Column(precision = 30, scale = 8)
    @Builder.Default
    private BigDecimal bid = BigDecimal.ZERO;

    @Column(precision = 30, scale = 8)
    @Builder.Default
    private BigDecimal bidQty = BigDecimal.ZERO;

    private String bidSource;

    @Column(precision = 30, scale = 8)
    @Builder.Default
    private BigDecimal ask = BigDecimal.valueOf(Double.MAX_VALUE);

    @Column(precision = 30, scale = 8)
    @Builder.Default
    private BigDecimal askQty = BigDecimal.ONE;

    private String askSource;

    @Builder.Default
    private long timeCreated = Instant.now().getEpochSecond();
}
