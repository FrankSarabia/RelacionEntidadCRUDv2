package com.example.EjercicioRelacionEntidad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class PerfilController {
    @Autowired
    private Environment env;

    @GetMapping("/profile")
    public String getActiveProfile() {
        return "Active profile: " + Arrays.toString(env.getActiveProfiles());
    }
}
