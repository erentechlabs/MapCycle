package com.mapcycle.mapcycle.user.auth;


import com.mapcycle.mapcycle.user.User;
import com.mapcycle.mapcycle.shared.security.jwt.JWTProvider;
import com.mapcycle.mapcycle.user.UserDto;
import com.mapcycle.mapcycle.user.UserMapper;
import com.mapcycle.mapcycle.user.UserRepository;
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

    @Autowired
    private UserMapper userMapper;

    // Register a new user
    public UserDto register(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    // Authenticate and generate a JWT token
    public String verify(UserDto userDto) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userDto.getUsername(),
                                userDto.getPassword())
                );

        if (authentication.isAuthenticated())
            return jwtProvider.generateToken(userDto.getUsername());

        return "Login failed";
    }
}
