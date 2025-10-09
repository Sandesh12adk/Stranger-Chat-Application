package com.example.userservice.mapper;

import com.example.userservice.dto.BlockedUserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.model.BlockedUser;
import com.example.userservice.model.User;
import com.example.userservice.repo.UserRepo;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlockedUserRequestToBlockedUser {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    public BlockedUser convert(BlockedUserRequest blockedUserRequest){
        long blockedTo= blockedUserRequest.getBlockedTo();
        long blockedBy= blockedUserRequest.getBlockedBy();
        BlockedUser blockedUser= new BlockedUser();
        blockedUser.setBlockedTo(blockedTo);
        User user= userRepo.findById(blockedBy)
                .orElseThrow(()-> new UserNotFoundException("User not found to Block yo chai"));
        blockedUser.setBlockedBy(user);
        return blockedUser;
    }
}
