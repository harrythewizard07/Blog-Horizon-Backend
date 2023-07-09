package com.springboot.blog.bloghorizon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.bloghorizon.payload.UserDto;
import com.springboot.blog.bloghorizon.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/blog/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<UserDto> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/{uid}")
	public ResponseEntity<UserDto> getUserById(@PathVariable(name = "uid") long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	// update user by id rest api
	@SecurityRequirement(name = "JWT Authentication")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@PutMapping("/{uid}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable(name = "uid") long id) {

		UserDto userResponse = userService.updateUser(userDto, id);

		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	// delete post rest api
	@SecurityRequirement(name = "JWT Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{uid}")
	public ResponseEntity<String> deleteUser(@PathVariable(name = "uid") long id) {

		userService.deleteUserByid(id);

		return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
	}
}
