package com.springboot.blog.bloghorizon.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.springboot.blog.bloghorizon.payload.UserDto;
import com.springboot.blog.bloghorizon.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
	
	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;
	

	@Test
	public void whenGetAllUsers_thenReturnAllUsers() {
		UserDto user1 = UserDto.builder().id(1L).build();
		
		UserDto user2 = UserDto.builder().id(2L).build();
		
		List<UserDto> expectedUsers = new ArrayList<>();
		expectedUsers.add(user1);
		expectedUsers.add(user2);
		
		//action
		when(userService.getAllUsers()).thenReturn(expectedUsers);
		
		List<UserDto> actualUsers = userController.getAllUsers();
		
		//assertion
		assertThat(actualUsers).isEqualTo(expectedUsers);
		
	}
	
	@Test
	public void whenGetUserById_thenReturnUserById() {
		
		UserDto expectedUserDto = UserDto.builder().id(1L).build();
		
		ResponseEntity<UserDto> resp = new ResponseEntity<UserDto>(expectedUserDto,HttpStatus.OK);				
		
		when(userService.getUserById(1L)).thenReturn(expectedUserDto);
		
		ResponseEntity<UserDto> actualUserDto = userController.getUserById(1L);
		
		assertThat(actualUserDto).isEqualTo(resp);
	}
	
	@Test
	public void whenUpdateUser_thenReturnUpdatedUserResponse() {
		
		UserDto expectedUserDto = UserDto.builder().id(1L).build();
		
		ResponseEntity<UserDto> resp = new ResponseEntity<UserDto>(expectedUserDto,HttpStatus.OK);				
		
		when(userService.updateUser(expectedUserDto, 1L)).thenReturn(expectedUserDto);
		
		ResponseEntity<UserDto> actualUserDto = userController.updateUser(expectedUserDto, 1L);
		
		assertThat(actualUserDto).isEqualTo(resp);
	}
	
	@Test
	public void whenDeleteUser_thenReturnDeleteUserResponse() {
				
		ResponseEntity<String> expectedResponse = new ResponseEntity<String>("User deleted successfully.",HttpStatus.OK);				
		
		ResponseEntity<String> deleteResponse = userController.deleteUser(1L);
		
		assertThat(deleteResponse).isEqualTo(expectedResponse);
	}

}
