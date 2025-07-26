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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepo postRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPost() {
        // Mock input data
        PostDto postDto = new PostDto();
        postDto.setTitle("Test Title");
        postDto.setContent("Test Content");

        User user = new User();
        user.setId(1);

        Category category = new Category();
        category.setCategroyId(1);

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setAddedDate(new Date());
        post.setImageName("default.png");
        post.setUser(user);
        post.setCategory(category);

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(categoryRepo.findById(1)).thenReturn(Optional.of(category));
        when(postRepo.save(any(Post.class))).thenReturn(post);
        when(modelMapper.map(any(Post.class), eq(PostDto.class))).thenReturn(postDto);
        when(modelMapper.map(eq(postDto), eq(Post.class))).thenReturn(post);

        // Call the service method
        PostDto createdPost = postService.createPost(postDto, 1, 1);

        // Verify and assert
        assertNotNull(createdPost);
        assertEquals(postDto.getTitle(), createdPost.getTitle());
        verify(postRepo, times(1)).save(any(Post.class));
    }

    @Test
    void updatePost_ShouldReturnUpdatedPostDto_WhenPostExists() {
        Post post;
        PostDto postDto;

        post = new Post();
        post.setPostId(1);
        post.setTitle("Sample Post");
        post.setContent("This is a sample post.");

        postDto = new PostDto();
        postDto.setPostId(1);
        postDto.setTitle("Updated Post");
        postDto.setContent("This is the updated content.");


        // Arrange
        Integer postId = 1;

        // Mock the behavior of postRepo.findById
        when(postRepo.findById(postId)).thenReturn(java.util.Optional.of(post));

        // Mock the ModelMapper to map PostDto to Post and vice versa
        when(modelMapper.map(postDto, Post.class)).thenReturn(post);
        when(modelMapper.map(post, PostDto.class)).thenReturn(postDto);

        // Act
        PostDto updatedPostDto = postService.updatePost(postDto, postId);

        // Assert
        assertNotNull(updatedPostDto);
        assertEquals(postDto.getTitle(), updatedPostDto.getTitle());
        assertEquals(postDto.getContent(), updatedPostDto.getContent());

        // Verify that the postRepo.save method is called
      //  verify(postRepo, times(1)).save(post);

        // Verify that the post.setPostId() method was called with the correct value
      //  verify(post, times(1)).setPostId(postId);
    }

    @Test
    void updatePost_ShouldThrowResourceNotFoundException_WhenPostNotFound() {
        Integer postId = 1;
       // PostDto postDto = null;  // simulate a case where postDto might be null or any data
        PostDto postDto = new PostDto();
        postDto.setPostId(1);
        postDto.setTitle("Updated Post");
        postDto.setContent("This is the updated content.");

        // Mock the behavior of postRepo.findById to return an empty Optional (post not found)
        when(postRepo.findById(postId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            postService.updatePost(postDto, postId);
        });

        // Assert: Verify the exception message contains the right content
//        assertTrue(thrown.getMessage().contains("Post"));
//        assertTrue(thrown.getMessage().contains("Post Id"));
      assertTrue(thrown.getMessage().contains("Post not found with Post Id : 1"));
//        assertTrue(thrown.getMessage().contains(postId.toString()));
    }

    @Test
    void deletePost() {
        Post post = new Post();
        post.setPostId(1);

        when(postRepo.findById(1)).thenReturn(Optional.of(post));
        doNothing().when(postRepo).delete(post);

        postService.deletePost(1);

        verify(postRepo, times(1)).delete(post);
    }

    @Test
    void getAllPost() {
        List<Post> posts = new ArrayList<>();
        Post post1 = new Post();
        Post post2 = new Post();
        posts.add(post1);
        posts.add(post2);

        Page<Post> postPage = new PageImpl<>(posts);

        when(postRepo.findAll(any(PageRequest.class))).thenReturn(postPage);
        when(modelMapper.map(any(Post.class), eq(PostDto.class))).thenReturn(new PostDto());

        PostResponse response = postService.getAllPost(0, 10, "id");

        assertNotNull(response);
        assertEquals(2, response.getContent().size());
    }

    @Test
    void getPostById() {
        Post post = new Post();
        post.setPostId(1);

        PostDto postDto = new PostDto();
        postDto.setPostId(1);

        when(postRepo.findById(1)).thenReturn(Optional.of(post));
        when(modelMapper.map(post, PostDto.class)).thenReturn(postDto);

        PostDto foundPost = postService.getPostById(1);

        assertNotNull(foundPost);
        assertEquals(1, foundPost.getPostId());
    }

    @Test
    void getPostsByCategory() {
        Category category = new Category();
        category.setCategroyId(1);

        List<Post> posts = new ArrayList<>();
        Post post1 = new Post();
        posts.add(post1);

        when(categoryRepo.findById(1)).thenReturn(Optional.of(category));
        when(postRepo.findByCategory(category)).thenReturn(posts);
        when(modelMapper.map(any(Post.class), eq(PostDto.class))).thenReturn(new PostDto());

        List<PostDto> postDtos = postService.getPostsByCategory(1);

        assertNotNull(postDtos);
        assertEquals(1, postDtos.size());
    }

    @Test
    void getPostsByUser() {
        User user = new User();
        user.setId(1);

        List<Post> posts = new ArrayList<>();
        Post post1 = new Post();
        posts.add(post1);

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(postRepo.findByUser(user)).thenReturn(posts);
        when(modelMapper.map(any(Post.class), eq(PostDto.class))).thenReturn(new PostDto());

        List<PostDto> postDtos = postService.getPostsByUser(1);

        assertNotNull(postDtos);
        assertEquals(1, postDtos.size());
    }

    @Test
    void searchPosts() {
        List<Post> posts = new ArrayList<>();
        Post post = new Post();
        post.setTitle("Test Post");
        posts.add(post);

        when(postRepo.findByTitleContaining("Test")).thenReturn(posts);
        when(modelMapper.map(any(Post.class), eq(PostDto.class))).thenReturn(new PostDto());

        List<PostDto> postDtos = postService.searchPosts("Test");

        assertNotNull(postDtos);
        assertEquals(1, postDtos.size());
    }
}
