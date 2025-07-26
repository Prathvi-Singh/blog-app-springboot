package com.prathvi.blogApp.controllers;

import com.prathvi.blogApp.payloads.ApiResponse;
import com.prathvi.blogApp.payloads.CategoryDto;
import com.prathvi.blogApp.payloads.PostDto;
import com.prathvi.blogApp.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory() {
        // Arrange
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryTitle("Technology");

        when(categoryService.createCategory(any(CategoryDto.class))).thenReturn(categoryDto);

        // Act
        ResponseEntity<ApiResponse> response = categoryController.createCategory(categoryDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().success);
        assertEquals("Successfully Added", response.getBody().message);

        verify(categoryService, times(1)).createCategory(any(CategoryDto.class));
    }

    @Test
    public void testGetCategoryById() {
        // Arrange
        Integer categoryId = 1;
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryTitle("Technology");

        when(categoryService.getCategory(categoryId)).thenReturn(categoryDto);

        // Act
        ResponseEntity<CategoryDto> response = categoryController.getCategoryById(categoryId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Technology", response.getBody().getCategoryTitle());

        verify(categoryService, times(1)).getCategory(categoryId);
    }

    @Test
    public void testGetCategory() {
        // Arrange
        List<CategoryDto> categories = Arrays.asList(new CategoryDto(), new CategoryDto());
        when(categoryService.getCategories()).thenReturn(categories);

        // Act
        ResponseEntity<List<CategoryDto>> response = categoryController.getCategory();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        verify(categoryService, times(1)).getCategories();
    }

    @Test
    public void testDeleteCategory() {
        // Arrange
        Integer categoryId = 1;

        // Act
        ResponseEntity<ApiResponse> response = categoryController.deleteCategory(categoryId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().success);
        assertEquals("Successfully Deleted", response.getBody().message);

        verify(categoryService, times(1)).deleteCategory(categoryId);
    }

    @Test
    public void testUpdateCategory() {
        // Arrange
        Integer categoryId = 1;
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategroyId(1);
        categoryDto.setCategoryTitle("Updated Category");

        when(categoryService.updateCategroy(categoryDto,categoryId)).thenReturn(categoryDto);

        // Act
        ResponseEntity<ApiResponse> response = categoryController.updateCategory(categoryDto, categoryId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().success);
        assertEquals("Successfully Updated", response.getBody().message);

        verify(categoryService, times(1)).updateCategroy(categoryDto, categoryId);
    }


}
