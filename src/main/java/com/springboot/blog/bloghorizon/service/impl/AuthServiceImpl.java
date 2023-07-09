package com.springboot.blog.bloghorizon.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.blog.bloghorizon.entity.Role;
import com.springboot.blog.bloghorizon.entity.User;
import com.springboot.blog.bloghorizon.exception.BlogAPIException;
import com.springboot.blog.bloghorizon.payload.LoginDto;
import com.springboot.blog.bloghorizon.payload.RegisterDto;
import com.springboot.blog.bloghorizon.repository.RoleRepository;
import com.springboot.blog.bloghorizon.repository.UserRepository;
import com.springboot.blog.bloghorizon.security.JwtTokenProvider;
import com.springboot.blog.bloghorizon.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public String login(LoginDto loginDto) {

		Authentication authentication;

		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Incorrect username or password");
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		return token;
	}

	@Override
	public String register(RegisterDto registerDto) {

		// Checking if username exists in the database
		if (userRepository.existsByUsername(registerDto.getUsername())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already in use!");
		}

		// Checking if the email id exists in the database
		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email Id is already in use!");
		}

		User user = new User();

		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		Set<Role> roles = new HashSet<>();

		Role userRole = roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		user.setRoles(roles);

		userRepository.save(user);

		return "User registered successfully";
	}

}
