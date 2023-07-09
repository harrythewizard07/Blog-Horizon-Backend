package com.springboot.blog.bloghorizon.service;

import java.util.List;

import com.springboot.blog.bloghorizon.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(long postId, CommentDto commentDto);
	
	List<CommentDto> getCommentByPostId(long postId);
	
	CommentDto getCommentById(long postId, long commentId);
	
	CommentDto updateComment(long postId, long commentId, CommentDto commentRequest);
	
	void deleteComment(long postId, long commentId);
}
