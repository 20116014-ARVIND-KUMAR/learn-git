package com.learnspringboot.blog.controllers;

import com.learnspringboot.blog.config.AppConstants;
import com.learnspringboot.blog.entities.Post;
import com.learnspringboot.blog.payloads.FileResponse;
import com.learnspringboot.blog.payloads.PostDto;
import com.learnspringboot.blog.payloads.PostResponse;
import com.learnspringboot.blog.services.FileService;
import com.learnspringboot.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PostController {

    private PostService postService;

    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Autowired
    public PostController(PostService postService, FileService fileService){
        this.postService=postService;
        this.fileService=fileService;
    }

    @PostMapping("/posts/userId/{userId}/categoryId/{categoryId}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){

        PostDto addedPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(addedPost, HttpStatus.CREATED);
    }

    @GetMapping("/posts/userId/{userId}")
    public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
                                                        @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize){

        return new ResponseEntity<>(this.postService.getPostsByUser(userId, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/posts/categoryId/{categoryId}")
    public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
                                                            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize){

        return new ResponseEntity<>(this.postService.getPostsByCategory(categoryId, pageNumber, pageSize),HttpStatus.OK );
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String fieldValue,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir)
    {
        return new ResponseEntity<>(this.postService.getAllPost(pageNumber, pageSize, fieldValue, sortDir), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        return new ResponseEntity<>(this.postService.getPostById(postId), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePostById(@PathVariable Integer postId){

        this.postService.deletePost(postId);

        return new ResponseEntity<>(Map.of("message", "{Post deleted successfully with id: " + postId), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){

        return new ResponseEntity<>(this.postService.updatePost(postDto, postId), HttpStatus.OK);
    }

    @GetMapping("/posts/keyword/{keyword}")
    public ResponseEntity<List<PostDto>> searchByTitle(@PathVariable String keyword){

        List<PostDto> postDtos = this.postService.searchPost(keyword);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @PostMapping("/posts/image/{postId}")
    public ResponseEntity<PostDto> imageUpload(@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException {

        PostDto postDto = this.postService.getPostById(postId);
        String imageName = this.fileService.uploadImage(this.path, image);
        postDto.setImageName(imageName);
        PostDto updatedPost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);

    }

    @GetMapping("/posts/image/{postId}")
    public ResponseEntity<?> imageDownload(@PathVariable Integer postId, HttpServletResponse response) {

        PostDto postDto = this.postService.getPostById(postId);
        String imageName = postDto.getImageName();

        try {
            InputStream resource = this.fileService.getResource(path, imageName);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource, response.getOutputStream());
        }
        catch (FileNotFoundException e){
            return new ResponseEntity<>(new FileResponse(imageName, "Image not found"), HttpStatus.NOT_FOUND);
        }
        catch (IOException e){
            return new ResponseEntity<>(new FileResponse(null, "Unable to fetch the image due to server error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
