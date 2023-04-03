package com.gestaodaqualidade.security;

import com.gestaodaqualidade.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = this.authenticationConfiguration.getAuthenticationManager();

        http.csrf().disable()
                .authorizeHttpRequests(
                        authz ->
                                authz.requestMatchers(HttpMethod.POST, "/login").permitAll()
                                        .requestMatchers("/error").permitAll()
                                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                        .requestMatchers("/empresa/**").hasAnyRole("MASTER", "ADM")
                                        .requestMatchers("/usuario/criarSenha").permitAll()
                                        .requestMatchers("/usuario/resetarSenha").permitAll()
                                        .requestMatchers("/usuario/**").hasAnyRole("MASTER", "ADM")
                                        .anyRequest().authenticated()
                                        .and()
                                        .addFilter(new AutenticacaoJWTFiltro(authenticationManager))
                                        .addFilter(new ValidacaoJWTFiltro(authenticationManager, usuarioService)
                ));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return this.usuarioService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userDetailsService());
        provider.setPasswordEncoder(this.passwordEncoder());

        return provider;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*");
            }
        };
    }

}
