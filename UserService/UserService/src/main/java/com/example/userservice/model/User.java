package com.example.userservice.model;

import com.example.userservice.constant.STATUS;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

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
}
