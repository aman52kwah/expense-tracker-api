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


    public User(){}


    // constructor for users
    public User(Long id,String email, String password ,boolean enabled,
                String verificationToken, LocalDateTime tokenExpiry){
        this.id = id;
        this.email = email;
        this.password =password;
        this.enabled = false;
        this.verificationToken = verificationToken;
        this.tokenExpiry = tokenExpiry;
    }

    // getters and setters for Users
    public Long getId(){ return id;}

    public String getEmail(){ return  email; }

    public boolean isEnabled(){
        return enabled;
    }
     public boolean getEnabled(){
        return enabled;
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

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public void setTokenExpiry(LocalDateTime tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }
}
