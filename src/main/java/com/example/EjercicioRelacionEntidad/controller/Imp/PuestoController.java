package com.example.EjercicioRelacionEntidad.controller.Imp;

import com.example.EjercicioRelacionEntidad.controller.IController;
import com.example.EjercicioRelacionEntidad.exceptions.InternalServerErrorException;
import com.example.EjercicioRelacionEntidad.exceptions.NotFoundException;
import com.example.EjercicioRelacionEntidad.model.Puesto;
import com.example.EjercicioRelacionEntidad.service.Imp.PuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/puesto")
public class PuestoController implements IController<Puesto> {
    @Autowired
    private PuestoService puestoService;
    @GetMapping("/listar")
    @Override
    public List<Puesto> obtenerTodos() {
        try {
            if(puestoService.listAll()==null){
                throw new NotFoundException("No se encontraron puestos");
            }
            return puestoService.listAll();
        } catch (Exception e) {
            throw new NotFoundException("No se encontraron puestos");
        }
    }
    @GetMapping("/listar/id")
    @Override
    public Puesto obtenerById(@RequestParam("id") int id) {
        try {
            if (puestoService.findById(id)==null){
                throw new NotFoundException("No se encontraron puestos");
            }
            return puestoService.findById(id);
        } catch (Exception e) {
            throw new NotFoundException("No se encontraron puestos");
        }
    }
    @PostMapping("/añadir")
    @Override
    public ResponseEntity<String> addNew(@RequestBody Puesto Entity) {
        try {
            puestoService.addNew(Entity);
            return ResponseEntity.ok("Puesto agregado correctamente!");
        } catch (Exception e) {
            throw new InternalServerErrorException("No se pudo agregar puesto");
        }
    }
    @PutMapping("/editar")
    @Override
    public ResponseEntity<String> editById(@RequestParam("id") int id,@RequestBody Puesto Entity) {
        try {
            puestoService.modify(id,Entity);
            return ResponseEntity.ok("Puesto modificado correctamente!");
        } catch (Exception e) {
            throw new InternalServerErrorException("No se pudo editar puesto");
        }
    }
    @DeleteMapping("/eliminar")
    @Override
    public ResponseEntity<String> deleteById(@RequestParam("id") int id) {
        try {
            puestoService.remove(id);
            return ResponseEntity.ok("Puesto eliminado correctamente!");
        } catch (Exception e) {
            throw new InternalServerErrorException("No se pudo elimiar puesto");
        }
    }
}
