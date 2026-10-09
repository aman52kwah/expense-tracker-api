package com.example.expensetracker.repository;

import com.example.expensetracker.model.Category;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
    List<Expense> findByUserAndExpenseDateBetween(User user, LocalDate start, LocalDate end);
    List<Expense> findByCategory(Category category);
    @Query("SELECT e.category.name, SUM(e.amount) FROM Expense e WHERE e.user = :user AND FUNCTION('DATE_FORMAT', e.expenseDate, '%Y-%m') = :month GROUP BY e.category.name")
    List<Object[]> findSummaryByUserAndMonth(@Param("user") User user, @Param("month") String month);

}

