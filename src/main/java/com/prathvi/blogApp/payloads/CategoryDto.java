package com.prathvi.blogApp.payloads;

import com.prathvi.blogApp.entities.Post;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public class CategoryDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categroyId;

    @Column(name="title",nullable = false)
    private String categoryTitle;


    private String categoryDescription;

}
