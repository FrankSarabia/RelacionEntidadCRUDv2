package com.example.EjercicioRelacionEntidad.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IController<T> {
    List<T> obtenerTodos();
    T obtenerById(int id);
    ResponseEntity<String> addNew(T Entity);
    ResponseEntity<String> editById(int id,T Entity);
    ResponseEntity<String> deleteById(int id);
}
