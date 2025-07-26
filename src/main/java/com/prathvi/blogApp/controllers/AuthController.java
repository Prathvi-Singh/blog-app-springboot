package com.prathvi.blogApp.controllers;
import com.prathvi.blogApp.payloads.JwtAuthRequest;
import com.prathvi.blogApp.payloads.JwtAuthResponse;
import com.prathvi.blogApp.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {
        System.out.println("Attempting to authenticate user: " + request.getUsername());
        this.authenticate(request.getUsername(), request.getPassword());
        System.out.println("User authenticated, generating token...");
        UserDetails loadUserByUsername = this.userDetailsService.loadUserByUsername(request.getUsername());
        System.out.println("Username: " + loadUserByUsername.getUsername());
        System.out.println("Password: " + loadUserByUsername.getPassword());
        System.out.println("Authorities: " + loadUserByUsername.getAuthorities());
        String token = this.jwtTokenHelper.generateToken(loadUserByUsername);
        System.out.println("Token generated: " + token);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public void authenticate(String username, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            this.authenticationManager.authenticate(authenticationToken);
        } catch (DisabledException e) {
            throw new RuntimeException("User is disabled", e);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid credentials", e);
        }
    }


}
