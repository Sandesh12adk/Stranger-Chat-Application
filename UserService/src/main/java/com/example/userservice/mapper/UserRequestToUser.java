package com.example.userservice.mapper;

import com.example.userservice.constant.GENDER;
import com.example.userservice.dto.UserRequest;
import com.example.userservice.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestToUser {

  public User userRequestToUser(UserRequest userRequest){
      User user= new User();
      user.setAge(userRequest.getAge());
      try {
          GENDER gender = GENDER.valueOf(userRequest.getGender().toUpperCase());
          user.setGender(gender);
      }catch (IllegalArgumentException ex){
          throw new IllegalArgumentException("Gender can be only 'Male' or 'Female'");
      }
      return user;
  }
}
