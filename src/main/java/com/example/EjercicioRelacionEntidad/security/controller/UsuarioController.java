package com.example.EjercicioRelacionEntidad.security.controller;

import com.example.EjercicioRelacionEntidad.security.model.Usuario;
import com.example.EjercicioRelacionEntidad.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/security")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioServicio;


    @GetMapping("usuarios")
    public ResponseEntity<Map<String,Object>> listar(){
        try{
            Map<String,Object> json = new HashMap<>();
            json.put("data", this.usuarioServicio.findAll());
            json.put("status", HttpStatus.OK);

            return new ResponseEntity<>(json, HttpStatus.OK);
        }catch(Exception e){
            Map<String,Object> json = new HashMap<>();

            json.put("message", "Error en el servidor...");
            json.put("status", HttpStatus.INTERNAL_SERVER_ERROR);

            return new ResponseEntity<>(json,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("usuarios")
    public ResponseEntity<Map<String,Object>> agregarUsuario(@RequestBody Usuario usr){
        try{
            Map<String,Object> json = new HashMap<>();
            usr.setHabilitado(true);
            int resultado = this.usuarioServicio.guardarUsuario(usr);
            if(resultado == 1){
                json.put("message","Usuario registrado correctamente...");
                json.put("status", HttpStatus.CREATED);

                return new ResponseEntity<>(json, HttpStatus.CREATED);
            }else if(resultado == 0){
                json.put("message","Ya existe un usuario registrado con esa informacion");
                json.put("status", HttpStatus.BAD_REQUEST);

                return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
            }else{
                json.put("message", "Error en el servidor...");
                json.put("status", HttpStatus.INTERNAL_SERVER_ERROR);

                return new ResponseEntity<>(json,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch(Exception e){
            Map<String,Object> json = new HashMap<>();

            json.put("message", "Error en el servidor...");
            json.put("status", HttpStatus.INTERNAL_SERVER_ERROR);

            return new ResponseEntity<>(json,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("registrar")
    public ResponseEntity<Map<String,Object>> registrarUsuario(@RequestBody Usuario usr){
        try{
            Map<String,Object> json = new HashMap<>();
            usr.setAdmin(false);
            usr.setHabilitado(true);

            int resultado = this.usuarioServicio.guardarUsuario(usr);

            if(resultado == 1){
                json.put("message","Usuario registrado correctamente!");
                json.put("status", HttpStatus.CREATED);

                return new ResponseEntity<>(json, HttpStatus.CREATED);
            }else if(resultado == 0){
                json.put("message","Ya existe un usuario registrado con esa informacion");
                json.put("status", HttpStatus.BAD_REQUEST);

                return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
            }else{
                json.put("message", "Error en el servidor...");
                json.put("status", HttpStatus.INTERNAL_SERVER_ERROR);

                return new ResponseEntity<>(json,HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }catch(Exception e){
            Map<String,Object> json = new HashMap<>();

            json.put("message", "Error en el servidor...");
            json.put("status", HttpStatus.INTERNAL_SERVER_ERROR);

            return new ResponseEntity<>(json,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
