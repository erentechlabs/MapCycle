package com.mapcycle.mapcycle.security.config;

import com.mapcycle.mapcycle.security.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    // Configure security filters
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        return http
                .csrf(customizer -> customizer.disable()) // Disable CSRF protection
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/auth/register", "/api/auth/login", "/api/auth/logout") // Allow access to these endpoints
                        .permitAll() // Permit all requests except these endpoints
                        .anyRequest().authenticated()) // Require authentication for all other requests
                .httpBasic(Customizer.withDefaults()) // Use HTTP Basic authentication
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Set session management policy to STATELESS
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter before UsernamePasswordAuthenticationFilter
                .build();

    }

    // Configure authentication provider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); // Use DAO authentication provider which is using for Spring Security
        provider.setUserDetailsService(userDetailsService); // Set user details service
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); // Set password encoder with 12 rounds of hashing
        return provider;
    }

    // Configure authentication manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Return authentication manager

    }
}
