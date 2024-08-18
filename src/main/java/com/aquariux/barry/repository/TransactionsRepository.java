package com.aquariux.barry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import com.aquariux.barry.model.Transaction;
import com.aquariux.barry.model.Wallet;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {

    public Streamable<Transaction> findAllByWallet(Wallet wallet);
}
