package com.example.EjercicioRelacionEntidad.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="PERSONAS")
@Data
@Getter
@Setter
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre")
    private String name;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "edad")
    private int edad;
    @Column(name = "direccion")
    private  String direccion;
    @ManyToOne
    @JoinColumn(name = "puesto_id")
    private Puesto puesto;
}
