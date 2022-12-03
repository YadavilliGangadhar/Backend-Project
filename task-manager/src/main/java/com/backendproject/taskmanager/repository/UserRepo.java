package com.backendproject.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendproject.taskmanager.entity.Users;

public interface UserRepo extends JpaRepository<Users, Long>{

}
