package com.prathvi.blogApp.services;

import com.prathvi.blogApp.entities.Role;
import com.prathvi.blogApp.entities.User;
import com.prathvi.blogApp.payloads.UserDto;
import com.prathvi.blogApp.repositories.RoleRepo;
import com.prathvi.blogApp.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepo roleRepo;

    private User user;
    private UserDto userDto;
    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test objects
        user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");

        userDto = new UserDto();
        userDto.setId(1);
        userDto.setName("John Doe");
        userDto.setEmail("john.doe@example.com");
        userDto.setPassword("password123");

        role = new Role();
        role.setId(2);
        role.setName("ROLE_USER");
    }

    @Test
    void registerNewUser() {
        when(modelMapper.map(userDto, User.class)).thenReturn(user);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(roleRepo.findById(2)).thenReturn(Optional.of(role));
        when(userRepo.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        UserDto result = userService.registerNewUser(userDto);

        assertNotNull(result);
        assertEquals(userDto.getName(), result.getName());
        assertEquals("encodedPassword", user.getPassword());
        assertTrue(user.getRoles().contains(role));

        verify(userRepo, times(1)).save(user);
    }

    @Test
    void createUser() {
        when(modelMapper.map(userDto, User.class)).thenReturn(user);
        when(userRepo.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        UserDto result = userService.createUser(userDto);

        assertNotNull(result);
        assertEquals(userDto.getName(), result.getName());

        verify(userRepo, times(1)).save(user);
    }

    @Test
    void updateUser() {
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(userRepo.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        UserDto result = userService.updateUser(userDto, 1);

        assertNotNull(result);
        assertEquals(userDto.getName(), result.getName());

        verify(userRepo, times(1)).save(user);
    }

    @Test
    void getUserById() {
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        UserDto result = userService.getUserById(1);

        assertNotNull(result);
        assertEquals(userDto.getName(), result.getName());

        verify(userRepo, times(1)).findById(1);
    }

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userRepo.findAll()).thenReturn(users);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        List<UserDto> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userDto.getName(), result.get(0).getName());

        verify(userRepo, times(1)).findAll();
    }

    @Test
    void deleteUser() {
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        doNothing().when(userRepo).delete(user);

        userService.deleteUser(1);

        verify(userRepo, times(1)).delete(user);
    }
}
