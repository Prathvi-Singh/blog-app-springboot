package com.prathvi.blogApp.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Data
@Table(name="categories")
public class Category{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categroyId;

//    @Column(name="title",length=100,nullable = false)
     private String categoryTitle;

//    @Column(name="description")
     private String categoryDescription;

     @OneToMany(mappedBy = "category" ,cascade = CascadeType.ALL)
     @JsonManagedReference
     private List<Post> posts=new ArrayList<>();

}
