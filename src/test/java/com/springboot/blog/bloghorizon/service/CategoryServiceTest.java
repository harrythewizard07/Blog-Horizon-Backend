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
import com.springboot.blog.bloghorizon.exception.ResourceNotFoundException;
import com.springboot.blog.bloghorizon.payload.CategoryDto;
import com.springboot.blog.bloghorizon.repository.CategoryRepository;
import com.springboot.blog.bloghorizon.service.impl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

	@InjectMocks
	CategoryServiceImpl underTestService;
	
	@Mock
	CategoryRepository categoryRepository;
	
	@Mock
	ModelMapper mapper;
	
	@Test
	public void whenFindAll_thenReturnAllCategories() {
		
		CategoryDto categoryDto = CategoryDto.builder().id(1L).build();		
		
		Category category = mapper.map(categoryDto, Category.class);
		
		List<Category> expectedCategories = java.util.Arrays.asList(category);
		
		when(categoryRepository.findAll()).thenReturn(expectedCategories);
		
		List<CategoryDto> actualCategories = underTestService.getAllCategories();
		
		assertThat(actualCategories).isEqualTo(expectedCategories);
	}

	@Test
	public void whenFindById_thenReturnCategoryById() {
		
		Category category = Category.builder().id(1L).build();
		
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
		
		CategoryDto expectedCategoryDto = mapper.map(category, CategoryDto.class);
		
		CategoryDto actualCategoryDto = underTestService.getCategoryById(1L);
		
		assertThat(actualCategoryDto).isEqualTo(expectedCategoryDto);
	}
	
	@Test
	public void whenFindById_thenThrowResourceNotFoundException_uponInvalidId() {
		
		long categoryId = 1L;
		
		when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> underTestService.getCategoryById(categoryId));
	}
	
	@Test
	public void whenUpdateCategory_thenShouldSaveUpdatedCategory() {
				
		Category category = Category.builder().id(1L).build();
		
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
		
		when(categoryRepository.save(category)).thenReturn(category);
		
		CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
		
		categoryDto = CategoryDto.builder().id(1L).build();
		
		underTestService.updateCategory(categoryDto, 1L);
		
		verify(categoryRepository, times(1)).save(category);
		
	}
}
