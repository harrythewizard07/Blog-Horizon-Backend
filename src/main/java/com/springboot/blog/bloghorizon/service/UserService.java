package com.springboot.blog.bloghorizon.service;

import java.util.List;

import com.springboot.blog.bloghorizon.payload.UserDto;

public interface UserService {
	
	List<UserDto> getAllUsers();
	
	UserDto getUserById(long id);
	
	UserDto getUserByUsername(String username);
	
	UserDto updateUser(UserDto userDto, long id);
	
	void deleteUserByid(long id);
}
