package com.example.EjercicioRelacionEntidad.security.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="USERS")
public class Usuario {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="USERNAME", unique = true, nullable = false)
    @NotBlank
    private String username;

    @Column(name="PASSWORD")
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonIgnoreProperties({"usuarios","handler","hibernateLazyInitializer"})
    @ManyToMany
    @JoinTable(
            name = "USERS_ROLES",//el nombre de la tabla intermedia en la BD
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID","ROLE_ID"})}
    )
    private List<Rol> roles;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean admin;

    private boolean habilitado;
    public void prePersist(){
        habilitado = true;
    }
}
