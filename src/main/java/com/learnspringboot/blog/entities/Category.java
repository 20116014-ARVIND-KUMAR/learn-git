package com.learnspringboot.blog.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(name = "category_title")
    private String categoryTitle;

    @Column(name = "category_description")
    private String categoryDesc;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Post> posts =new ArrayList<>();

}
