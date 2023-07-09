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

import com.springboot.blog.bloghorizon.entity.Category;
import com.springboot.blog.bloghorizon.entity.Post;
import com.springboot.blog.bloghorizon.exception.ResourceNotFoundException;
import com.springboot.blog.bloghorizon.payload.PostDto;
import com.springboot.blog.bloghorizon.repository.CategoryRepository;
import com.springboot.blog.bloghorizon.repository.PostRepository;
import com.springboot.blog.bloghorizon.service.impl.PostServiceImpl;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

	@InjectMocks
	PostServiceImpl underTestService;

	@Mock
	PostRepository postRepository;
	
	@Mock
	CategoryRepository categoryRepository;
	
	@Mock
	ModelMapper mapper;

	@Test
	public void whenFindAll_thenReturnAllPosts() {

		// given
		PostDto postDto = PostDto.builder().id(1L).build();
		
		Post post = mapper.map(postDto, Post.class);
		
		List<Post> expectedPosts=java.util.Arrays.asList(post);
		
		//when
		when(postRepository.findAll()).thenReturn(expectedPosts);	
		
		List<PostDto> actualPost=underTestService.getAllPosts();

		//then
		assertThat(actualPost).isEqualTo(expectedPosts);		
	}
	
	@Test
	public void whenFindById_thenReturnPostById() {
		
		Post post = Post.builder().id(1L).build();
		
		when(postRepository.findById(1L)).thenReturn(Optional.of(post));
		
		PostDto expectedPostDto = mapper.map(post, PostDto.class);
		
		PostDto actualPostDto = underTestService.getPostById(1L);
		
		assertThat(actualPostDto).isEqualTo(expectedPostDto);
	}
	
	@Test
	public void whenFindById_thenThrowResourceNotFoundException_uponInvalidId() {
		
		long postId = 1L;
		
		when(postRepository.findById(postId)).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> underTestService.getPostById(postId));
	}
	
	@Test
	public void whenUpdatePost_thenShouldSaveUpdatedPost() {
				
		Post post = Post.builder().id(1L).build();
		Category category = Category.builder().id(1L).build();
		
		when(postRepository.findById(1L)).thenReturn(Optional.of(post));
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
		
		when(postRepository.save(post)).thenReturn(post);
		
		PostDto postDto = mapper.map(post, PostDto.class);
		
		postDto = PostDto.builder().categoryId(1L).build();
		
		underTestService.updatePost(postDto, 1L);
		
		verify(postRepository, times(1)).save(post);
	}

}
