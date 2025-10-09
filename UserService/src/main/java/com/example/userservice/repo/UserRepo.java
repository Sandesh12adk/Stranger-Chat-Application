package com.example.userservice.repo;

import com.example.userservice.constant.STATUS;
import com.example.userservice.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    public List<User> findByStatus(STATUS status);
    // Derived method to set status ONLINE or OFFLINE based on userId
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :status WHERE u.userId = :userId")
    int updateStatusByUserId(long userId, STATUS status);
}
