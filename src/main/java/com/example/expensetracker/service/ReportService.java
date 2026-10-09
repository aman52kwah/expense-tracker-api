package com.example.expensetracker.service;

import com.example.expensetracker.dto.SummaryResponse;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ReportService(ExpenseRepository expenseRepository, UserRepository userRepository){
        this.expenseRepository =expenseRepository;
        this.userRepository = userRepository;
    }

    // fetch monthly summary
    public SummaryResponse getMonthlySummary(String email, String monthStr){
        YearMonth yearMonth = YearMonth.parse(monthStr); // "2026-10"
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Expense> expenses = expenseRepository.findByUserAndExpenseDateBetween(user,start,end);

        Map<String, BigDecimal>  byCategory = expenses.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getCategory().getName(),
                        Collectors.reducing(BigDecimal.ZERO, Expense::getAmount,BigDecimal::add)
                ));
        BigDecimal total = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        return  new SummaryResponse(monthStr,total, byCategory);
    }
}
