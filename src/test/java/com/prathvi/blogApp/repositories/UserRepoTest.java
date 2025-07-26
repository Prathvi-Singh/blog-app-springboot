//package com.prathvi.blogApp.repositories;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//
//class UserRepoTest{
//
//    @Mock
//    private UserRepo userRepo;
//
//    private UserRepoTest() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void findByEmail() {
//        // Arrange
//        String email = "test@example.com";
//        User mockUser = new User();
//        mockUser.setId(1);
//        mockUser.setName("Test User");
//        mockUser.setAbout("This is a test user.");
//        mockUser.setEmail(email);
//        mockUser.setPassword("password123");
//
//        when(userRepo.findByEmail(email)).thenReturn(Optional.of(mockUser));
//
//        // Act
//        Optional<User> foundUser = userRepo.findByEmail(email);
//
//        // Assert
//        assertTrue(foundUser.isPresent(), "User should be present");
//        assertEquals(email, foundUser.get().getEmail(), "Email should match");
//        assertEquals("Test User", foundUser.get().getName(), "Name should match");
//        assertEquals("This is a test user.", foundUser.get().getAbout(), "About should match");
//        assertEquals("password123", foundUser.get().getPassword(), "Password should match");
//
//        // Verify that the repository method was called exactly once
//        verify(userRepo, times(1)).findByEmail(email);
//    }
//}
