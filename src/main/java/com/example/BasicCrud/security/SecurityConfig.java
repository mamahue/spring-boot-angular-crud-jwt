package com.example.BasicCrud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 🔓 desactiva CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 🔓 todas las rutas públicas
                );

        return http.build();
    }

@Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
}
}
