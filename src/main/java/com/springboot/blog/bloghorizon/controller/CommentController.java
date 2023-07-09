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

import com.springboot.blog.bloghorizon.payload.CommentDto;
import com.springboot.blog.bloghorizon.service.CommentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/blog")
@CrossOrigin("*")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@SecurityRequirement(name = "JWT Authentication")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/posts/{pid}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value = "pid") long postId,
			@Valid @RequestBody CommentDto commentDto) {

		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}

	@GetMapping("/posts/{pid}/comments")
	public List<CommentDto> getCommentByPostId(@PathVariable(value = "pid") long postId) {
		return commentService.getCommentByPostId(postId);
	}

	@GetMapping("/posts/{pid}/comments/{cid}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "pid") long postId,
			@PathVariable(value = "cid") long commentId) {

		CommentDto commentDto = commentService.getCommentById(postId, commentId);

		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}

	@SecurityRequirement(name = "JWT Authentication")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping("/posts/{pid}/comments/{cid}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "pid") long postId,
			@PathVariable(value = "cid") long commentId, @Valid @RequestBody CommentDto commentDto) {

		CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);

		return new ResponseEntity<>(updatedComment, HttpStatus.OK);
	}

	@SecurityRequirement(name = "JWT Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/posts/{pid}/comments/{cid}")
	public ResponseEntity<String> deleteComment(@PathVariable(value = "pid") long postId,
			@PathVariable(value = "cid") long commentId) {
		
		commentService.deleteComment(postId, commentId);
		
		return new ResponseEntity<>("Comment Deleted Successfully!", HttpStatus.OK);
	}
}
