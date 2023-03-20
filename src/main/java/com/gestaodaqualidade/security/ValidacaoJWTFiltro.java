package com.gestaodaqualidade.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gestaodaqualidade.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class ValidacaoJWTFiltro extends BasicAuthenticationFilter {

    private UsuarioService usuarioService;

    public ValidacaoJWTFiltro(AuthenticationManager authenticationManager, UsuarioService usuarioService) {
        super(authenticationManager);
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String atributo = request.getHeader("Authorization");

        if(atributo == null || !atributo.startsWith("Bearer ")){
            chain.doFilter(request, response);
            return;
        }

        String token = atributo.replace("Bearer ", "");

        UsernamePasswordAuthenticationToken authenticationToken = this.pegarUsuarioDoToken(token);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(request, response);

    }


    private UsernamePasswordAuthenticationToken pegarUsuarioDoToken(String token){
        String email = JWT.require(Algorithm.HMAC512(AutenticacaoJWTFiltro.SEGREDO))
                .build()
                .verify(token)
                .getSubject();

        if(email == null){
            return null;
        }

        UserDetails usuario = this.usuarioService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
    }
}
