package com.example.EjercicioRelacionEntidad.repository;

import com.example.EjercicioRelacionEntidad.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    @Query("SELECT p FROM Persona p WHERE p.puesto.id = :puesto_id")
    List<Persona> findPersonasByPuesto(@Param("puesto_id") int puesto_id);
}
