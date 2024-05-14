package com.example.EjercicioRelacionEntidad.repository;

import com.example.EjercicioRelacionEntidad.model.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuestoRepository extends JpaRepository<Puesto,Integer> {
}
