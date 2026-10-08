package com.example.expensetracker.model;

import jakarta.persistence.*;

@Entity
@Table(name ="categories" , uniqueConstraints = @UniqueConstraint(columnNames = {"name","user_id"}))
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    // no-arg constructor
    public Category(){};

    public Category( String name,  User user){
        this.name= name;
        this.user = user;

    }

    public Long getId(){
       return id;
    }

    public String getName(){
        return  name;
    }

    public User getUser(){
        return user;
    }


    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setUser(User user){
        this.user = user;
    }
}
