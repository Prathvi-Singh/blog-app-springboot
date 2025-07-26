package com.prathvi.blogApp.controllers;

import com.prathvi.blogApp.payloads.ApiResponse;
import com.prathvi.blogApp.payloads.UserDto;
import com.prathvi.blogApp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserController{

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<UserDto>  createUser(@Valid @RequestBody UserDto userDto){
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@PathVariable("userId") Integer userId1){
     UserDto updateUser = this.userService.updateUser(userDto,userId1);
     return ResponseEntity.ok(updateUser);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId1){
       this.userService.deleteUser(userId1);
       //return ResponseEntity.ok(Map.of("message" , "user deleted Successfully"));
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully",true),HttpStatus.OK);
    }

     @GetMapping("/{userId}")
     public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
        System.out.println(userId);
        return new ResponseEntity(this.userService.getUserById(userId),HttpStatus.OK);
     }

     @GetMapping("/")
     public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<List<UserDto>>(this.userService.getAllUsers(),HttpStatus.OK);
     }

     @PostMapping("/register")
     public ResponseEntity<ApiResponse> Register(@RequestBody UserDto userDto){
        this.userService.registerNewUser(userDto);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Register Successfully",true),HttpStatus.OK);
     }

}

