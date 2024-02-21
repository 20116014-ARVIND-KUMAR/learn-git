package com.learnspringboot.blog;

import com.learnspringboot.blog.entities.Category;
import com.learnspringboot.blog.repositories.CategoryRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;


@DataJpaTest
public class CategoryTest {

    @Autowired
    CategoryRepo categoryRepo;

    @Test
    public void setCategoryRepo(){

        Category category = Category.builder().categoryTitle("Hello").categoryDesc("World").build();

        Category savedCat = this.categoryRepo.save(category);
        Category savedCat1 = this.categoryRepo.save(category);

        System.out.println(savedCat);
        System.out.println(savedCat1);
    }
}
