package com.example.EjercicioRelacionEntidad.security;

import com.example.EjercicioRelacionEntidad.security.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        Usuario usr = null;
        String username = null;
        String psw = null;
        try{
            usr = new ObjectMapper().readValue(request.getInputStream(),Usuario.class);
            username = usr.getUsername();
            psw = usr.getPassword();
        }catch(Exception e){
            System.out.println(e);
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, psw);
        return this.authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException{
        User usr = (User) authResult.getPrincipal();
        String username = usr.getUsername();
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        Claims claims = Jwts.claims().add("authorities",new ObjectMapper().writeValueAsString(roles)).build();
        String token = Jwts.builder()
                .subject(username)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(TokenJwtConfig.SECRET_KEY)
                .compact();
        response.addHeader(TokenJwtConfig.HEADER_AUTHORIZATION, TokenJwtConfig.PREFIX_TOKEN + token);
        Map<String, Object> json = new HashMap<>();
        json.put("token", token);
        json.put("username", username);
        json.put("message", String.format("Bienvenido %s, has iniciado sesion!!!", username));
        response.getWriter().write(new ObjectMapper().writeValueAsString(json));
        response.setContentType(TokenJwtConfig.CONTENT_TYPE);
        response.setStatus(200);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {
        Map<String, Object> json = new HashMap<>();
        json.put("message", "Error en la autenticacion, credenciales incorrectas...");
        json.put("error", failed.getMessage());


        response.getWriter().write(new ObjectMapper().writeValueAsString(json));
        response.setContentType(TokenJwtConfig.CONTENT_TYPE);
        response.setStatus(401);
    }
}
