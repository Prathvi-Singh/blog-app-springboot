package com.prathvi.blogApp.payloads;

import com.prathvi.blogApp.entities.Category;
import com.prathvi.blogApp.entities.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class PostDto{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    private CategoryDto category;
    private UserDto user;
}
