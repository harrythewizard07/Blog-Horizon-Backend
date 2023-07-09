package com.springboot.blog.bloghorizon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.bloghorizon.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
