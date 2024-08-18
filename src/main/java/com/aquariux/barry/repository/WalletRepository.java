package com.aquariux.barry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aquariux.barry.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
