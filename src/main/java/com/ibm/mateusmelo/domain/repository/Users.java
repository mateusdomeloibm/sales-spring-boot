package com.ibm.mateusmelo.domain.repository;

import com.ibm.mateusmelo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Users extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
