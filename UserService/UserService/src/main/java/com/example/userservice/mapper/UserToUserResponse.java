package com.example.userservice.mapper;

import com.example.userservice.dto.UserResponse;
import com.example.userservice.model.User;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserToUserResponse {

    public UserResponse userToUserResponse(User user) {
        if (user == null) {
            return null; // or return a new UserResponse() with default values
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());

        // Null checks for individual fields
        userResponse.setAge(user.getAge() != 0 ?user.getAge() : 18);
        userResponse.setStatus(user.getStatus() != null ? user.getStatus().name() : "UNKNOWN");
        userResponse.setUserCreatedAt(user.getUserCreatedAt() != null ? user.getUserCreatedAt() : null);
        userResponse.setCountry(user.getCountry() != null ? user.getCountry() : "Unknown");
        userResponse.setUserName(user.getUserName() !=null? user.getUserName():"Unknown");
        userResponse.setGender(user.getGender().name()!=null? user.getGender().name(): "MALE");
        return userResponse;
    }
}
