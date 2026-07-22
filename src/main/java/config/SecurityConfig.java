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
    private final LoginSuccessHandler loginSuccessHandler;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          LoginSuccessHandler loginSuccessHandler) {

        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .userDetailsService(userDetailsService)

                .authorizeHttpRequests(auth -> auth


                        // Public
                        .requestMatchers(
                                "/",
                                "/register",
                                "/login",
                                "/css/**",
                                "/js/**",
                                "/uploads/**"
                        ).permitAll()



                        // ADMIN ONLY
                        .requestMatchers(
                                "/products/new",
                                "/products/save",
                                "/products/edit/**",
                                "/products/delete/**",
                                "/categories/**",
                                "/admin/**"
                        ).hasRole("ADMIN")



                        // USER ORDER ACCESS
                        .requestMatchers(
                                "/orders/**",
                                "/cart/**",
                                "/wishlist/**",
                                "/profile/**",
                                "/products",
                                "/products/{id}"
                        ).hasAnyRole("USER","ADMIN")



                        .anyRequest().authenticated()

                )

                .formLogin(login -> login

                        .loginPage("/login")

                        .successHandler(loginSuccessHandler)

                        .permitAll()

                )

                .logout(logout -> logout

                        .logoutSuccessUrl("/login?logout")

                        .permitAll()

                );

        return http.build();

    }
}