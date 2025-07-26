package com.prathvi.blogApp.controllers;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import com.prathvi.blogApp.entities.Post;
import com.prathvi.blogApp.payloads.ApiResponse;
import com.prathvi.blogApp.payloads.PostDto;
import com.prathvi.blogApp.payloads.PostResponse;
import com.prathvi.blogApp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api")
public class PostController{

    @Autowired
    private PostService postService;

    @PostMapping("/users/{userId}/categories/{categroyId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody  PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categroyId){
       System.out.println(postDto);
       System.out.println(userId);
       System.out.println(categroyId);
       PostDto createPost = this.postService.createPost(postDto,userId,categroyId);
       return new ResponseEntity<PostDto>( createPost , HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
        List<PostDto> postDtos = this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
        List<PostDto> postDtos = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam Integer pageNumber,
                                                    @RequestParam Integer pageSize ,
                                                    @RequestParam(value="sortBy" , defaultValue="postId" ,required=false) String sortBy
    ){
        PostResponse postResponse=this.postService.getAllPost(pageNumber,pageSize,sortBy);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('admin')")

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        return new ResponseEntity<PostDto>(this.postService.getPostById(postId),HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto,@PathVariable Integer postId){
        return new ResponseEntity<PostDto>(this.postService.updatePost(postDto,postId),HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePostByID(@PathVariable Integer postId){
        return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully delete Post of id : " + postId,true),HttpStatus.OK);
    }

    @GetMapping("posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable String keyword){
        return new ResponseEntity<List<PostDto>>(postService.searchPosts(keyword),HttpStatus.OK);
    }



}
