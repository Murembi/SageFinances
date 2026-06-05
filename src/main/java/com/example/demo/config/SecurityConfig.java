package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Authorize requests
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login").permitAll()
                        .requestMatchers("/admin/**").hasRole("Admin")
                        .requestMatchers("/manager/**").hasRole("Manager")
                        .requestMatchers("/borrower/**").hasRole("Borrower")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )

                // Session management
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // create session only if needed
                        .sessionFixation().migrateSession() // protect against session fixation
                )

                // Concurrent session control
                .sessionManagement(session -> session
                        .maximumSessions(1) // only one active session per user
                        .expiredUrl("/login?expired=true") // redirect if session expires
                )

                // Logout handling
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login?logout=true") //redirect if session expires
                )

                .httpBasic(basic -> {})
                .csrf(AbstractHttpConfigurer::disable
                );

        return http.build();
    }
}
