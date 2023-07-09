package com.springboot.blog.bloghorizon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.blog.bloghorizon.entity.User;
import com.springboot.blog.bloghorizon.exception.ResourceNotFoundException;
import com.springboot.blog.bloghorizon.payload.UserDto;
import com.springboot.blog.bloghorizon.repository.UserRepository;
import com.springboot.blog.bloghorizon.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper mapper;

	@Override
	public List<UserDto> getAllUsers() {

		List<User> users = userRepository.findAll();
		
		log.info("Fetched data for all users successfully.");

		return users.stream().map(user -> mapToDto(user)).collect(Collectors.toList());
	}

	@Override
	public UserDto getUserById(long id) {

		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.toString(id)));
		
		log.info("Fetched data for a single user successfully.");

		return mapToDto(user);
	}
	
	@Override
	public UserDto getUserByUsername(String username) {
		
		User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
		
		log.info("Fetched data for a user by username successfully.");
		
		return mapToDto(user);
	}

	@Override
	public UserDto updateUser(UserDto userDto, long id) {

		// finding user by id in the database
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.toString(id)));

		user.setName(userDto.getName());
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));

		User updatedUser = userRepository.save(user);
		
		log.info("Updated user data successfully.");

		return mapToDto(updatedUser);
	}

	@Override
	public void deleteUserByid(long id) {

		// finding user by id in the database
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.toString(id)));
		
		log.info("Deleted user data successfully.");

		userRepository.delete(user);
	}

	// Converting Entity to DTO
	private UserDto mapToDto(User user) {

		UserDto userDto = mapper.map(user, UserDto.class);

		return userDto;
	}

	// Converting DTO to Entity
	@SuppressWarnings("unused")
	private User mapToEntity(UserDto userDto) {

		User user = mapper.map(userDto, User.class);

		return user;
	}

}
