package com.prathvi.blogApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.prathvi.blogApp.entities.Category;


public interface CategoryRepo extends JpaRepository<Category ,Integer> {

}
