package com.example.EjercicioRelacionEntidad.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class MultiHttpSecurityConfig {



    @Bean
    @Order(1)
    public SecurityFilterChain puestoSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/puesto/**")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().hasRole("ADMIN")
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public SecurityFilterChain anySecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    //Aqui se crean dos usuarios ficticios uno admin y otro user para roles distintos
        public UserDetailsService userDetailsService() throws Exception {
            // ensure the passwords are encoded properly
            User.UserBuilder users = User.withDefaultPasswordEncoder();
            InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
            manager.createUser(users.username("user").password("password").roles("USER").build());
            manager.createUser(users.username("admin").password("password").roles("USER","ADMIN").build());
            return manager;
    }
}
