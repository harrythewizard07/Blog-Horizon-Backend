package com.springboot.blog.bloghorizon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.bloghorizon.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	List<Post> findByUserId(long userId);
	
	List<Post> findByCategoryId(long categoryId);
}
