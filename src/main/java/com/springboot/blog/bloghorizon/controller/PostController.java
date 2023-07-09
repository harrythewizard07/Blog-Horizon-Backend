package com.springboot.blog.bloghorizon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.bloghorizon.payload.PostDto;
import com.springboot.blog.bloghorizon.service.PostService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/blog")
@CrossOrigin("*")
public class PostController {

	@Autowired
	private PostService postService;

	// create posts rest API
	@SecurityRequirement(name = "JWT Authentication")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@PostMapping("/users/{uid}/posts")
	public ResponseEntity<PostDto> createPost(@PathVariable(value = "uid") long userId, @Valid @RequestBody PostDto postDto) {

		return new ResponseEntity<>(postService.createPost(userId,postDto), HttpStatus.CREATED);
	}

	// get all posts rest API
	@GetMapping("/posts")
	public List<PostDto> getAllPosts() {
		return postService.getAllPosts();
	}

	// get posts by id rest API
	@GetMapping("/posts/{pid}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name = "pid") long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	// get posts by user id rest API
	@GetMapping("/posts/users/{uid}")
	public List<PostDto> getPostByUserId(@PathVariable(value = "uid") long userId){
		return postService.getPostByUserId(userId);
	}
	
	// get posts by category id rest API
	@GetMapping("/posts/categories/{categoryId}")
	public List<PostDto> getPostByCategoryId(@PathVariable(value = "categoryId") long categoryId) {
		return postService.getPostByCategoryId(categoryId);
	}

	// update post by id rest API
	@SecurityRequirement(name = "JWT Authentication")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping("/posts/{pid}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "pid") long id) {

		PostDto postResponse = postService.updatePost(postDto, id);

		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}

	// delete post rest API
	@SecurityRequirement(name = "JWT Authentication")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Post Deleted Successfully"),
						@ApiResponse(responseCode = "401", description = "Access Denied, User Unauthorized")})
	@DeleteMapping("/posts/{pid}")
	public ResponseEntity<String> deletePost(@PathVariable(name = "pid") long id) {

		postService.deletePostById(id);

		return new ResponseEntity<>("Post deleted successfully.", HttpStatus.OK);
	}
}
