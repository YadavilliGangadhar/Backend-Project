package com.backendproject.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendproject.taskmanager.entity.Users;
import com.backendproject.taskmanager.payload.UserDto;
import com.backendproject.taskmanager.service.UserService;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	//POST Mapping store the user into the DB
	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.createUser(userDto),HttpStatus.CREATED);
	}

}
