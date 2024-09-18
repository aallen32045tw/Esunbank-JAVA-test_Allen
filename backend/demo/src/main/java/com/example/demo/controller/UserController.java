package com.example.demo.controller;

import com.example.demo.dto.UserLoginDto;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.entity.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            userService.registerUser(
                registrationDto.getPhoneNumber(), 
                registrationDto.getPassword(), 
                registrationDto.getUserName()
            );
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginDto loginDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
    }

    // 使用 UserService 驗證用戶憑據
    User user = userService.loginUser(loginDto.getPhoneNumber(), loginDto.getPassword());
    if (user == null) {
        return ResponseEntity.badRequest().body("Invalid phone number or password");
    }

    // 用戶憑據有效，生成 JWT token
    UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getPhoneNumber());
    String jwt = jwtUtil.generateToken(userDetails);

    // 返回 token 和 user.id
    Map<String, Object> response = new HashMap<>();
    response.put("token", jwt);
    response.put("userId", user.getId());  // 返回 userId
    return ResponseEntity.ok(response);
}

}
