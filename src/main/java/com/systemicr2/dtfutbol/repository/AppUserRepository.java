package com.systemicr2.dtfutbol.repository;

import com.systemicr2.dtfutbol.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    // Metodo personalizado fundamental para Spring Security.
    // Usamos Optional porque puede que el usuario que intente entrar no exista.
    Optional<AppUser> findByUsername(String username);

}


