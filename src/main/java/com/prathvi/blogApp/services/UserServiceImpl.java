package com.prathvi.blogApp.services;

import com.prathvi.blogApp.entities.Role;
import com.prathvi.blogApp.entities.User;
import com.prathvi.blogApp.exceptions.ResourceNotFoundException;
import com.prathvi.blogApp.payloads.UserDto;
import com.prathvi.blogApp.repositories.RoleRepo;
import com.prathvi.blogApp.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.prathvi.blogApp.exceptions.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import com.prathvi.blogApp.DemoprathviApplication;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
       User user=this.modelMapper.map(userDto,User.class);
       user.setPassword(this.passwordEncoder.encode(user.getPassword()));
       Role role=this.roleRepo.findById(2).get();
       user.getRoles().add(role);
       this.userRepo.save(user);

       return this.modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user=this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
        user.setId(userId);
        userRepo.save(user);
        UserDto userDto1=this.userToDto(user);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));
        return this.modelMapper.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users=this.userRepo.findAll();
        List<UserDto> userDtos=users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
        userRepo.delete(user);
    }

    private User dtoToUser(UserDto userDto){
        User user=this.modelMapper.map(userDto , User.class);
//        User user=new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
        return user;
    }
    private UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setAbout(user.getAbout());
//        userDto.setPassword(user.getPassword());
       return userDto;
     }

}
