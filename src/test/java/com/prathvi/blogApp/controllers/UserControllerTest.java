package com.prathvi.blogApp.controllers;

import com.prathvi.blogApp.payloads.ApiResponse;
import com.prathvi.blogApp.payloads.UserDto;
import com.prathvi.blogApp.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setName("John Doe");

        when(userService.createUser(any(UserDto.class))).thenReturn(userDto);

        // Act
        ResponseEntity<UserDto> response = userController.createUser(userDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
        verify(userService, times(1)).createUser(any(UserDto.class));
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setName("Updated User");

        when(userService.updateUser(any(UserDto.class), eq(1))).thenReturn(userDto);

        // Act
        ResponseEntity<UserDto> response = userController.updateUser(userDto, 1);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated User", response.getBody().getName());
        verify(userService, times(1)).updateUser(any(UserDto.class), eq(1));
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        doNothing().when(userService).deleteUser(1);

        // Act
        ResponseEntity<?> response = userController.deleteUser(1);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof ApiResponse);
        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertEquals("User deleted Successfully", apiResponse.message);
        assertTrue(apiResponse.success);
        verify(userService, times(1)).deleteUser(1);
    }

    @Test
    public void testGetSingleUser() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setName("John Doe");

        when(userService.getUserById(1)).thenReturn(userDto);

        // Act
        ResponseEntity<UserDto> response = userController.getSingleUser(1);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
        verify(userService, times(1)).getUserById(1);
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        List<UserDto> users = new ArrayList<>();
        UserDto user1 = new UserDto();
        user1.setId(1);
        user1.setName("John Doe");
        users.add(user1);

        UserDto user2 = new UserDto();
        user2.setId(2);
        user2.setName("Jane Doe");
        users.add(user2);

        when(userService.getAllUsers()).thenReturn(users);

        // Act
        ResponseEntity<List<UserDto>> response = userController.getAllUsers();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("John Doe", response.getBody().get(0).getName());
        assertEquals("Jane Doe", response.getBody().get(1).getName());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void testRegisterUser() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setName("New User");

        ApiResponse apiResponse = new ApiResponse("User Register Successfully", true);
        when(userService.registerNewUser(any(UserDto.class))).thenReturn(userDto);
        // Act
        ResponseEntity<ApiResponse> response = userController.Register(userDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("User Register Successfully", response.getBody().message);
        assertTrue(response.getBody().success);
        verify(userService, times(1)).registerNewUser(any(UserDto.class));
    }
}
