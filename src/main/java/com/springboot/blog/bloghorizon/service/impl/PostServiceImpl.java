package com.springboot.blog.bloghorizon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.bloghorizon.entity.Category;
import com.springboot.blog.bloghorizon.entity.Post;
import com.springboot.blog.bloghorizon.entity.User;
import com.springboot.blog.bloghorizon.exception.ResourceNotFoundException;
import com.springboot.blog.bloghorizon.payload.PostDto;
import com.springboot.blog.bloghorizon.repository.CategoryRepository;
import com.springboot.blog.bloghorizon.repository.PostRepository;
import com.springboot.blog.bloghorizon.repository.UserRepository;
import com.springboot.blog.bloghorizon.service.PostService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public PostDto createPost(long userId, PostDto postDto) {

		// converting DTO to Entity
		Post post = mapToEntity(postDto);

		// retrieve user entity by id
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.toString(userId)));

		// set user to post entity;
		post.setUser(user);

		// finding category by id
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", Long.toString(postDto.getCategoryId())));

		// adding the category to post entity
		post.setCategory(category);

		// saving post entity to the database
		Post newPost = postRepository.save(post);
		
		log.info("Created a new post successfully.");

		return mapToDto(newPost);
	}

	@Override
	public List<PostDto> getAllPosts() {

		List<Post> posts = postRepository.findAll();
		
		log.info("Fetched data for all posts successfully.");
		
		return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
	}

	@Override
	public PostDto getPostById(long id) {

		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));
		
		log.info("Fetched data for a single post successfully.");
		
		return mapToDto(post);
	}

	@Override
	public List<PostDto> getPostByUserId(long userId) {

		// This is done so that the rest api throws exception if the user does not exist
		userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", Long.toString(userId)));

		// Retrieving posts by userid
		List<Post> posts = postRepository.findByUserId(userId);
		
		log.info("Fetched data for a post with a given user id successfully.");

		// converting the list of post entities to a list of post dto and returning them
		return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
	}

	@Override
	public List<PostDto> getPostByCategoryId(long categoryId) {

		// This is done so that the rest api throws exception if the category does not exist
		categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", Long.toString(categoryId)));

		// Getting posts by category id
		List<Post> posts = postRepository.findByCategoryId(categoryId);
		
		log.info("Fetched data for a post with a given category id successfully.");

		// converting the list of post entities to a list of post dto and returning them
		return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {

		// get post by id from the database
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));

		// getting category by id from the database
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", Long.toString(id)));

		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		post.setCategory(category);

		Post updatedPost = postRepository.save(post);
		
		log.info("Updated post data successfully.");

		return mapToDto(updatedPost);
	}

	@Override
	public void deletePostById(long id) {

		// get post by id from the database
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));
		
		log.info("Deleted post data successfully.");
		
		postRepository.delete(post);
	}

	// Converting Entity to DTO
	private PostDto mapToDto(Post post) {

		PostDto postDto = mapper.map(post, PostDto.class);

		return postDto;
	}

	// Converting DTO to Entity
	private Post mapToEntity(PostDto postDto) {

		Post post = mapper.map(postDto, Post.class);

		return post;
	}

}
