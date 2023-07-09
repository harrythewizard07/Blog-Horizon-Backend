package com.springboot.blog.bloghorizon.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponse {
	
	private String token;
	
	private UserDto userDto;
}
