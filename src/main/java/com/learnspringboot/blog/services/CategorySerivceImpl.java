package com.learnspringboot.blog.services;

import com.learnspringboot.blog.entities.Category;
import com.learnspringboot.blog.exceptions.ResourceNotFoundException;
import com.learnspringboot.blog.payloads.CategoryDto;
import com.learnspringboot.blog.repositories.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorySerivceImpl implements CategoryService{

    private CategoryRepo categoryRepo;
    private ModelMapper mapper;

    @Autowired
    public CategorySerivceImpl(CategoryRepo categoryRepo, ModelMapper mapper){
        this.categoryRepo = categoryRepo;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = this.mapper.map(categoryDto, Category.class);
        Category categoryAdded = this. categoryRepo.save(category);

        return this.mapper.map(categoryAdded, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

        Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDesc(categoryDto.getCategoryDesc());

        Category updatedCatory = this.categoryRepo.save(cat);

        return this.mapper.map(updatedCatory, CategoryDto.class);

    }

    @Override
    public CategoryDto getCategory(Integer cateogoryId) {

        Category cat = this.categoryRepo.findById(cateogoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", cateogoryId));

        return this.mapper.map(cat, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer cateogoryId) {
        Category cat = this.categoryRepo.findById(cateogoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", cateogoryId));

        this.categoryRepo.delete(cat);
    }

    @Override
    public List<CategoryDto> getALlCategory() {

        List<Category> categoryList = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtoList = categoryList.stream().map((cat)->this.mapper.map(cat, CategoryDto.class)).toList();

        return categoryDtoList;
    }
}
