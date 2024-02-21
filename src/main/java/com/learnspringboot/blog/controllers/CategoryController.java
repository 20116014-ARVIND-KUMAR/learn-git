package com.learnspringboot.blog.controllers;

import com.learnspringboot.blog.payloads.CategoryDto;
import com.learnspringboot.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryService service;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.service = categoryService;
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){

        CategoryDto category = this.service.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
        CategoryDto category = this.service.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer categoryId){
        this.service.deleteCategory(categoryId);

        return new ResponseEntity<>(Map.of("message", "User deleted successfully with id: " + categoryId), HttpStatus.OK);
    }

    @GetMapping("/categories/{catId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId){
        CategoryDto category = this.service.getCategory(catId);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> categoryDtoList = this.service.getALlCategory();

        return new ResponseEntity<>(categoryDtoList, HttpStatus.OK);
    }


}
