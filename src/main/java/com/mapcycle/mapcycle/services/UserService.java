package com.mapcycle.mapcycle.services;

import com.mapcycle.mapcycle.models.dtos.UserRegistrationDto;
import com.mapcycle.mapcycle.models.dtos.UserResponseDto;
import com.mapcycle.mapcycle.models.dtos.UserUpdateDto;
import com.mapcycle.mapcycle.models.entities.User;
import com.mapcycle.mapcycle.models.mappers.UserMapper;
import com.mapcycle.mapcycle.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toResponseDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        return userMapper.toResponseDto(user);
    }

    @Transactional
    public UserResponseDto createUser(UserRegistrationDto registrationDto) {
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new IllegalStateException("Username already taken.");
        }
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new IllegalStateException("Email already in use.");
        }

        User user = userMapper.toEntity(registrationDto);

        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        User savedUser = userRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }

    @Transactional
    public UserResponseDto updateUser(Long userId, UserUpdateDto updateDto) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        userMapper.updateUserFromDto(updateDto, existingUser);

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toResponseDto(updatedUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }
}