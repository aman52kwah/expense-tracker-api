package com.example.expensetracker.controller;

import com.example.expensetracker.dto.ExpenseRequest;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    // inject expense service into controller
    public ExpenseController( ExpenseService expenseService){
        this.expenseService = expenseService;
    }


    // GET /api/expenses  - List user expenses
    @GetMapping
    public List<Expense> getExpenses(Authentication auth){
        String email = auth.getPrincipal().toString();
        return expenseService.getExpensesForUser(email);
    }

    //POST /api/expense - user create expense
    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody ExpenseRequest request,
                                                 Authentication auth){
        String email =auth.getPrincipal().toString();
        Expense expense = expenseService.createExpense(email, request);
        return ResponseEntity.status(201).body(expense);
    }

    //PUT /api/expense/{id} - update expense
    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @Valid @RequestBody
    ExpenseRequest request, Authentication auth){
        String email = auth.getPrincipal().toString();
        return expenseService.updateExpense(id, email, request);
    }

    //DELETE /api/expense/{id}  - delete expense
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteExpense(@PathVariable Long id, @Valid @RequestBody
     Authentication auth){
        String email = auth.getPrincipal().toString();
        expenseService.deleteExepense(id,email);
        return ResponseEntity.noContent().build();
    }

}
