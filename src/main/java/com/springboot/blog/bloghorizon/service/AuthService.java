package com.springboot.blog.bloghorizon.service;

import com.springboot.blog.bloghorizon.payload.LoginDto;
import com.springboot.blog.bloghorizon.payload.RegisterDto;

public interface AuthService {
	
	String login(LoginDto loginDto);
	
	String register(RegisterDto registerDto);
}
