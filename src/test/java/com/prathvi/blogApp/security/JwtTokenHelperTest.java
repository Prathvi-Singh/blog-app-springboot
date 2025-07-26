package com.prathvi.blogApp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtTokenHelperTest {

    private JwtTokenHelper jwtTokenHelper;
    private Key secretKey;



    @BeforeEach
    void setUp() {
        jwtTokenHelper = new JwtTokenHelper();
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

//    @Test
//    void testIsTokenExpired() {
//        // Retrieve the secret key from JwtTokenHelper
//        //var secretKey = jwtTokenHelper.getSecretKey();
//
//        // Create an expired token using the retrieved secret key
//        String expiredToken = Jwts.builder()
//                .setSubject("testuser")
//                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60)) // 1 hour ago
//                .setExpiration(new Date(System.currentTimeMillis() - 1000)) // Expired 1 second ago
//                .signWith(secretKey) // Use the same key as JwtTokenHelper
//                .compact();
//
//        // Validate the expired token
//        boolean isValid = jwtTokenHelper.validateToken(expiredToken,
//                new User("testuser", "password", new ArrayList<>()));
//
//        // The token is expired, so validation should return false
//        assertFalse(isValid, "Token validation should fail for expired token");
//    }


    @Test
    void testGenerateToken() {
        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());
        String token = jwtTokenHelper.generateToken(userDetails);
        assertNotNull(token, "Token should not be null");
    }

    @Test
    void testGetUsernameFromToken() {
        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());
        String token = jwtTokenHelper.generateToken(userDetails);
        String username = jwtTokenHelper.getUsernameFromToken(token);
        assertEquals("testuser", username, "Username should match the one in the token");
    }

    @Test
    void testGetExpirationDateFromToken() {
        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());
        String token = jwtTokenHelper.generateToken(userDetails);
        Date expirationDate = jwtTokenHelper.getExpirationDateFromToken(token);
        assertNotNull(expirationDate, "Expiration date should not be null");
        assertTrue(expirationDate.after(new Date()), "Expiration date should be in the future");
    }

//    @Test
//    void testIsTokenExpired() throws InterruptedException {
//        // Create an expired token
//        String expiredToken = Jwts.builder()
//                .setSubject("testuser")
//                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60))
//                .setExpiration(new Date(System.currentTimeMillis() - 1000))
//                .signWith(secretKey)
//                .compact();
//
//        assertTrue(jwtTokenHelper.validateToken(expiredToken, new User("testuser", "password", new ArrayList<>())));
//        Thread.sleep(1000); //Ensure timeout work if token with link validate fails
//    }

    @Test
    void testValidateToken() {
        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());
        String token = jwtTokenHelper.generateToken(userDetails);
        assertTrue(jwtTokenHelper.validateToken(token, userDetails), "Token should be valid for the user");
    }


}
