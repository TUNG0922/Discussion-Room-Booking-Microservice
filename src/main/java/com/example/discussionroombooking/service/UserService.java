package com.example.discussionroombooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.discussionroombooking.model.User;
import com.example.discussionroombooking.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Check if username already exists
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    // Save new user
    public void saveUser(User user) {
        userRepository.save(user);
    }

    // Authenticate user with username, password, and role
    public User authenticate(String username, String password, String role) {
        User user = userRepository.findByUsername(username);
        if (user == null) return null; // User not found
        if (!user.getRole().equalsIgnoreCase(role)) return null; // Role mismatch
        if (!user.getPassword().equals(password)) return null; // Password mismatch
        return user; // Authenticated
    }
}
