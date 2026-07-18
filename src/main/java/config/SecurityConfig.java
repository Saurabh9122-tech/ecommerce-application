package com.saurabh.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())   // ← Step 7

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/",
                                "/products/**",
                                "/register",
                                "/login",
                                "/images/**",
                                "/uploads/**",
                                "/css/**",
                                "/js/**"
                        ).permitAll()

                        .requestMatchers("/categories/**")
                        .hasRole("ADMIN")

                        .anyRequest()
                        .authenticated()
                )

                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/products", true)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}