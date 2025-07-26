package com.prathvi.blogApp.services;

import com.prathvi.blogApp.payloads.CategoryDto;

import java.util.List;

public interface CategoryService{

    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategroy(CategoryDto categoryDto, Integer categoryId);
    public void deleteCategory(Integer categoryId);
    CategoryDto getCategory(Integer categoryId);
    List<CategoryDto> getCategories();
}
