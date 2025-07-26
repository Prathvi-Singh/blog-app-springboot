package com.prathvi.blogApp.controllers;
import com.prathvi.blogApp.payloads.ApiResponse;
import com.prathvi.blogApp.payloads.CategoryDto;
import com.prathvi.blogApp.repositories.CategoryRepo;
import com.prathvi.blogApp.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody CategoryDto categoryDto){
         CategoryDto createCategory =this.categoryService.createCategory(categoryDto);
         System.out.println(createCategory);
         return new ResponseEntity<>(new ApiResponse("Successfully Added",true ),HttpStatus.CREATED);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer Id){
        return new ResponseEntity<CategoryDto>(categoryService.getCategory(Id),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategory(){
        return new ResponseEntity<List<CategoryDto>>(categoryService.getCategories() , HttpStatus.OK);
    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer Id){
        categoryService.deleteCategory(Id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully Deleted",true),HttpStatus.OK);
    }

    @PutMapping("/update/{Id}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer Id){
       categoryService.updateCategroy(categoryDto,Id);
       return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully Updated",true),HttpStatus.OK);
    }

}
