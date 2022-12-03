package com.backendproject.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendproject.taskmanager.entity.Task;

public interface TaskRepo extends JpaRepository<Task, Long>{

	List<Task> findAllByUsersId(long userid);

}
