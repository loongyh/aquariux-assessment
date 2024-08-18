package com.aquariux.barry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import com.aquariux.barry.model.Prices;

@Repository
public interface PricesRepository extends JpaRepository<Prices, Long> {

    @Query("SELECT DISTINCT symbol FROM Prices")
    public Streamable<String> findDistinctSymbols();

    public Prices findFirstBySymbolOrderByIdDesc(String symbol);
}
