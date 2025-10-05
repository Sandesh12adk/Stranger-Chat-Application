package com.example.userservice.service;

import com.example.userservice.constant.STATUS;
import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.mapper.UserRequestToUser;
import com.example.userservice.mapper.UserToUserResponse;
import com.example.userservice.model.User;
import com.example.userservice.repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final UserRequestToUser userRequestToUser;
    private final UserToUserResponse userToUserResponse;
    private final GeoService geoService;
    @Autowired
    public UserService(UserRepo userRepo,UserToUserResponse userToUserResponse, UserRequestToUser
                        userRequestToUser,GeoService geoService){
        this.userRepo= userRepo;
        this.userRequestToUser= userRequestToUser;
        this.userToUserResponse= userToUserResponse;
        this.geoService= geoService;
    }
    public UserResponse createUser(UserRequest userRequest, HttpServletRequest request){
        User user= userRequestToUser.userRequestToUser(userRequest);
        user.setCountry(geoService.getCountry(request));
        String username = "User_" + UUID.randomUUID().toString().substring(0, 8)+" "+ userRequest.getAge();
        user.setUserName(username);
        userRepo.save(user);
        return userToUserResponse.userToUserResponse(user);
    }
    public UserResponse findById(long id){
        User user= userRepo.findById(id).orElseThrow(()->
                new UserNotFoundException("User Not Exist"));
        return userToUserResponse.userToUserResponse(user);
    }
    public List<UserResponse> findByStatus(String status1) {
        List<UserResponse> activeUsers= new ArrayList<>();
        try {
            STATUS status = STATUS.valueOf(status1.toUpperCase());
            userRepo.findByStatus(status).forEach((user)->{
                activeUsers.add(userToUserResponse.userToUserResponse(user));
            });
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Status can only be 'OFFLINE' or 'ONLINE'");
        }
        return activeUsers;
    }
    public void setStatus(long userId, String status1){
        try {
            STATUS status = STATUS.valueOf(status1.toUpperCase());
            userRepo.updateStatusByUserId(userId,status);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Status can only be 'OFFLINE' or 'ONLINE'");
        }
    }
}