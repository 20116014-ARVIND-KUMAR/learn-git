package com.learnspringboot.blog.services;

import com.learnspringboot.blog.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Integer cateogoryId);

    CategoryDto getCategory(Integer cateogoryId);

    void deleteCategory(Integer cateogoryId);

    List<CategoryDto> getALlCategory();
}
