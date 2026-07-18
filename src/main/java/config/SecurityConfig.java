package com.saurabh.ecommerce.config;

import com.saurabh.ecommerce.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .userDetailsService(userDetailsService)

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/register",
                                "/login",
                                "/css/**",
                                "/images/**",
                                "/uploads/**"
                        ).permitAll()

                        .requestMatchers("/products/new/**").hasRole("ADMIN")
                        .requestMatchers("/products/edit/**").hasRole("ADMIN")
                        .requestMatchers("/products/delete/**").hasRole("ADMIN")
                        .requestMatchers(
                                "/admin",
                                "/categories/**",
                                "/products/new/**",
                                "/products/edit/**",
                                "/products/delete/**"
                        ).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/products", true)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }
}