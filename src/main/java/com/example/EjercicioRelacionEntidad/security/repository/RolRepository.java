package com.example.EjercicioRelacionEntidad.security.repository;

import com.example.EjercicioRelacionEntidad.security.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol,Long> {
    Optional<Rol> findByNombre(String name);
}
