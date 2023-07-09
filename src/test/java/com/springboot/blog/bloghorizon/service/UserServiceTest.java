package com.springboot.blog.bloghorizon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.springboot.blog.bloghorizon.entity.User;
import com.springboot.blog.bloghorizon.exception.ResourceNotFoundException;
import com.springboot.blog.bloghorizon.payload.UserDto;
import com.springboot.blog.bloghorizon.repository.UserRepository;
import com.springboot.blog.bloghorizon.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	UserServiceImpl underTestService;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	ModelMapper mapper;
	
	@Test
	public void whenFindAll_thenReturnAllUsers() {
		
		UserDto userDto = UserDto.builder().id(1L).build();		
		
		User user = mapper.map(userDto, User.class);
		
		List<User> expectedUsers = java.util.Arrays.asList(user);
		
		when(userRepository.findAll()).thenReturn(expectedUsers);
		
		List<UserDto> actualUsers = underTestService.getAllUsers();
		
		assertThat(actualUsers).isEqualTo(expectedUsers);
	}
	
	
	@Test
	public void whenFindById_thenReturnUserById() {
		
		User user = User.builder().id(1L).build();
		
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		
		UserDto expectedUserDto = mapper.map(user, UserDto.class);
		
		UserDto actualUserDto = underTestService.getUserById(1L);
		
		assertThat(actualUserDto).isEqualTo(expectedUserDto);
	}
	
	@Test
	public void whenFindById_thenThrowResourceNotFoundException_uponInvalidId() {
		
		long userId = 1L;
		
		when(userRepository.findById(userId)).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> underTestService.getUserById(userId));
	}
	
	@Test
	public void whenFindByUserName_thenReturnUserByUserName() {
		
		Optional<User> user = Optional.of(User.builder().username("harry").build());
		
		when(userRepository.findByUsername("harry")).thenReturn(user);
		
		UserDto expectedUserDto = mapper.map(user, UserDto.class);
		
		UserDto actualUserDto = underTestService.getUserByUsername("harry");
		
		assertThat(actualUserDto).isEqualTo(expectedUserDto);
	}
	
	@Test
	public void whenUpdateUser_thenShouldSaveUpdatedUser() {
				
		User user = User.builder().id(1L).build();
		
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		
		when(userRepository.save(user)).thenReturn(user);
		
		UserDto userDto = mapper.map(user, UserDto.class);
		
		userDto = UserDto.builder().id(1L).build();
		
		underTestService.updateUser(userDto, 1L);
		
		verify(userRepository, times(1)).save(user);
		
	}
	
}
