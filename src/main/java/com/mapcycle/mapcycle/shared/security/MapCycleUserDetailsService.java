package com.mapcycle.mapcycle.shared.security;

import com.mapcycle.mapcycle.user.User;
import com.mapcycle.mapcycle.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MapCycleUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    // Load user details by username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);

        // If user is not found, throw an exception
        if (user.isEmpty()) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user.orElse(null));
    }
}
