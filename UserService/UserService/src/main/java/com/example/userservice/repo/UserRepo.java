package com.example.userservice.repo;

import com.example.userservice.constant.STATUS;
import com.example.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    public List<User> findByStatus(STATUS status);
}
