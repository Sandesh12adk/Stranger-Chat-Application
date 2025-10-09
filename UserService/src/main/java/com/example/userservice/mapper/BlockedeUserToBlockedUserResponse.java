package com.example.userservice.mapper;

import com.example.userservice.dto.BlockedUserResponse;
import com.example.userservice.model.BlockedUser;
import org.springframework.stereotype.Component;

@Component
public class BlockedeUserToBlockedUserResponse {
    public BlockedUserResponse convert(BlockedUser blockedUser){
        BlockedUserResponse blockedUserResponse= new BlockedUserResponse();
        blockedUserResponse.setBlockedBy(blockedUser.getBlockedBy().getUserId());
        blockedUserResponse.setBlockedTo(blockedUser.getBlockedTo());
        blockedUserResponse.setId(blockedUser.getId());
        blockedUserResponse.setBlockedDateAndTime(blockedUser.getBlockedDateAndTime());
        return blockedUserResponse;
    }
}
