package com.prathvi.blogApp.controllers;

import com.prathvi.blogApp.payloads.JwtAuthRequest;
import com.prathvi.blogApp.payloads.JwtAuthResponse;
import com.prathvi.blogApp.security.JwtTokenHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private JwtTokenHelper jwtTokenHelper;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateToken_Success() {
        // Arrange
        JwtAuthRequest request = new JwtAuthRequest();
        request.setUsername("testUser");
        request.setPassword("testPassword");

        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("testUser");
        when(mockUserDetails.getPassword()).thenReturn("encodedPassword");
        when(mockUserDetails.getAuthorities()).thenReturn(null);

        String mockToken = "mockJwtToken";
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(mockUserDetails);
        when(jwtTokenHelper.generateToken(mockUserDetails)).thenReturn(mockToken);

        // Act
        ResponseEntity<JwtAuthResponse> response = authController.createToken(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockToken, response.getBody().getToken());

        verify(authenticationManager, times(1))
                .authenticate(new UsernamePasswordAuthenticationToken("testUser", "testPassword"));
        verify(userDetailsService, times(1)).loadUserByUsername("testUser");
        verify(jwtTokenHelper, times(1)).generateToken(mockUserDetails);
    }

    @Test
    public void testCreateToken_UserDisabled() {
        // Arrange
        JwtAuthRequest request = new JwtAuthRequest();
        request.setUsername("testUser");
        request.setPassword("testPassword");

        doThrow(new DisabledException("User is disabled"))
                .when(authenticationManager)
                .authenticate(new UsernamePasswordAuthenticationToken("testUser", "testPassword"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authController.createToken(request);
        });

        assertEquals("User is disabled", exception.getMessage());
        verify(authenticationManager, times(1))
                .authenticate(new UsernamePasswordAuthenticationToken("testUser", "testPassword"));
        verify(userDetailsService, never()).loadUserByUsername(anyString());
    }

    @Test
    public void testCreateToken_InvalidCredentials() {
        // Arrange
        JwtAuthRequest request = new JwtAuthRequest();
        request.setUsername("testUser");
        request.setPassword("wrongPassword");

        doThrow(new BadCredentialsException("Invalid credentials"))
                .when(authenticationManager)
                .authenticate(new UsernamePasswordAuthenticationToken("testUser", "wrongPassword"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authController.createToken(request);
        });

        assertEquals("Invalid credentials", exception.getMessage());
        verify(authenticationManager, times(1))
                .authenticate(new UsernamePasswordAuthenticationToken("testUser", "wrongPassword"));
        verify(userDetailsService, never()).loadUserByUsername(anyString());
    }

    @Test
    public void testAuthenticate_Success() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";

        // Act & Assert (no exception should be thrown)
        assertDoesNotThrow(() -> authController.authenticate(username, password));

        verify(authenticationManager, times(1))
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @Test
    public void testAuthenticate_UserDisabled() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";

        doThrow(new DisabledException("User is disabled"))
                .when(authenticationManager)
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authController.authenticate(username, password);
        });

        assertEquals("User is disabled", exception.getMessage());
        verify(authenticationManager, times(1))
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }


}
