package com.learnspringboot.blog.payloads;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    private Integer categoryId;

    @NotEmpty(message = "title shouldn't be empty")
    private String categoryTitle;

    @NotEmpty
    @Size(min = 10, message = "Description should be of min of 10 length")
    private String categoryDesc;

}
