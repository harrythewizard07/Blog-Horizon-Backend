package com.springboot.blog.bloghorizon.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.springboot.blog.bloghorizon.payload.CommentDto;
import com.springboot.blog.bloghorizon.service.CommentService;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {
	
	@InjectMocks
	CommentController commentController;
	
	@Mock
	CommentService commentService;
	
	@Test
	public void whenCreateComment_thenReturnCreatedComment() {
		
		CommentDto expectedCommentDto = CommentDto.builder().id(1L).build();
		
		ResponseEntity<CommentDto> resp = new ResponseEntity<CommentDto>(expectedCommentDto,HttpStatus.CREATED);				
		
		when(commentService.createComment(1L, expectedCommentDto)).thenReturn(expectedCommentDto);
		
		ResponseEntity<CommentDto> actualCommentDto = commentController.createComment(1L, expectedCommentDto);
		
		assertThat(actualCommentDto).isEqualTo(resp);
		
	}

	@Test
	void whenGetCommentByPostId_thenReturnCommentByPostId() {
		CommentDto comment1 = CommentDto.builder().id(1L).build();
		
		CommentDto comment2 = CommentDto.builder().id(1L).build();
		
		List<CommentDto> expectedComments = new ArrayList<>();
		expectedComments.add(comment1);
		expectedComments.add(comment2);
		
		//action
		when(commentService.getCommentByPostId(1L)).thenReturn(expectedComments);
		
		List<CommentDto> actualComments = commentController.getCommentByPostId(1L);
		
		//assertion
		assertThat(actualComments).isEqualTo(expectedComments);
	}
	
	@Test
	public void whenGetCommentById_thenReturnCommentById() {
		
		CommentDto expectedCommentDto = CommentDto.builder().id(1L).build();
		
		ResponseEntity<CommentDto> resp = new ResponseEntity<CommentDto>(expectedCommentDto,HttpStatus.OK);				
		
		when(commentService.getCommentById(1L, 1L)).thenReturn(expectedCommentDto);
		
		ResponseEntity<CommentDto> actualCommentDto = commentController.getCommentById(1L, 1L);
		
		assertThat(actualCommentDto).isEqualTo(resp);
	}
	
	@Test
	public void whenUpdateComment_thenReturnUpdatedCommentResponse() {
		
		CommentDto expectedCommentDto = CommentDto.builder().id(1L).build();
		
		ResponseEntity<CommentDto> resp = new ResponseEntity<CommentDto>(expectedCommentDto,HttpStatus.OK);				
		
		when(commentService.updateComment(1L, 1L, expectedCommentDto)).thenReturn(expectedCommentDto);
		
		ResponseEntity<CommentDto> actualCommentDto = commentController.updateComment(1L, 1L, expectedCommentDto);
		
		assertThat(actualCommentDto).isEqualTo(resp);
	}
	
	@Test
	public void whenDeletePost_thenReturnDeletePostResponse() {
				
		ResponseEntity<String> expectedResponse = new ResponseEntity<String>("Comment Deleted Successfully!",HttpStatus.OK);				
		
		ResponseEntity<String> deleteResponse = commentController.deleteComment(1L, 1L);
		
		assertThat(deleteResponse).isEqualTo(expectedResponse);
	}

}
