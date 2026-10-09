package com.example.expensetracker.model;

import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
// users table
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true, length = 50)
    String email;
    @Column(nullable = false)
    String password;
    boolean enabled ;
    String verificationToken;
    LocalDateTime tokenExpiry;
    @Column(nullable = false)
    LocalDateTime createdAt = LocalDateTime.now();
    LocalDateTime lastLoginAt = LocalDateTime.now();
@Enumerated(EnumType.STRING)
@Column(nullable = false)
    private   Role role;
@Enumerated(EnumType.STRING)
@Column(nullable = false)
    private  UserStatus userStatus;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }



    public User(){

    }


    // constructor for users
    public User(Long id, String email, String password,
                String verificationToken, LocalDateTime tokenExpiry, Role role, UserStatus userStatus){
        this.id = id;
        this.email = email;
        this.password =password;
        this.verificationToken = verificationToken;
        this.tokenExpiry = tokenExpiry;
        this.role = role;
        this.userStatus = userStatus;
    }

    // getters and setters for Users
    public Long getId(){ return id;}

    public String getEmail(){ return  email; }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public String getPassword(){
        return password;
    }

    public String getVerificationToken(){
        return verificationToken;
    }

    public LocalDateTime getTokenExpiry() {
        return tokenExpiry;
    }

    public Role getRole() {
        return role;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    //setters for user field

    public void setId(Long id){
        this.id = id;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setRole(Role role){
        this.role =role;
    }

    public void setUserStatus(UserStatus userStatus){
        this.userStatus =userStatus;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public void setTokenExpiry(LocalDateTime tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }



    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
}
