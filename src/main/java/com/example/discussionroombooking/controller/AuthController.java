package com.example.discussionroombooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.discussionroombooking.model.User;
import com.example.discussionroombooking.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserService userService;

    // Sign Up
    @PostMapping("/signup")
    public String registerUser(@RequestBody User user) {
        if (userService.usernameExists(user.getUsername())) {
            return "Username already exists!";
        }
        userService.saveUser(user);  // will save name, username, password, role
        return "User registered successfully!";
    }

    // Sign In
    @PostMapping("/signin")
    public SignInResponse loginUser(@RequestBody User user) {
        User authenticatedUser = userService.authenticate(user.getUsername(), user.getPassword(), user.getRole());
        if (authenticatedUser == null) {
            return new SignInResponse(false, "Invalid credentials", null, null);
        }
        // Return success, role, and name
        return new SignInResponse(true, "Login successful", authenticatedUser.getRole(), authenticatedUser.getName());
    }

    // Inner class for response
    public static class SignInResponse {
        private boolean success;
        private String message;
        private String role;
        private String name;  // Added name

        public SignInResponse(boolean success, String message, String role, String name) {
            this.success = success;
            this.message = message;
            this.role = role;
            this.name = name;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public String getRole() { return role; }
        public String getName() { return name; }
    }
}
