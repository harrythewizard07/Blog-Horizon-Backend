package com.springboot.blog.bloghorizon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.springboot.blog.bloghorizon.entity.Comment;
import com.springboot.blog.bloghorizon.entity.Post;
import com.springboot.blog.bloghorizon.exception.ResourceNotFoundException;
import com.springboot.blog.bloghorizon.payload.CommentDto;
import com.springboot.blog.bloghorizon.repository.CommentRepository;
import com.springboot.blog.bloghorizon.repository.PostRepository;
import com.springboot.blog.bloghorizon.service.impl.CommentServiceImpl;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

	@InjectMocks
	CommentServiceImpl underTestService;
	
	@Mock
	CommentRepository commentRepository;
	
	@Mock
	PostRepository postRepository;
	
	@Mock
	ModelMapper mapper;
	
	@Test
	public void whenFindById_thenReturnCommentById() {
		
		Post post = Post.builder().id(1L).build();
		Comment comment = Comment.builder().id(1L).post(post).build();
		
		when(postRepository.findById(1L)).thenReturn(Optional.of(post));
		when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
		
		CommentDto expectedCommentDto = mapper.map(comment, CommentDto.class);
		
		CommentDto actualCommentDto = underTestService.getCommentById(1L, 1L);
		
		assertThat(actualCommentDto).isEqualTo(expectedCommentDto);
	}
	
	@Test
	public void whenFindByPostId_thenReturnCommentByPostId() {
		
		CommentDto commentDto = CommentDto.builder().id(1L).build();
		
		Comment comment = mapper.map(commentDto, Comment.class);
		Post post = Post.builder().id(1L).build();
		
		comment = Comment.builder().id(1L).post(post).build();
		
		CommentDto expectedCommentDto = mapper.map(comment, CommentDto.class);
		
		List<Comment> expectedComments = java.util.Arrays.asList(comment);
		List<CommentDto> expectedCommentsList = java.util.Arrays.asList(expectedCommentDto);
		
		when(commentRepository.findByPostId(post.getId())).thenReturn(expectedComments);
		
		List<CommentDto> actualComments = underTestService.getCommentByPostId(post.getId());
		
		assertThat(actualComments).isEqualTo(expectedCommentsList);
	}
	
	@Test
	public void whenFindById_thenThrowResourceNotFoundException_uponInvalidId() {
		
		long commentId = 1L;
		long postId = 1L;
		
		when(postRepository.findById(postId)).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> underTestService.getCommentById(postId, commentId));
	}
	
	@Test
	public void whenUpdateComment_thenShouldSaveUpdatedComment() {
		
		Post post = Post.builder().id(1L).build();
		Comment comment = Comment.builder().id(1L).post(post).build();
		
		when(postRepository.findById(1L)).thenReturn(Optional.of(post));
		when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
		
		when(commentRepository.save(comment)).thenReturn(comment);
		
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
		
		commentDto = CommentDto.builder().build();
		
		underTestService.updateComment(1L, 1L, commentDto);
		
		verify(commentRepository, times(1)).save(comment);
		
	}
	
}
