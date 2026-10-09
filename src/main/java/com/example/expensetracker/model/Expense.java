package com.example.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name ="expenses")
public class Expense {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    private BigDecimal amount;
    private String description;
    private LocalDate expenseDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name ="category_id")
    @JsonIgnore
    private Category category;


    // no-arg constructor
    public Expense(){};

    public Expense(User user, Category category, BigDecimal amount, LocalDate expenseDate,String description){
        this.category = category;
        this.user = user;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.description = description;
    }

        public Long getId(){
        return id;
        }

    public User getUser() {
        return user;
    }

    public Category getCategory(){
        return category;
    }

    public String getDescription(){
        return description;
    }

    public LocalDate getExpenseDate(){
        return  expenseDate;
    }

    public BigDecimal getAmount(){
        return amount;
    }

    public void setId(Long id){
        this.id =id;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setCategory(Category category){
        this.category = category;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }
}
