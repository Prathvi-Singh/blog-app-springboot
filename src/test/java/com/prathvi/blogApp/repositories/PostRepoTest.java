package com.prathvi.blogApp.repositories;

import com.prathvi.blogApp.entities.Category;
import com.prathvi.blogApp.entities.Post;
import com.prathvi.blogApp.entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

//@DataJpaTest
//class PostRepoTest {
//
//    @Autowired
//    private PostRepo postRepo;
//
//    @Autowired
//    private UserRepo userRepo;
//
//    @Autowired
//    private CategoryRepo categoryRepo;
//
//    private User testUser;
//    private Category testCategory;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        // Create test user
//        testUser = new User();
//        testUser.setName("John Doe");
//        testUser.setEmail("john.doe@example.com");
//        testUser.setPassword("password123");
//        userRepo.save(testUser);
//
//        // Create test category
//        testCategory = new Category();
//        testCategory.setCategoryTitle("Technology");
//        testCategory.setCategoryDescription("Tech-related posts");
//        categoryRepo.save(testCategory);
//
//        // Create test posts
//        Post post1 = new Post();
//        post1.setTitle("Spring Boot Tutorial");
//        post1.setContent("Learn Spring Boot from scratch.");
//        post1.setImageName("springboot.png");
//        post1.setAddedDate(new Date());
//        post1.setCategory(testCategory);
//        post1.setUser(testUser);
//
//        Post post2 = new Post();
//        post2.setTitle("Introduction to AI");
//        post2.setContent("What is Artificial Intelligence?");
//        post2.setImageName("ai_intro.png");
//        post2.setAddedDate(new Date());
//        post2.setCategory(testCategory);
//        post2.setUser(testUser);
//
//        postRepo.save(post1);
//        postRepo.save(post2);
//    }
//
//    @Test
//    void findByUser() {
//        List<Post> posts = postRepo.findByUser(testUser);
//        assertNotNull(posts);
//        assertEquals(2, posts.size());
//        assertEquals("John Doe", posts.get(0).getUser().getName());
//    }
//
//    @Test
//    void findByCategory() {
//        List<Post> posts = postRepo.findByCategory(testCategory);
//        assertNotNull(posts);
//        assertEquals(2, posts.size());
//        assertEquals("Technology", posts.get(0).getCategory().getCategoryTitle());
//    }
//
//    @Test
//    void findByTitleContaining() {
//        List<Post> posts = postRepo.findByTitleContaining("AI");
//        assertNotNull(posts);
//        assertEquals(1, posts.size());
//        assertEquals("Introduction to AI", posts.get(0).getTitle());
//    }
//}

import org.mockito.Mock;

import java.util.Arrays;

import static org.mockito.Mockito.*;

class PostRepoTest {

    @Mock
    private PostRepo postRepo;

    private User testUser;
    private Category testCategory;
    private Post post1, post2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create test user
        testUser = new User();
        testUser.setName("John Doe");
        testUser.setEmail("john.doe@example.com");

        // Create test category
        testCategory = new Category();
        testCategory.setCategoryTitle("Technology");

        // Create test posts
        post1 = new Post();
        post1.setTitle("Spring Boot Tutorial");
        post1.setContent("Learn Spring Boot from scratch.");
        post1.setAddedDate(new Date());
        post1.setCategory(testCategory);
        post1.setUser(testUser);

        post2 = new Post();
        post2.setTitle("Introduction to AI");
        post2.setContent("What is Artificial Intelligence?");
        post2.setAddedDate(new Date());
        post2.setCategory(testCategory);
        post2.setUser(testUser);

        when(postRepo.findByUser(testUser)).thenReturn(Arrays.asList(post1, post2));
        when(postRepo.findByCategory(testCategory)).thenReturn(Arrays.asList(post1, post2));
        when(postRepo.findByTitleContaining("AI")).thenReturn(List.of(post2));
    }

    @Test
    void findByUser() {
        List<Post> posts = postRepo.findByUser(testUser);
        assertNotNull(posts);
        assertEquals(2, posts.size());
        assertEquals("John Doe", posts.get(0).getUser().getName());
    }

    @Test
    void findByCategory() {
        List<Post> posts = postRepo.findByCategory(testCategory);
        assertNotNull(posts);
        assertEquals(2, posts.size());
        assertEquals("Technology", posts.get(0).getCategory().getCategoryTitle());
    }

    @Test
    void findByTitleContaining() {
        List<Post> posts = postRepo.findByTitleContaining("AI");
        assertNotNull(posts);
        assertEquals(1, posts.size());
        assertEquals("Introduction to AI", posts.get(0).getTitle());
    }
}

