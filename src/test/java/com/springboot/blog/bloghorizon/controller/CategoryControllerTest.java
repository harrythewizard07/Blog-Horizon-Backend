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

import com.springboot.blog.bloghorizon.payload.CategoryDto;
import com.springboot.blog.bloghorizon.service.CategoryService;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

	@InjectMocks
	CategoryController categoryController;

	@Mock
	CategoryService categoryService;
	
	@Test
	public void whenAddCategory_thenReturnAddedCategory() {
		
		CategoryDto expectedCategoryDto = CategoryDto.builder().id(1L).build();
		
		ResponseEntity<CategoryDto> resp = new ResponseEntity<CategoryDto>(expectedCategoryDto,HttpStatus.CREATED);				
		
		when(categoryService.addCategory(expectedCategoryDto)).thenReturn(expectedCategoryDto);
		
		ResponseEntity<CategoryDto> actualCategoryDto = categoryController.addCategory(expectedCategoryDto);
		
		assertThat(actualCategoryDto).isEqualTo(resp);
		
	}
	
	@Test
	public void whenGetAllCategories_thenReturnAllCategories() {
		CategoryDto category1 = CategoryDto.builder().id(1L).build();
		
		CategoryDto category2 = CategoryDto.builder().id(1L).build();
		
		List<CategoryDto> expectedCategories = new ArrayList<>();
		expectedCategories.add(category1);
		expectedCategories.add(category2);
		
		//action
		when(categoryService.getAllCategories()).thenReturn(expectedCategories);
		
		List<CategoryDto> actualCategories = categoryController.getAllCategories();
		
		//assertion
		assertThat(actualCategories).isEqualTo(expectedCategories);
		
	}
	
	@Test
	public void whenGetCategoryById_thenReturnCategoryById() {
		
		CategoryDto expectedCategoryDto = CategoryDto.builder().id(1L).build();
		
		ResponseEntity<CategoryDto> resp = new ResponseEntity<CategoryDto>(expectedCategoryDto,HttpStatus.OK);				
		
		when(categoryService.getCategoryById(1L)).thenReturn(expectedCategoryDto);
		
		ResponseEntity<CategoryDto> actualCategoryDto = categoryController.getCategoryById(1L);
		
		assertThat(actualCategoryDto).isEqualTo(resp);
	}
	
	@Test
	public void whenUpdateCategory_thenReturnUpdatedCategoryResponse() {
		
		CategoryDto expectedCategoryDto = CategoryDto.builder().id(1L).build();
		
		ResponseEntity<CategoryDto> resp = new ResponseEntity<CategoryDto>(expectedCategoryDto,HttpStatus.OK);				
		
		when(categoryService.updateCategory(expectedCategoryDto, 1L)).thenReturn(expectedCategoryDto);
		
		ResponseEntity<CategoryDto> actualCategoryDto = categoryController.updateCategory(expectedCategoryDto, 1L);
		
		assertThat(actualCategoryDto).isEqualTo(resp);
	}
	
	@Test
	public void whenDeleteCategory_thenReturnDeleteCategoryResponse() {
				
		ResponseEntity<String> expectedResponse = new ResponseEntity<String>("Category deleted successfully.",HttpStatus.OK);				
		
		ResponseEntity<String> deleteResponse = categoryController.deleteCategory(1L);
		
		assertThat(deleteResponse).isEqualTo(expectedResponse);
	}

}
