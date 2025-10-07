package com.example.userservice.controller;

import com.example.userservice.dto.BlockedUserRequest;
import com.example.userservice.dto.BlockedUserResponse;
import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.service.BlockedUserService;
import com.example.userservice.service.GeoService;
import com.example.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    @Autowired
    private BlockedUserService blockedUserService;
    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping("/signin")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody UserRequest userRequest, HttpServletRequest request, HttpSession
                                              session){
        UserResponse userResponse= userService.createUser(userRequest,request);
        session.setAttribute("userName",userResponse.getUserName());
        session.setAttribute("userId",userResponse.getUserId());
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
    @GetMapping("/session-check")
    public ResponseEntity<UserResponse> checkSession(HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        Long userId= (Long) session.getAttribute("userId");
        if (userName != null && userId!= null) {
           UserResponse userResponse= userService.findById(userId);
            return ResponseEntity.ok(userResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        if (session != null) {
            session.invalidate();  // invalidate session
        }
        return ResponseEntity.ok("Logged out successfully");
    }
   @PostMapping("/set-status")
    public void setStatus(@RequestParam long userId, @RequestParam String status){
        userService.setStatus(userId,status);
   }
   @GetMapping("get-all-blocked-users")
    public ResponseEntity<List<BlockedUserResponse>> findAllBlockedUsers(){
        return ResponseEntity.ok(blockedUserService.findAll());
   }
    @GetMapping("/isblocked/{blockedBy}/{blockedTo}")
    public ResponseEntity<Boolean> isBlocked(@PathVariable long blockedBy, @PathVariable long blockedTo){
        return ResponseEntity.ok(blockedUserService.isBlocekd(blockedBy,blockedTo));
    }
   @PostMapping("/block")
    public void block(@RequestBody BlockedUserRequest blockedUserRequest){
        blockedUserService.block(blockedUserRequest);
   }
    @PostMapping("/unblock/{blockedBy}/{blockedTo}")
    public void block(@PathVariable long blockedBy, @PathVariable long blockedTo){
        blockedUserService.unBlock(blockedBy,blockedTo);
    }
}
