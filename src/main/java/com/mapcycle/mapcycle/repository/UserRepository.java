package com.mapcycle.mapcycle.repository;

import com.mapcycle.mapcycle.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query to find a user by username
    Optional<User> findByUsername(String username);
}
