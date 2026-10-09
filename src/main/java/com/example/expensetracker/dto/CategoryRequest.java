package com.example.expensetracker.dto;

public class CategoryRequest {
    private String name;
    private Long id;

    // no-arg constructor
    public CategoryRequest() {
    }

    public CategoryRequest(String name, Long id){
        this.name = name;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
