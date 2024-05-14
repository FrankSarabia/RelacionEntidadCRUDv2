package com.example.EjercicioRelacionEntidad.controller.Imp;

import com.example.EjercicioRelacionEntidad.controller.IController;
import com.example.EjercicioRelacionEntidad.exceptions.InternalServerErrorException;
import com.example.EjercicioRelacionEntidad.exceptions.NotFoundException;
import com.example.EjercicioRelacionEntidad.model.Persona;
import com.example.EjercicioRelacionEntidad.service.Imp.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/persona")
public class PersonaController implements IController<Persona> {
    @Autowired
    private PersonaService personaService;
    @GetMapping("/listar")
    @Override
    public List<Persona> obtenerTodos() {
        try {
            if (personaService.listAll()==null){
                throw new NotFoundException("No se encontraron personas");
            }
            return personaService.listAll();
        } catch (Exception e) {
            throw new NotFoundException("No se encontraron personas");
        }
    }
    @GetMapping("/listar/id")
    @Override
    public Persona obtenerById(@RequestParam("id") int id) {
        try {
            if (personaService.findById(id)==null){
                throw new NotFoundException("No se encontraron personas");
            }
            return personaService.findById(id);
        } catch (Exception e) {
            throw new NotFoundException("No se encontraron personas");
        }
    }
    @PostMapping("/añadir")
    @Override
    public ResponseEntity<String> addNew(@RequestBody Persona persona) {
        try {
            personaService.addNew(persona);
            return ResponseEntity.ok("Persona agregada correctamente!");
        } catch (Exception e) {
            throw new InternalServerErrorException("No se pudo agregar persona");
        }
    }
    @PutMapping("/editar")
    @Override
    public ResponseEntity<String> editById(@RequestParam("id") int id,@RequestBody Persona persona) {
        try {
            personaService.modify(id,persona);
            return ResponseEntity.ok("Persona modificada correctamente!");
        } catch (Exception e) {
            throw new InternalServerErrorException("No se pudo modificar persona");
        }
    }
    @DeleteMapping("/eliminar")
    @Override
    public ResponseEntity<String> deleteById(@RequestParam("id") int id) {
        try {
            personaService.remove(id);
            return ResponseEntity.ok("Persona eliminada correctamente!");
        } catch (Exception e) {
            throw new InternalServerErrorException("No se pudo eliminar persona");
        }
    }
    @GetMapping("/listar/puesto")
    public ResponseEntity<List<Persona>> findByPuesto(@RequestParam("id") int id){
        try {
            return ResponseEntity.ok(personaService.findPersonasByPuesto(id));
        } catch (Exception e) {
            throw new NotFoundException("No se encontraron personas");
        }
    }
}
