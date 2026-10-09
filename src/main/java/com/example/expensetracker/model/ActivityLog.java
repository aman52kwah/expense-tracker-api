package com.example.expensetracker.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity_log")
public class ActivityLog {
    @Id
    @GeneratedValue
    Long id;
    @Column(nullable = false)
    String action; //signup,login,suspended etc
    @Column(nullable = false)
    String actorEmail;  // who performed the action
    String targetEmail; // affected user(nullable)
    String details;  // optional JSON/details
    @Column(nullable = false)
    LocalDateTime timestamp = LocalDateTime.now();


    //no-arg constructor
    public ActivityLog(){}
    public ActivityLog(String action, String actorEmail,String targetEmail, String details){
        this.action = action;
        this.actorEmail = actorEmail;
        this.targetEmail = targetEmail;
        this.details = details;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActorEmail() {
        return actorEmail;
    }

    public void setActorEmail(String actorEmail) {
        this.actorEmail = actorEmail;
    }

    public String getTargetEmail() {
        return targetEmail;
    }

    public void setTargetEmail(String targetEmail) {
        this.targetEmail = targetEmail;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
