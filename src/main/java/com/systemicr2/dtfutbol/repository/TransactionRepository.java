package com.systemicr2.dtfutbol.repository;

import com.systemicr2.dtfutbol.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long>{
}
