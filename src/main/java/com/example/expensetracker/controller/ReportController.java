package com.example.expensetracker.controller;

import com.example.expensetracker.dto.SummaryResponse;
import com.example.expensetracker.service.ReportService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    // get Summary
    @GetMapping("/summary")
    public SummaryResponse getSummary(@RequestParam String month, Authentication auth){
        String email = auth.getPrincipal().toString();
        return reportService.getMonthlySummary(email,month);
    }
}
