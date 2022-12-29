package com.backendproject.taskmanager.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backendproject.taskmanager.entity.Task;

public interface TaskRepo extends JpaRepository<Task, Long>{

	List<Task> findAllByUsersId(long userid);
	
	@Transactional
	@Modifying
    @Query("UPDATE Task t SET t.taskname = :taskname WHERE t.id = :taskId")
    int updateTask(@Param("taskId") long taskid, @Param("taskname") String taskname);


}
