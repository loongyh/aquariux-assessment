package com.aquariux.barry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aquariux.barry.model.Transactions;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
}
