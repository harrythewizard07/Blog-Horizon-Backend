package com.springboot.blog.bloghorizon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.bloghorizon.payload.CategoryDto;
import com.springboot.blog.bloghorizon.service.CategoryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/blog/categories")
@CrossOrigin("*")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@SecurityRequirement(name = "JWT Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) {

		return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
	}

	@GetMapping
	public List<CategoryDto> getAllCategories() {

		return categoryService.getAllCategories();
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(value = "categoryId") long categoryId) {

		return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
	}

	@SecurityRequirement(name = "JWT Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable(value = "categoryId") long categoryId) {

		CategoryDto categoryResponse = categoryService.updateCategory(categoryDto, categoryId);

		return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
	}
	
	@SecurityRequirement(name = "JWT Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable(value = "categoryId") long categoryId) {
		categoryService.deleteCategory(categoryId);
		
		return new ResponseEntity<>("Category deleted successfully.", HttpStatus.OK);
	}
}
