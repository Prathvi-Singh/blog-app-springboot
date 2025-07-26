package com.prathvi.blogApp.services;

import com.prathvi.blogApp.entities.Post;
import com.prathvi.blogApp.payloads.PostDto;
import com.prathvi.blogApp.payloads.PostResponse;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.*;

public interface PostService {
   PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
   PostDto updatePost(PostDto postDto,Integer postId);
   void deletePost(Integer postId);
//   List<PostDto> getAllPost(Integer NumberPage,Integer PageSize);
   PostResponse getAllPost(Integer NumberPage, Integer PageSize,String sortBy);
   PostDto getPostById(Integer postId);
   List<PostDto> getPostsByCategory(Integer categoryId);
   List<PostDto> getPostsByUser(Integer userId);
   List<PostDto> searchPosts(String keyword);


}
