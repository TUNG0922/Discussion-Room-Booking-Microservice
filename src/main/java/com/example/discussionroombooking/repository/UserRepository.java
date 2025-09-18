package com.example.discussionroombooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.discussionroombooking.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
