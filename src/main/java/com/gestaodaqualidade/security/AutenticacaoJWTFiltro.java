package com.gestaodaqualidade.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestaodaqualidade.model.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;

public class AutenticacaoJWTFiltro extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;

    public static final String SEGREDO = "Z2VzdGFvLWRhLXF1YWxpZGFkZS1hbGZhbWlkaWE=";

    public AutenticacaoJWTFiltro(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Usuario usuario = (Usuario) authResult.getPrincipal();

        String token = JWT.create().withSubject(usuario.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 21600000))
                .sign(Algorithm.HMAC512(SEGREDO));

        response.getWriter().write("{\"Authorization\": \"Bearer " + token + "\"}");
        response.getWriter().flush();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            Usuario usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario.getEmail(),
                    usuario.getSenha());

            setDetails(request, authenticationToken);

            return this.authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException("Falha na autenticação", e);
        }
    }
}
