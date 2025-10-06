package com.example.chatservice.service;

import com.example.chatservice.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user")  // the service name registered in Eureka
public interface UserServiceClient {
    @GetMapping("/api/user/find-by-id/{id}")
    public UserResponse findById(@PathVariable long id);
    @PostMapping("/api/user/set-status")
    public void setStatus(@RequestParam long userId, @RequestParam String status);
}