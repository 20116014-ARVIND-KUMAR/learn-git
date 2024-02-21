package com.learnspringboot.blog.services;

import com.learnspringboot.blog.entities.Category;
import com.learnspringboot.blog.entities.Post;
import com.learnspringboot.blog.entities.User;
import com.learnspringboot.blog.exceptions.ResourceNotFoundException;
import com.learnspringboot.blog.payloads.PostDto;
import com.learnspringboot.blog.payloads.PostResponse;
import com.learnspringboot.blog.repositories.CategoryRepo;
import com.learnspringboot.blog.repositories.PostRepo;
import com.learnspringboot.blog.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    private PostRepo postRepo;
    private ModelMapper mapper;

    private UserRepo userRepo;
    private CategoryRepo categoryRepo;

    @Autowired
    public PostServiceImpl(PostRepo postRepo, ModelMapper mapper, UserRepo user, CategoryRepo category){

        this.mapper = mapper;
        this.postRepo=postRepo;
        this.userRepo= user;
        this.categoryRepo=category;
    }

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        Post post = this.mapper.map(postDto, Post.class);
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "UserId", userId));
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","CategoryId", categoryId));

        post.setUser(user);
        post.setCategory(category);
        post.setAddedDate(new Date());
        post.setImageName("default.png");

        Post post1= this.postRepo.save(post);

        return this.mapper.map(post1,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = this.postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));

        post.setPostTitle(postDto.getPostTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = this.postRepo.save(post);

        return this.mapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {

        Post post = this.postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));

        this.postRepo.delete(post);
    }

    @Override
    public PostDto getPostById(Integer postId) {

        Post post = this.postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));

        return this.mapper.map(post, PostDto.class);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber , Integer pageSize, String fieldValue, String sortDir) {

        Sort sort= sortDir.equalsIgnoreCase("asc")?Sort.by(fieldValue).ascending():Sort.by(fieldValue).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> posts = this.postRepo.findAll(p);

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(posts.getContent()
                                      .stream().map(post -> this.mapper.map(post, PostDto.class)).toList());

        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLastPage(posts.isLast());

        return postResponse;
    }

    @Override
    public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {

        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","CategoryId", categoryId));

        Pageable p = PageRequest.of(pageNumber, pageSize);

        Page<Post> posts = this.postRepo.findAllByCategory(category, p);

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(posts.getContent()
                .stream().map(post -> this.mapper.map(post, PostDto.class)).toList());

        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLastPage(posts.isLast());


        return postResponse;

    }

    @Override
    public PostResponse getPostsByUser(Integer userId, Integer pageNumber , Integer pageSize) {

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "UserId", userId));

        Pageable p = PageRequest.of(pageNumber, pageSize);

        Page<Post> posts = this.postRepo.findAllByUser(user, p);

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(posts.getContent()
                .stream().map(post -> this.mapper.map(post, PostDto.class)).toList());

        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLastPage(posts.isLast());


        return postResponse;
}

    @Override
    public List<PostDto> searchPost(String keyword) {

        List<Post> posts = this.postRepo.findByPostTitleContaining(keyword);

        return posts.stream().map(post -> this.mapper.map(post, PostDto.class)).toList();
    }
}
