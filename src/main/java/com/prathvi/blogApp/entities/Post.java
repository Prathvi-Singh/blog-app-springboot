package com.prathvi.blogApp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name="posts2")
public class Post{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    @Column(name="post_title",length = 100,nullable = false)
    private String title;
    private String content;
    private String imageName;
    private Date addedDate;


    @ManyToOne
    @JoinColumn(name = "categroy_id")
    @JsonBackReference
    private Category category;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}