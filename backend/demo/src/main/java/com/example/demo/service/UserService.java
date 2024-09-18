package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(String phoneNumber, String password, String userName) {
        String encodedPassword = passwordEncoder.encode(password);
        userRepository.registerUser(phoneNumber, encodedPassword, userName);
    }

    @Transactional
    public User loginUser(String phoneNumber, String password) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            user.setLastLoginTime(LocalDateTime.now());
            return userRepository.save(user);
        }
        return null;
    }
}
