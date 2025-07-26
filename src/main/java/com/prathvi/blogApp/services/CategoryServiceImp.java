package com.prathvi.blogApp.services;

import com.prathvi.blogApp.entities.Category;
import com.prathvi.blogApp.exceptions.ResourceNotFoundException;
import com.prathvi.blogApp.payloads.CategoryDto;
import com.prathvi.blogApp.repositories.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImp implements CategoryService{

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category =this.modelMapper.map(categoryDto,Category.class);
        Category addcategory= categoryRepo.save(category);
        return this.modelMapper.map(addcategory,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategroy(CategoryDto categoryDto, Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","category Id",categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        categoryRepo.save(category);

        return this.modelMapper.map(category,CategoryDto.class);
    }

//    @Override
//    public void deleteCategory(Integer categoryId) {
//        Category category=this.categoryRepo.deleteById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","category Id",categoryId));
//        this.categoryRepo.delete(category);
//    }
    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category Id", categoryId));
        this.categoryRepo.delete(category);
    }


    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category Id", categoryId));
     //   this.categoryRepo.delete(category);
        return this.modelMapper.map(category,CategoryDto.class);
    }
        @Override
        public List<CategoryDto> getCategories() {
            List<Category> categories = categoryRepo.findAll();
            List<CategoryDto> categoryDtos = categories.stream()
                    .map(e -> this.modelMapper.map(e, CategoryDto.class))
                    .collect(Collectors.toList());
            return categoryDtos;
        }

    }
