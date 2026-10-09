package com.example.expensetracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "default_categories")
public class DefaultCategory {
    @Id
    @GeneratedValue Long id;
    @Column(nullable = false, unique = true) String name;

    public  DefaultCategory(){}

    public DefaultCategory(Long id, String name){
        this.id =id;
        this.name = name;
    }

    // setters
    public Long getId(){return id;}
    public String getName(){return name;}

    public void setName(String name){
        this.name = name;
    }

}
