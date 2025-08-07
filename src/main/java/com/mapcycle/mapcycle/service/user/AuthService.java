package com.mapcycle.mapcycle.service.user;


import com.mapcycle.mapcycle.domain.entities.User;
import com.mapcycle.mapcycle.repository.UserRepository;
import com.mapcycle.mapcycle.security.jwt.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    // Register a new user
    public User register(User user) {
        return userRepository.save(user);

    }

    // Authenticate and generate a JWT token
    public String verify(User user) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        // If authentication is successful, generate a JWT token
        if (authentication.isAuthenticated())
            return jwtProvider.generateToken(user.getUsername());

        return "Login failed";

    }
}
