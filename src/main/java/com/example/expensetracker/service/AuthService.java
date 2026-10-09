package com.example.expensetracker.service;

import com.example.expensetracker.dto.LoginRequest;
import com.example.expensetracker.dto.RegisterRequest;

import com.example.expensetracker.model.*;
import com.example.expensetracker.repository.ActivityLogRepository;
import com.example.expensetracker.repository.CategoryRepository;
import com.example.expensetracker.repository.DefaultCategoryRepository;
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
    private final DefaultCategoryRepository defaultCategoryRepository;
    private final CategoryRepository categoryRepository;
    private ActivityLogRepository activityLogRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       DefaultCategoryRepository defaultCategoryRepository,
                       CategoryRepository categoryRepository,
                       ActivityLogRepository activityLogRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.activityLogRepository =activityLogRepository;
        this.defaultCategoryRepository =defaultCategoryRepository;
        this.categoryRepository =categoryRepository;
    }

    public User register(RegisterRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already registered");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserStatus(UserStatus.UNVERIFIED);
        user.setVerificationToken(UUID.randomUUID().toString());
        user.setTokenExpiry(LocalDateTime.now().plusMinutes(10));
        user = userRepository.save(user);


        // Copy default categories to user
        for (DefaultCategory dc : defaultCategoryRepository.findAll()) {
            Category c = new Category(dc.getName(), user);
            categoryRepository.save(c);
        }
        //Log activity
        activityLogRepository.save(new ActivityLog("SIGNUP", user.getEmail(),user.getEmail(), null));

        return user;
    }

    public User verifyEmail(String token){
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(()->new RuntimeException("Invalid verification token"));

        if (user.getTokenExpiry().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Token expired");
        }
        user.setUserStatus(UserStatus.ACTIVE);
        user.setVerificationToken(null);
        user.setTokenExpiry(null);
        user = userRepository.save(user);

        activityLogRepository.save(new ActivityLog("VERIFY_EMAIL", user.getEmail(),user.getEmail(),null));
        return user;
    }

    public User login(LoginRequest request)  {
      String email = request.getEmail();
      String password = request.getPassword();

      User user = userRepository.findByEmail(request.getEmail())
              .orElseThrow(()-> new RuntimeException("Invalid email or password"));

      if(user.getUserStatus() !=  UserStatus.ACTIVE){
          throw new RuntimeException("Please verify email before logging in");
      }
      if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
        throw new RuntimeException("Invalid email or password");
        }
      user.setLastLoginAt(LocalDateTime.now());
      userRepository.save(user);

      activityLogRepository.save(new ActivityLog("LOGIN", user.getEmail(), user.getEmail(), null));
      return user;

    }
}

