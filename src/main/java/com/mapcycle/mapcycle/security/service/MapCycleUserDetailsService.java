package com.mapcycle.mapcycle.security.service;

import com.mapcycle.mapcycle.domain.entities.User;
import com.mapcycle.mapcycle.security.model.UserPrincipal;
import com.mapcycle.mapcycle.repository.UserRepository;
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
