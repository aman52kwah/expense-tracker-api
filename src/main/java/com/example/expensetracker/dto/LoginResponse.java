package com.example.expensetracker.dto;

public class LoginResponse {
    private String token;


    // no-arg constructor
    public LoginResponse(){}

    public LoginResponse(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }
}
