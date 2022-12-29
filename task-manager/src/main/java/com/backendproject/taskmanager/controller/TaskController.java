package com.backendproject.taskmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendproject.taskmanager.payload.TaskDto;
import com.backendproject.taskmanager.service.TaskService;

@RestController
@RequestMapping("/api")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	//save task
	@PostMapping("/{userid}/tasks")
	public ResponseEntity<TaskDto> saveTask(
			@PathVariable(name = "userid") long userid,
			@RequestBody TaskDto taskDto
			){
		return new ResponseEntity<TaskDto>(taskService.saveTask(userid, taskDto),HttpStatus.CREATED);
	}
	//update task
	@PutMapping("/{userid}/tasks")
	public ResponseEntity<TaskDto> updateTask(
			@PathVariable(name="userid") long userid,
			@RequestBody TaskDto taskDto
			){
		return new ResponseEntity<TaskDto>(taskService.updateTask(userid, taskDto),HttpStatus.ACCEPTED);
	}
	//get all task
	@GetMapping("/{userid}/tasks")
	public ResponseEntity<List<TaskDto>> getAllTasks(
			@PathVariable(name = "userid") long userid
			){
		return new ResponseEntity<>(taskService.getAllTasks(userid),HttpStatus.OK);
	}
	//get individual task
	@GetMapping("/{userid}/tasks/{taskid}")
	public ResponseEntity<TaskDto> getTask(
			@PathVariable(name = "userid") long userid,
			@PathVariable(name = "taskid") long taskid
			){
		return new ResponseEntity<>(taskService.getTask(userid, taskid),HttpStatus.OK);
	}
	
	@PreAuthorize(value = "ROLE_ADMIN")
	//delete individual task
	@DeleteMapping("/{userid}/tasks/{taskid}")
	public ResponseEntity<String> deleteTask(
			@PathVariable(name = "userid") long userid,
			@PathVariable(name = "taskid") long taskid
			){
		taskService.deleteTask(userid, taskid);
		return new ResponseEntity<>("task deleted successfully",HttpStatus.OK);
	}

}
