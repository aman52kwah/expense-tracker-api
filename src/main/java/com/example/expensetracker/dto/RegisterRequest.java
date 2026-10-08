package com.example.expensetracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegisterRequest {
    @Email(message = "Invalid email format")
    @NotBlank(message =" Email is required")
    private  String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message ="Password must be at least 6 characters")
    private  String password;

    //getters and setters

    //no-arg constructor
    public RegisterRequest(){}

    public RegisterRequest( String email, String password){
        this.email = email;
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
