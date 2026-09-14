package com.systemicr2.dtfutbol.repository;

import com.systemicr2.dtfutbol.model.Callup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallupRepository extends JpaRepository<Callup, Long>{
}
