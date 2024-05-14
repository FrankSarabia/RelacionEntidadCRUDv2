package com.example.EjercicioRelacionEntidad.service.Imp;

import com.example.EjercicioRelacionEntidad.exceptions.InternalServerErrorException;
import com.example.EjercicioRelacionEntidad.exceptions.NotFoundException;
import com.example.EjercicioRelacionEntidad.model.Persona;
import com.example.EjercicioRelacionEntidad.model.Puesto;
import com.example.EjercicioRelacionEntidad.repository.PersonaRepository;
import com.example.EjercicioRelacionEntidad.service.IServicios;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService implements IServicios<Persona> {
    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public List<Persona> listAll() {
        try {
            return personaRepository.findAll();
        } catch (Exception e) {
            throw new NotFoundException("No se encontraron personas");
        }
    }

    @Override
    public Persona findById(int id) {
        try {
            return personaRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new NotFoundException("No se encontraron personas con id: " + id);
        }
    }
    @Transactional
    @Override
    public void addNew(Persona newPersona) {
        try {
            personaRepository.save(newPersona);
        } catch (Exception e) {
            throw new InternalServerErrorException("No se pudo agregar a la persona nueva");
        }
    }
    @Transactional
    @Override
    public void remove(int id) {
        try {
            personaRepository.deleteById(id);
        } catch (Exception e) {
            throw new InternalServerErrorException("No se pudo eliminar a la persona con id: "+ id);
        }
    }
    @Transactional
    @Override
    public void modify(int id,Persona newPersona) {
        try {
            Optional<Persona> personaExistente = personaRepository.findById(id);
            if (personaExistente.isPresent()) {
                Persona persona = personaExistente.get();
                persona.setName(newPersona.getName());
                persona.setApellido(newPersona.getApellido());
                persona.setEdad(newPersona.getEdad());
                persona.setDireccion(newPersona.getDireccion());
                persona.setPuesto(newPersona.getPuesto());
                personaRepository.save(persona);
            }
        } catch (Exception e) {
            throw new InternalServerErrorException("No se pudo modificar a la persona con id: "+ id);
        }
    }
    public List<Persona> findPersonasByPuesto (int puesto_id){
        try {
            return personaRepository.findPersonasByPuesto(puesto_id);
        } catch (Exception e) {
            throw new NotFoundException("No se encontraron personas con id de puesto: " + puesto_id);
        }
    }
}
