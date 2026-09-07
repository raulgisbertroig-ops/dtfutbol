package com.systemicr2.dtfutbol.repository;

import com.systemicr2.dtfutbol.model.CategoryRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRuleRepository extends JpaRepository<CategoryRule, Long> {
}
