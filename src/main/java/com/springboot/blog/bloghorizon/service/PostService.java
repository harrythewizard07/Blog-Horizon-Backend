package com.springboot.blog.bloghorizon.service;

import java.util.List;

import com.springboot.blog.bloghorizon.payload.PostDto;

public interface PostService {
	PostDto createPost(long userId, PostDto postDto);

	List<PostDto> getAllPosts();

	PostDto getPostById(long id);
	
	List<PostDto> getPostByUserId(long userId);
	
	List<PostDto> getPostByCategoryId(long categoryId);
	
	PostDto updatePost(PostDto postDto, long id);
	
	void deletePostById(long id);

}
