package com.example.EjercicioRelacionEntidad.security.service;


import com.example.EjercicioRelacionEntidad.security.model.Usuario;
import com.example.EjercicioRelacionEntidad.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetallesUsuarioService implements UserDetailsService {
    @Autowired
    private UserRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> usuarioOptional = this.usuarioRepository.findByUsername(username);

        if(usuarioOptional.isEmpty()){
            throw new UsernameNotFoundException(String.format("El usuario: %s no existe", username));
        }
        Usuario result = usuarioOptional.orElseThrow();
        List<GrantedAuthority> authorities = result.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                .collect(Collectors.toList());

        return new User(
                result.getUsername(),
                result.getPassword(),
                result.isHabilitado(),
                true,
                true,
                true,
                authorities
        );
    }
}
