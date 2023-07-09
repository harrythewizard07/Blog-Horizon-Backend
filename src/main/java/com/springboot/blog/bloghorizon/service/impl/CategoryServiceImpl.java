package com.springboot.blog.bloghorizon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.bloghorizon.entity.Category;
import com.springboot.blog.bloghorizon.exception.ResourceNotFoundException;
import com.springboot.blog.bloghorizon.payload.CategoryDto;
import com.springboot.blog.bloghorizon.repository.CategoryRepository;
import com.springboot.blog.bloghorizon.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {

		// converting dto to entity
		Category category = mapToEntity(categoryDto);

		categoryRepository.save(category);

		log.info("Added a new category successfully.");

		return mapToDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategories() {

		List<Category> categories = categoryRepository.findAll();

		log.info("Fetched data for all categories successfully.");

		return categories.stream().map(category -> mapToDto(category)).collect(Collectors.toList());
	}

	@Override
	public CategoryDto getCategoryById(long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", Long.toString(categoryId)));
		
		log.info("Fetched data for a single category successfully.");
		
		return mapToDto(category);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", Long.toString(categoryId)));

		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());

		Category updatedCategory = categoryRepository.save(category);
		
		log.info("Updated category data successfully.");

		return mapToDto(updatedCategory);
	}

	@Override
	public void deleteCategory(long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", Long.toString(categoryId)));

		log.info("Deleted category data successfully.");
		
		categoryRepository.delete(category);
	}

	// Converting Entity to DTO
	private CategoryDto mapToDto(Category category) {

		CategoryDto categoryDto = mapper.map(category, CategoryDto.class);

		return categoryDto;
	}

	// Converting DTO to Entity
	private Category mapToEntity(CategoryDto categoryDto) {

		Category category = mapper.map(categoryDto, Category.class);

		return category;
	}

}
