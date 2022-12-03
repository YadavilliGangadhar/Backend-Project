package com.backendproject.taskmanager.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backendproject.taskmanager.entity.Users;
import com.backendproject.taskmanager.payload.UserDto;
import com.backendproject.taskmanager.repository.UserRepo;
import com.backendproject.taskmanager.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	//Autowired repository of users
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {
		// userDto is not an entity of Users
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Users users = userDtoToUsers(userDto); //converted userDto to users using this method
		Users savedUser = userRepo.save(users);
		
		return usersToUserDto(savedUser); //converted Users to userDto using this method
	}
	
	private Users userDtoToUsers(UserDto userDto) {
		Users users = new Users();
		users.setName(userDto.getName());
		users.setEmail(userDto.getEmail());
		users.setPassword(userDto.getPassword());
		return users;
	}
	
	private UserDto usersToUserDto(Users savedUser) {
		UserDto userDto = new UserDto();
		userDto.setId(savedUser.getId());
		userDto.setEmail(savedUser.getEmail());
		userDto.setPassword(savedUser.getPassword());
		userDto.setName(savedUser.getName());
		return userDto;
	}

}
