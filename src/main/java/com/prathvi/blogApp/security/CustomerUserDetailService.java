package com.prathvi.blogApp.security;

import com.prathvi.blogApp.entities.User;
import com.prathvi.blogApp.exceptions.ResourceNotFoundException;
import com.prathvi.blogApp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User","email"+username,0));
        return user;
    }
}
