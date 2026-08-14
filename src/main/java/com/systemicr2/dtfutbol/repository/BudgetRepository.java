package com.systemicr2.dtfutbol.repository;

import com.systemicr2.dtfutbol.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
