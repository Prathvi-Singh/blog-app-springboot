package com.prathvi.blogApp.services;

import com.prathvi.blogApp.entities.Category;
import com.prathvi.blogApp.entities.Post;
import com.prathvi.blogApp.entities.User;
import com.prathvi.blogApp.exceptions.ResourceNotFoundException;
import com.prathvi.blogApp.payloads.PostDto;
import com.prathvi.blogApp.payloads.PostResponse;
import com.prathvi.blogApp.repositories.CategoryRepo;
import com.prathvi.blogApp.repositories.PostRepo;
import com.prathvi.blogApp.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


import java.lang.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper  modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        System.out.printf("%d  %d",userId,categoryId);
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","User Id",userId));
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
        System.out.printf("User ID: %d, Category ID: %d%n", userId, categoryId);
//        System.out.println("Fetched User: " + user);
//        System.out.println("Fetched Category: " + category);
        Post post=this.modelMapper.map(postDto,Post.class);
        System.out.println("Mapped Post: " + post);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost =this.postRepo.save(post);

        return this.modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post" , "Post Id" , postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        postRepo.save(post);


        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post Id",postId));
        postRepo.delete(post);
    }

//    @Override
//    public List<PostDto> getAllPost() {
//        List<Post> posts=this.postRepo.findAll();
//        List<PostDto> postDtos=posts.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
//        return postDtos;
//    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy) {

        Pageable pageable = (Pageable) PageRequest.of(pageNumber, pageSize , Sort.by(sortBy));
        Page<Post> pagePost = this.postRepo.findAll((org.springframework.data.domain.Pageable) pageable);
        List<Post> posts = pagePost.getContent();
        List<PostDto> postDtos = posts.stream()
                .map(post -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;

    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post Id",postId));
        PostDto postDto=this.modelMapper.map(post,PostDto.class);
        return postDto;
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","category Id",categoryId));
        List<Post> posts = this.postRepo.findByCategory(category);

        List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        System.out.println(userId);
        User user=this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","user Id",userId));
//        System.out.println(user.toString());
        List<Post> posts=this.postRepo.findByUser(user);

        List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
     }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts=postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos=posts.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
