package com.springboot.blog.bloghorizon.payload;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
	
	private long id;
	
	//Title should not be null or empty
	//Title should have at least 2 characters
	@NotEmpty
	@Size(min = 2, message = "Post title should have at least 2 characters")
	private String title;
	
	//Post description should not be null or empty
	//Post description should have at least 10 characters
	@NotEmpty
	@Size(min = 10, message = "Post description should have atleast 10 characters")
	private String description;
	
	//Post content should not be null or empty
	@NotEmpty
	private String content;
	private Set<CommentDto> comments;
	private UserDto user;
	//private CategoryDto category;	
	private Long categoryId;
}
