package com.example.EjercicioRelacionEntidad.security.service;

import com.example.EjercicioRelacionEntidad.security.model.Rol;
import com.example.EjercicioRelacionEntidad.security.model.Usuario;
import com.example.EjercicioRelacionEntidad.security.repository.RolRepository;
import com.example.EjercicioRelacionEntidad.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UserRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return (List<Usuario>) this.usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorNombre(String username) {
        return this.usuarioRepository.findByUsername(username);
    }
    @Transactional
    public Usuario save(Usuario usr) {

        Optional<Rol> defaultRol = this.rolRepositorio.findByNombre("ROLE_USER");
        List<Rol> roles = new ArrayList<>();

        defaultRol.ifPresent(roles::add);

        if(usr.isAdmin()){
            Optional<Rol> adminRol = this.rolRepositorio.findByNombre("ROLE_ADMIN");

            adminRol.ifPresent(roles::add);
        }

        usr.setRoles(roles);

        String pswCifrada = passwordEncoder.encode(usr.getPassword());

        usr.setPassword(pswCifrada);

        return this.usuarioRepository.save(usr);
    }

    public int guardarUsuario(Usuario usr) {
        try{
            Optional<Usuario> query = this.usuarioRepository.findByUsername(usr.getUsername());

            if(query.isPresent()){
                return 0;
            }else{
                Usuario registro = save(usr);
                return 1;
            }
        }catch(Exception e){
            System.out.println("Error en el metodo de guardarUsuario en el servicio");
            return -1;
        }
    }

}
