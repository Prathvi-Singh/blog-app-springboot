package com.prathvi.blogApp.payloads;

import com.prathvi.blogApp.entities.Post;
import com.prathvi.blogApp.entities.Role;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
public class UserDto{

    private int Id;
    private String name;
    @NotBlank(message="Email is mandatory")
    @Email(message="Please provide a valid email address")
    private String email;
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be at least 8 characters long"
    )

    private String password;
    private String about;

    private List<Post> posts=new ArrayList<>();
    private Set<Role> roles=new HashSet<>();
}
