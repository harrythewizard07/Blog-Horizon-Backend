package com.springboot.blog.bloghorizon.service;

import java.util.List;

import com.springboot.blog.bloghorizon.payload.CategoryDto;

public interface CategoryService {
	
	CategoryDto addCategory(CategoryDto categoryDto);
	
	List<CategoryDto> getAllCategories();
	
	CategoryDto getCategoryById(long categoryId);
	
	CategoryDto updateCategory(CategoryDto categoryDto, long categoryId);
	
	void deleteCategory(long categoryId);
}
