package com.mapcycle.mapcycle.shared.security.jwt;

import com.mapcycle.mapcycle.shared.security.MapCycleUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    ApplicationContext context;

    // Create a filter to validate JWT tokens
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization"); // Get the Authorization header from the request
        String token = null; // Initialize the token variable
        String username = null;

        // Extract the token from the Authorization header and validate it
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Extract the token from the header
            username = jwtProvider.extractUserName(token); // Extract the username from the token
        }

        // Check if the token is valid and set the authentication context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load the user details and validate the token
            UserDetails userDetails = context.getBean(MapCycleUserDetailsService.class).loadUserByUsername(username);

            // If the token is valid, set the authentication context
            if (jwtProvider.validateToken(token, userDetails)) {

                // Create a new authentication token and set its details
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Set the authentication details and context
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication context
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
