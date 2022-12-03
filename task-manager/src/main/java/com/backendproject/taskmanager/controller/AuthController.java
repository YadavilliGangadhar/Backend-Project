package com.backendproject.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendproject.taskmanager.entity.Users;
import com.backendproject.taskmanager.payload.LoginDto;
import com.backendproject.taskmanager.payload.UserDto;
import com.backendproject.taskmanager.service.UserService;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//POST Mapping store the user into the DB
	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.createUser(userDto),HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody LoginDto loginDto){
		Authentication authentication =
				authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
					);
		System.out.println(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseEntity<>("user loged in successfully",HttpStatus.OK);
	}

}
