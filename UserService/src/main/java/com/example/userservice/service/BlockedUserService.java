package com.example.userservice.service;

import com.example.userservice.dto.BlockedUserRequest;
import com.example.userservice.dto.BlockedUserResponse;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.mapper.BlockedUserRequestToBlockedUser;
import com.example.userservice.mapper.BlockedeUserToBlockedUserResponse;
import com.example.userservice.model.User;
import com.example.userservice.repo.BlockedUserRepo;
import com.example.userservice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlockedUserService {
    private final BlockedUserRepo blockedUserRepo;
    private final UserRepo userRepo;
    @Autowired
    private  BlockedUserRequestToBlockedUser blockedUserRequestToBlockedUser;
    @Autowired
    private BlockedeUserToBlockedUserResponse blockedeUserToBlockedUserResponse;
    public BlockedUserService(BlockedUserRepo blockedUserRepo, UserRepo userRepo) {
        this.blockedUserRepo = blockedUserRepo;
        this.userRepo= userRepo;
    }

    public void block(BlockedUserRequest blockedUserRequest) {
        // Check if the block relationship already exists
        User user=userRepo.findById(  blockedUserRequest.getBlockedBy())
                .orElseThrow(()-> new UserNotFoundException("User Not found"));
        boolean alreadyExists = blockedUserRepo.existsByBlockedByAndBlockedTo(
              user,
                blockedUserRequest.getBlockedTo()
        );

        // Only save if it doesn't already exist
        if (!alreadyExists) {
            blockedUserRepo.save(blockedUserRequestToBlockedUser
                    .convert(blockedUserRequest));
        }
        // If it already exists, do nothing (idempotent)
    }
    public BlockedUserResponse findById(long id){
        return blockedeUserToBlockedUserResponse.convert(blockedUserRepo.findById(id)
                .orElseThrow(()-> new UserNotFoundException("Not found")));
    }
    public boolean isBlocekd(long blockedBy, long blockedTo){
        User user=userRepo.findById(blockedBy)
                .orElseThrow(()-> new UserNotFoundException("User Not found"));
        return blockedUserRepo.existsByBlockedByAndBlockedTo(user,blockedTo);
    }
    public List<BlockedUserResponse> findAll(){
        List<BlockedUserResponse> blockedUserResponseList= new ArrayList<>();
        blockedUserRepo.findAll()
                .forEach((user)-> blockedUserResponseList.add(blockedeUserToBlockedUserResponse.convert(user)));
        return blockedUserResponseList;
    }
    public void unBlock(long blockedBy, long blockedTo){
        User user=userRepo.findById(blockedBy)
                .orElseThrow(()-> new UserNotFoundException("User Not found"));
          blockedUserRepo.deleteByBlockedByAndBlockedTo(user,blockedTo);
    }
}
