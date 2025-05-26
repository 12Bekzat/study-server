package com.study.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.entities.User;

public interface IUserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
