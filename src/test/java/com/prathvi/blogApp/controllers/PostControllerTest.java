package com.prathvi.blogApp.controllers;

import com.prathvi.blogApp.payloads.ApiResponse;
import com.prathvi.blogApp.payloads.PostDto;
import com.prathvi.blogApp.payloads.PostResponse;
import com.prathvi.blogApp.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePost() {
        // Arrange
        PostDto postDto = new PostDto();
        postDto.setTitle("Test Post");
        Integer userId = 1;
        Integer categoryId = 2;

        when(postService.createPost(any(PostDto.class), eq(userId), eq(categoryId)))
                .thenReturn(postDto);

        // Act
        ResponseEntity<PostDto> response = postController.createPost(postDto, userId, categoryId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Post", response.getBody().getTitle());

        verify(postService, times(1)).createPost(any(PostDto.class), eq(userId), eq(categoryId));
    }

    @Test
    public void testGetPostsByUser() {
        // Arrange
        Integer userId = 1;
        List<PostDto> postList = Arrays.asList(new PostDto(), new PostDto());
        when(postService.getPostsByUser(userId)).thenReturn(postList);

        // Act
        ResponseEntity<List<PostDto>> response = postController.getPostsByUser(userId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());

        verify(postService, times(1)).getPostsByUser(userId);
    }

    @Test
    public void testGetPostsByCategory() {
        // Arrange
        Integer categoryId = 1;
        List<PostDto> postList = Arrays.asList(new PostDto(), new PostDto());
        when(postService.getPostsByCategory(categoryId)).thenReturn(postList);

        // Act
        ResponseEntity<List<PostDto>> response = postController.getPostsByCategory(categoryId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());

        verify(postService, times(1)).getPostsByCategory(categoryId);
    }

    @Test
    public void testDeletePostByID() {
        // Arrange
        Integer postId = 1;

        // Act
        ResponseEntity<ApiResponse> response = postController.deletePostByID(postId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().success);
        assertEquals("Successfully delete Post of id : 1", response.getBody().message);
    }


    @Test
    public void testSearchPostsByTitle() {
        // Arrange
        String keyword = "test";
        List<PostDto> postList = Arrays.asList(new PostDto(), new PostDto());
        when(postService.searchPosts(keyword)).thenReturn(postList);

        // Act
        ResponseEntity<List<PostDto>> response = postController.searchPostsByTitle(keyword);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());

        verify(postService, times(1)).searchPosts(keyword);
    }


    @Test
    public void testUpdatePostById() {
        // Arrange
        Integer postId = 1;
        PostDto postDto = new PostDto();
        postDto.setTitle("Updated Post");
        when(postService.updatePost(postDto, postId)).thenReturn(postDto);

        // Act
        ResponseEntity<PostDto> response = postController.updatePostById(postDto, postId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Post", response.getBody().getTitle());

        verify(postService, times(1)).updatePost(postDto, postId);
    }

}
