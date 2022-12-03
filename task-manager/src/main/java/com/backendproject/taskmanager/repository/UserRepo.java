package com.backendproject.taskmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendproject.taskmanager.entity.Users;

public interface UserRepo extends JpaRepository<Users, Long>{

	Optional<Users> findByEmail(String email);

}
