package com.springboot.blog.bloghorizon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.bloghorizon.payload.JwtAuthResponse;
import com.springboot.blog.bloghorizon.payload.LoginDto;
import com.springboot.blog.bloghorizon.payload.RegisterDto;
import com.springboot.blog.bloghorizon.payload.UserDto;
import com.springboot.blog.bloghorizon.service.AuthService;
import com.springboot.blog.bloghorizon.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/blog/auth")
@CrossOrigin("*")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserService userService;

	// Building login rest APi
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
		String token = authService.login(loginDto);
		
		UserDto userDto = userService.getUserByUsername(loginDto.getUsername());
		
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setToken(token);
		jwtAuthResponse.setUserDto(userDto);
		
		return ResponseEntity.ok(jwtAuthResponse);
	}

	// Building register rest API
	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
		String response = authService.register(registerDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
