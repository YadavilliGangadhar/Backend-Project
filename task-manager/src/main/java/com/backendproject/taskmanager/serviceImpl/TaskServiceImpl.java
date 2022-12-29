package com.backendproject.taskmanager.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.backendproject.taskmanager.entity.Task;
import com.backendproject.taskmanager.entity.Users;
import com.backendproject.taskmanager.exception.APIException;
import com.backendproject.taskmanager.exception.TaskNotFound;
import com.backendproject.taskmanager.exception.UserNotFound;
import com.backendproject.taskmanager.payload.TaskDto;
import com.backendproject.taskmanager.repository.TaskRepo;
import com.backendproject.taskmanager.repository.UserRepo;
import com.backendproject.taskmanager.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private ModelMapper modelMapper; //modelMapper is used for casting data from entity to DTO or vice versa instead of creating conversion methods manually.
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private TaskRepo taskRepo;
	
	@Override
	public TaskDto saveTask(long userid, TaskDto taskDto) {
		Users user = userRepo.findById(userid).orElseThrow(
			() -> new UserNotFound(String.format("User Id %d not found", userid))
				);
		Task task = modelMapper.map(taskDto, Task.class);
		task.setUsers(user);
		Task savedTask = taskRepo.save(task);
		return modelMapper.map(savedTask, TaskDto.class);
	}

	@Override
	public List<TaskDto> getAllTasks(long userid) {
		userRepo.findById(userid).orElseThrow(
				() -> new UserNotFound(String.format("User Id %d not found", userid))
					);
		List<Task> tasks = taskRepo.findAllByUsersId(userid);
		return tasks.stream().map(
				task -> modelMapper.map(task, TaskDto.class)
				).collect(Collectors.toList());
	}

	@Override
	public TaskDto getTask(long userid, long taskid) {
		Users users = userRepo.findById(userid).orElseThrow(
				() -> new UserNotFound(String.format("User Id %d not found", userid))
					);
		Task task = taskRepo.findById(taskid).orElseThrow(
				() -> new TaskNotFound(String.format("task id %d not found", taskid))
				);
		if(users.getId() != task.getUsers().getId()) {
			throw new APIException(String.format("given task id %d is not belongs to given user id %d", taskid, userid));
		}
		return modelMapper.map(task, TaskDto.class);
	}

	@Override
	public void deleteTask(long userid, long taskid) {
		Users users = userRepo.findById(userid).orElseThrow(
				() -> new UserNotFound(String.format("User Id %d not found", userid))
					);
		Task task = taskRepo.findById(taskid).orElseThrow(
				() -> new TaskNotFound(String.format("task id %d not found", taskid))
				);
		if(users.getId() != task.getUsers().getId()) {
			throw new APIException(String.format("given task id %d is not belongs to given user id %d", taskid, userid));
		}
		taskRepo.deleteById(taskid);
	}

	@Override
	public TaskDto updateTask(long userid, TaskDto taskDto) {
		Users users = userRepo.findById(userid).orElseThrow(
				() -> new UserNotFound(String.format("User Id %d not found", userid))
					);
		
		Task task = modelMapper.map(taskDto, Task.class);
		task.setUsers(users);
		int updatedTask = taskRepo.updateTask(task.getId(),task.getTaskname());

		return modelMapper.map(updatedTask, TaskDto.class);
	}

}
