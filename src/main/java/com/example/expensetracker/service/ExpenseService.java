package com.example.expensetracker.service;

import com.example.expensetracker.dto.ExpenseRequest;
import com.example.expensetracker.model.Category;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.CategoryRepository;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


        // constructor injection
     public ExpenseService( ExpenseRepository expenseRepository,
                            CategoryRepository categoryRepository, UserRepository userRepository){
         this.categoryRepository =categoryRepository;
         this.userRepository = userRepository;
         this.expenseRepository = expenseRepository;
     }


     // method to get all expenses for a user
    public List<Expense> getExpensesForUser(String email){
         User user = userRepository.findByEmail(email)
                 .orElseThrow(()-> new RuntimeException("User not found"));
         return expenseRepository.findByUser(user);
    }

    // create an expense
    public Expense createExpense(String email, ExpenseRequest request){
         User user = userRepository.findByEmail(email)
                 .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(()-> new RuntimeException("Category not found"));

        // verify category belongs to this user
        if(!category.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Category does not belongs to user");
        }


        Expense expense = new Expense(
                user, category,request.getAmount(),request.getExpenseDate(), request.getDescription()
        );
        return  expenseRepository.save(expense);
    }


    // update an expense with ownership check
    public Expense updateExpense(Long id, String email, ExpenseRequest request){
         Expense expense = expenseRepository.findById(id)
                 .orElseThrow(()-> new RuntimeException("Expense not found"));

         //ownership check  to prevent IDOR

        if (!expense.getUser().getEmail().equals(email)){
            throw new RuntimeException("Not authorized to update this expense");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(()-> new RuntimeException("Category not found"));

        if (!category.getUser().getId().equals(expense.getUser().getId())){

            throw new RuntimeException("Category does not belongs to user");
        }

        expense.setAmount(request.getAmount());
        expense.setCategory(category);
        expense.setDescription(request.getDescription());
        expense.setExpenseDate(request.getExpenseDate());

        return expenseRepository.save(expense);
    }


    // delete an expense(with ownership check

    public void deleteExepense(Long id, String email){
         Expense expense = expenseRepository.findById(id)
                 .orElseThrow(()-> new RuntimeException("Expense not found"));

         if (!expense.getUser().getEmail().equals(email)){
             throw new RuntimeException("Not authorized to delete this expense");
         }
         expenseRepository.delete(expense);
    }


}


