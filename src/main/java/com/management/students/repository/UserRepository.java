package com.management.students.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.students.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}
