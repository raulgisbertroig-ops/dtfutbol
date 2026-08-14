package com.systemicr2.dtfutbol.repository;

import com.systemicr2.dtfutbol.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
}
