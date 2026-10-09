package com.example.expensetracker.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseRequest {
    @NotNull @DecimalMin("0.01")
    private BigDecimal amount;
    private String description;
    @NotNull
    private LocalDate expenseDate;
    @NotNull
    private Long catergoryId; // cleint references category by id


    // no-arg constructor
    public ExpenseRequest(){}

    public  ExpenseRequest(BigDecimal amount, String description, LocalDate expenseDate, Long catergoryId){
        this.amount = amount;
        this.description = description;
        this.expenseDate = expenseDate;
        this.catergoryId = catergoryId;
    }


    public @NotNull @DecimalMin("0.01") BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@NotNull @DecimalMin("0.01") BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public @NotNull LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(@NotNull LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public @NotNull Long getCatergoryId() {
     return catergoryId;
    }

    public void setCatergoryId(@NotNull Long catergoryId) {
        this.catergoryId = catergoryId;
    }
}
