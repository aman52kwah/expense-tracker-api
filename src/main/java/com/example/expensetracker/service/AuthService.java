package com.example.expensetracker.service;

import com.example.expensetracker.dto.LoginRequest;
import com.example.expensetracker.dto.RegisterRequest;
import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already registered");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(false);
        user.setVerificationToken(UUID.randomUUID().toString());
        user.setTokenExpiry(LocalDateTime.now().plusMinutes(10));
        return userRepository.save(user);
    }

    public User verifyEmail(String token){
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(()->new RuntimeException("Invalid verification token"));

        if (user.getTokenExpiry().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Token expired");
        }
        user.setEnabled(true);
        user.setVerificationToken(null);
        user.setTokenExpiry(null);
        return userRepository.save(user);
    }

    public User login(LoginRequest request)  {
      String email = request.getEmail();
      String password = request.getPassword();

      User user = userRepository.findByEmail(request.getEmail())
              .orElseThrow(()-> new RuntimeException("Invalid email or password"));

      if(!user.isEnabled()){
          throw new RuntimeException("Please verify email before logging in");
      }
      if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
        throw new RuntimeException("Invalid email or password");
        }
      return user;

    }
}

