package com.prathvi.blogApp.repositories;

import com.prathvi.blogApp.entities.Category;
import com.prathvi.blogApp.entities.Post;
import com.prathvi.blogApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);
    List<Post>  findByCategory(Category category);
    List<Post> findByTitleContaining(String title);

}
