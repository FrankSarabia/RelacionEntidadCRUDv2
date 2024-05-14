package com.example.EjercicioRelacionEntidad.service;

import com.example.EjercicioRelacionEntidad.model.Puesto;

import java.util.List;

public interface IServicios<T> {
    List<T> listAll();
    T findById(int id);
    void addNew(T Entity);
    void remove(int id);
    void modify(int id,T Entity);
}
