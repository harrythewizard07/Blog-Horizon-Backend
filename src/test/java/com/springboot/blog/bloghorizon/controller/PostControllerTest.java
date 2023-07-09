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

import com.springboot.blog.bloghorizon.payload.PostDto;
import com.springboot.blog.bloghorizon.service.PostService;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

	@InjectMocks
	PostController postController;

	@Mock
	PostService postService;
	
	@Test
	public void whenCreatePost_thenReturnCreatedPost() {
		
		PostDto expectedPostDto = PostDto.builder().id(1L).build();
		
		ResponseEntity<PostDto> resp = new ResponseEntity<PostDto>(expectedPostDto,HttpStatus.CREATED);				
		
		when(postService.createPost(1L, expectedPostDto)).thenReturn(expectedPostDto);
		
		ResponseEntity<PostDto> actualPostDto = postController.createPost(1L, expectedPostDto);
		
		assertThat(actualPostDto).isEqualTo(resp);
		
	}

	@Test
	public void whenGetAllPosts_thenReturnAllPosts() {
		PostDto post1 = PostDto.builder().id(1L).build();
		
		PostDto post2 = PostDto.builder().id(2L).build();
		
		List<PostDto> expectedPosts = new ArrayList<>();
		expectedPosts.add(post1);
		expectedPosts.add(post2);
		
		//action
		when(postService.getAllPosts()).thenReturn(expectedPosts);
		
		List<PostDto> actualPosts = postController.getAllPosts();
		
		//assertion
		assertThat(actualPosts).isEqualTo(expectedPosts);
		
	}
	
	@Test
	public void whenGetPostById_thenReturnPostById() {
		
		PostDto expectedPostDto = PostDto.builder().id(1L).build();
		
		ResponseEntity<PostDto> resp = new ResponseEntity<PostDto>(expectedPostDto,HttpStatus.OK);				
		
		when(postService.getPostById(1L)).thenReturn(expectedPostDto);
		
		ResponseEntity<PostDto> actualPostDto = postController.getPostById(1L);
		
		assertThat(actualPostDto).isEqualTo(resp);
	}
	
	@Test
	public void whenGetPostByUserId_thenReturnPostByUserId() {
		
		PostDto postDto = PostDto.builder().id(1L).build();
		
		List<PostDto> expectedPosts = new ArrayList<>();
		expectedPosts.add(postDto);
		
		when(postService.getPostByUserId(1L)).thenReturn(expectedPosts);
		
		List<PostDto> actualPosts = postController.getPostByUserId(1L);
		
		assertThat(actualPosts).isEqualTo(expectedPosts);
	}
	
	@Test
	public void whenGetPostByCategoryId_thenReturnPostByCategoryId() {
		
		PostDto postDto = PostDto.builder().id(1L).build();
		
		List<PostDto> expectedPosts = new ArrayList<>();
		expectedPosts.add(postDto);
		
		when(postService.getPostByCategoryId(1L)).thenReturn(expectedPosts);
		
		List<PostDto> actualPosts = postController.getPostByCategoryId(1L);
		
		assertThat(actualPosts).isEqualTo(expectedPosts);
	}
	
	@Test
	public void whenUpdatePost_thenReturnUpdatedPostResponse() {
		
		PostDto expectedPostDto = PostDto.builder().id(1L).build();
		
		ResponseEntity<PostDto> resp = new ResponseEntity<PostDto>(expectedPostDto,HttpStatus.OK);				
		
		when(postService.updatePost(expectedPostDto, 1L)).thenReturn(expectedPostDto);
		
		ResponseEntity<PostDto> actualPostDto = postController.updatePost(expectedPostDto, 1L);
		
		assertThat(actualPostDto).isEqualTo(resp);
	}
	
	@Test
	public void whenDeletePost_thenReturnDeletePostResponse() {
				
		ResponseEntity<String> expectedResponse = new ResponseEntity<String>("Post deleted successfully.",HttpStatus.OK);				
		
		ResponseEntity<String> deleteResponse = postController.deletePost(1L);
		
		assertThat(deleteResponse).isEqualTo(expectedResponse);
	}

}
