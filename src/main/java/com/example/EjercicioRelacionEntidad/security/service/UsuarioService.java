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
    //Inyectamos los Repos y el Bean del Encoder
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

    /*Cuando se agregue un nuevo usuario, por defecto este tendra el
     * rol de usuario normal, pero con una bandera en el request, se
     * puede determinar si el usuario agregado tambien tendra el
     * rol de administrador
     *
     * El rol no se crea con el usuario, los roles ya existen
     * */
    @Transactional
    public Usuario save(Usuario usr) {
        /*Primero agregamos el rol de usuario normal al objeto que recibimos*/

        Optional<Rol> defaultRol = this.rolRepositorio.findByNombre("ROLE_USER");

        //Lista de roles asociada al usuario
        List<Rol> roles = new ArrayList<>();

        //Si encuentra el registro con el nombre ROLE_USER
        //Otra forma de escribirlo = defaultRol.ifPresent(rol -> roles.add(rol));
        defaultRol.ifPresent(roles::add);


        //Ahora se tiene que verificar que en el atributo del usr recibido
        //el valor de la bander "admin" para determinar si el rol de ADMIN se agrega
        //a la lista
        if(usr.isAdmin()){
            Optional<Rol> adminRol = this.rolRepositorio.findByNombre("ROLE_ADMIN");

            adminRol.ifPresent(roles::add);
        }
        //Agregamos la lista de roles al usuario
        usr.setRoles(roles);

        /*Ahora, tenemos que cifrar la psw recibida del objeto usr
         * para guardarla en la BD.
         * */

        String pswCifrada = passwordEncoder.encode(usr.getPassword());

        //Agregamos la psw cifrada al usr
        usr.setPassword(pswCifrada);

        //Finalmente agregamos el usuario a la BD.
        return this.usuarioRepository.save(usr);
    }

    public int guardarUsuario(Usuario usr) {
        try{
            /*Antes de intentar agregar un usuario tenemos que saber si existe en la base de datos.*/
            Optional<Usuario> query = this.usuarioRepository.findByUsername(usr.getUsername());

            if(query.isPresent()){
                //Quiere decir que si hay un registro, por lo que habria duplicado, entonces no se agrega
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
