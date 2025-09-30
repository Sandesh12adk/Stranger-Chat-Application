package com.example.userservice.mapper;

import com.example.userservice.dto.UserRequest;
import com.example.userservice.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestToUser {

  public User userRequestToUser(UserRequest userRequest){
      User user= new User();
      user.setAge(userRequest.getAge());
      return user;
  }
}
