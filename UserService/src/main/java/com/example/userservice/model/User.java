package com.example.userservice.model;

import com.example.userservice.constant.GENDER;
import com.example.userservice.constant.STATUS;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    @Column(unique = false, nullable = false)
    private int age;
    @CreatedDate
    private LocalDateTime userCreatedAt;
    @Enumerated(EnumType.STRING)
    private STATUS status= STATUS.OFFLINE;
    @Column(nullable = true)
    private String country="unknown";
    private String userName;
    @Enumerated(EnumType.STRING)
    private GENDER gender;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "blockedBy")
    private List<BlockedUser> blockedUserList;
}