package com.mapcycle.mapcycle.service.coreservice;


import com.mapcycle.mapcycle.domain.entities.User;
import com.mapcycle.mapcycle.repository.UserRepository;
import com.mapcycle.mapcycle.security.jwt.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    public User register(User user) {
        return userRepository.save(user);

    }

    public String verify(User user) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (authentication.isAuthenticated())
            return jwtProvider.generateToken(user.getUsername());

        return "Login failed";

    }
}
