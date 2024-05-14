package com.example.EjercicioRelacionEntidad.service.Imp;

import com.example.EjercicioRelacionEntidad.exceptions.InternalServerErrorException;
import com.example.EjercicioRelacionEntidad.exceptions.NotFoundException;
import com.example.EjercicioRelacionEntidad.model.Puesto;
import com.example.EjercicioRelacionEntidad.repository.PuestoRepository;
import com.example.EjercicioRelacionEntidad.service.IServicios;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PuestoService implements IServicios<Puesto> {
    @Autowired
    private final PuestoRepository puestoRepository;

    public PuestoService(PuestoRepository puestoRepository) {
        this.puestoRepository = puestoRepository;
    }

    @Override
    public List<Puesto> listAll() {
        try {
            return puestoRepository.findAll();
        } catch (Exception e) {
            throw new NotFoundException("No se encontraron puestos");
        }
    }

    @Override
    public Puesto findById(int id) {
        try {
            return puestoRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new NotFoundException("No se encontraron puestos con id: "+ id);
        }
    }
    @Transactional
    @Override
    public void addNew(Puesto nuevoPuesto) {
        try {
            puestoRepository.save(nuevoPuesto);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al agregar puesto");
        }
    }
    @Transactional
    @Override
    public void remove(int id) {
        try {
            puestoRepository.deleteById(id);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al eliminar puesto");
        }
    }
    @Transactional
    @Override
    public void modify(int id, Puesto newPuesto) {
        try {
            Optional<Puesto> puestoExistente = puestoRepository.findById(id);
            if (puestoExistente.isPresent()) {
                Puesto puesto = puestoExistente.get();
                puesto.setNombre(newPuesto.getNombre());
                puesto.setSalario(newPuesto.getSalario());
                puestoRepository.save(puesto);
            }
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al modificar puesto");
        }
    }
}
