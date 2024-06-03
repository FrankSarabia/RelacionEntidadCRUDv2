package com.example.EjercicioRelacionEntidad.security.repository;

import com.example.EjercicioRelacionEntidad.security.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByUsername(String username);
}
