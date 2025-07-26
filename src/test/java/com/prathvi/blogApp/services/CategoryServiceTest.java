package com.prathvi.blogApp.services;

import com.prathvi.blogApp.entities.Category;
import com.prathvi.blogApp.exceptions.ResourceNotFoundException;
import com.prathvi.blogApp.payloads.CategoryDto;
import com.prathvi.blogApp.repositories.CategoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CategoryServiceTest {



    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    CategoryServiceImp categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory() {

        // Mock input data
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategroyId(1);
        categoryDto.setCategoryTitle("Technology");

        Category category = new Category();
        category.setCategroyId(1);
        category.setCategoryTitle("Technology");

        Category savedCategory = new Category();
        savedCategory.setCategroyId(1);
        savedCategory.setCategoryTitle("Technology");

        CategoryDto savedCategoryDto = new CategoryDto();
        savedCategoryDto.setCategroyId(1);
        savedCategoryDto.setCategoryTitle("Technology");

        // Mock behaviors
        when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
        when(categoryRepo.save(category)).thenReturn(savedCategory);
        when(modelMapper.map(savedCategory, CategoryDto.class)).thenReturn(savedCategoryDto);

        // Call the method to test
        CategoryDto result = categoryService.createCategory(categoryDto);

        // Assertions
        assertNotNull(result);
        assertEquals("Technology", result.getCategoryTitle());
        assertEquals(1, result.getCategroyId());

        // Verify the interactions
        verify(modelMapper, times(1)).map(categoryDto, Category.class);
        verify(categoryRepo, times(1)).save(category);
        verify(modelMapper, times(1)).map(savedCategory, CategoryDto.class);
    }

    @Test
    void testUpdateCategory(){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategroyId(1);
        categoryDto.setCategoryTitle("Technology");

        Category category = new Category();
        category.setCategroyId(1);
        category.setCategoryTitle("Technology");

        Integer categoryId=1;

        when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
        when(categoryRepo.findById(categoryId)).thenReturn(java.util.Optional.of(category));
        when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

        CategoryDto result=categoryService.updateCategroy(categoryDto,categoryId);

        assertNotNull(result);
        assertEquals(categoryDto.getCategoryTitle(), result.getCategoryTitle());
        assertEquals(categoryDto.getCategroyId(), result.getCategroyId());
    }

    @Test
    void testDeleteCategory_Success() {
        // Mock input data
        Integer categoryId = 1;
        Category category = new Category();
        category.setCategroyId(categoryId);
        category.setCategoryTitle("Test Category");

        // Mock behavior
        when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
        doNothing().when(categoryRepo).delete(category);

        // Call the method
        categoryService.deleteCategory(categoryId);

        // Verify interactions
        verify(categoryRepo, times(1)).findById(categoryId);
        verify(categoryRepo, times(1)).delete(category);
    }

    @Test
    void testDeleteCategory_ResourceNotFoundException() {
        // Mock input data
        Integer categoryId = 999;

        // Mock behavior
        when(categoryRepo.findById(categoryId)).thenReturn(Optional.empty());

        // Call the method and assert exception
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> categoryService.deleteCategory(categoryId));

        // Assertions
        assertEquals("Category not found with category Id : 999", exception.getMessage());

        // Verify interactions
        verify(categoryRepo, times(1)).findById(categoryId);
        verify(categoryRepo, never()).delete(any());
    }

    @Test
    public void testGetCategory_Success() {
        // Arrange
        Integer categoryId = 1;
        Category category = new Category();
        category.setCategroyId(categoryId);
        category.setCategoryTitle("Technology");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategroyId(categoryId);
        categoryDto.setCategoryTitle("Technology");

        when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
        when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

        // Act
        CategoryDto result = categoryService.getCategory(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(categoryId, result.getCategroyId());
        assertEquals("Technology", result.getCategoryTitle());
        verify(categoryRepo, times(1)).findById(categoryId);
    }


    @Test
    public void testGetCategory_ResourceNotFoundException() {
        // Arrange
        Integer invalidCategoryId = 999;
        when(categoryRepo.findById(invalidCategoryId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.getCategory(invalidCategoryId)
        );

        assertEquals("Category not found with category Id : 999", exception.getMessage());
        verify(categoryRepo, times(1)).findById(invalidCategoryId);
    }

    @Test
    public void testGetCategories_Success() {
        // Arrange
        List<Category> categories = new ArrayList<>();
        Category category1 = new Category();
        category1.setCategroyId(1);
        category1.setCategoryTitle("Technology");
        categories.add(category1);

        Category category2 = new Category();
        category2.setCategroyId(2);
        category2.setCategoryTitle("Science");
        categories.add(category2);

        when(categoryRepo.findAll()).thenReturn(categories);

        List<CategoryDto> expectedDtos = new ArrayList<>();
        CategoryDto dto1 = new CategoryDto();
        dto1.setCategroyId(1);
        dto1.setCategoryTitle("Technology");
        expectedDtos.add(dto1);

        CategoryDto dto2 = new CategoryDto();
        dto2.setCategroyId(2);
        dto2.setCategoryTitle("Science");
        expectedDtos.add(dto2);

        when(modelMapper.map(category1, CategoryDto.class)).thenReturn(dto1);
        when(modelMapper.map(category2, CategoryDto.class)).thenReturn(dto2);

        // Act
        List<CategoryDto> result = categoryService.getCategories();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Technology", result.get(0).getCategoryTitle());
        assertEquals("Science", result.get(1).getCategoryTitle());
        verify(categoryRepo, times(1)).findAll();
        verify(modelMapper, times(2)).map(any(Category.class), eq(CategoryDto.class));
    }

}


