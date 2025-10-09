package com.example.userservice.repo;

import com.example.userservice.model.BlockedUser;
import com.example.userservice.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedUserRepo extends JpaRepository<BlockedUser,Long> {
    boolean existsByBlockedByAndBlockedTo(User blockedBy, long blockedTo);
    @Transactional
    void deleteByBlockedByAndBlockedTo(User blockedBy, long blockedTo);
}
