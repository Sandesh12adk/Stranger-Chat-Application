package com.example.userservice.controller;

import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.service.GeoService;
import com.example.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody UserRequest userRequest, HttpServletRequest request){
        UserResponse userResponse= userService.createUser(userRequest,request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userResponse);
    }
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable long id){
        UserResponse userResponse= userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userResponse);
    }
    @GetMapping("/find-by-status/{status}")
    public ResponseEntity<List<UserResponse>> findByStatus(@PathVariable String status){
        List<UserResponse> users= userService.findByStatus(status);
        return ResponseEntity.status(HttpStatus.OK)
                .body(users);
    }
}
