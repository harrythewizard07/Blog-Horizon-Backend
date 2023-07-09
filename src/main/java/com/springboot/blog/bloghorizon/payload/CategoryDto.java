package com.springboot.blog.bloghorizon.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
	
	private long id;
	
	//category name must not be null or empty
	@NotEmpty(message = "Category name must not be null or empty.")
	private String name;
	
	//category description must not be null or empty
	//Category description must be minimum 10 characters
	@NotEmpty(message = "Category description must not be null or empty.")
	@Size(min = 10, message = "Category description must be minimum 10 characters.")
	private String description;

}
