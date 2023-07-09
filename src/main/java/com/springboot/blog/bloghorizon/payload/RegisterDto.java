package com.springboot.blog.bloghorizon.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

	// name must not be null or empty
	@NotEmpty(message = "Name must not be null or empty.")
	private String name;

	// username must not be null or empty
	@NotEmpty(message = "Username must not be null or empty")
	private String username;

	// email should not be null or empty
	// email field validation
	@NotEmpty(message = "Email must not be null or empty.")
	@Email
	private String email;

	// Password must not be null or empty
	// Password must contain at least 6 characters
	@NotEmpty(message = "Password must not be null or empty")
	@Size(min = 6, message = "Password must contain atleast 6 characters")
	private String password;
}
