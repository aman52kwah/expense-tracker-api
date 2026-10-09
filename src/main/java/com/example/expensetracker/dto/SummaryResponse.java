package com.example.expensetracker.dto;

import java.math.BigDecimal;
import java.util.Map;

public class SummaryResponse {
    private String month;
    private BigDecimal total;
    private Map<String, BigDecimal> byCategory;



    public SummaryResponse(String month, BigDecimal total, Map<String,BigDecimal> byCategory){
        this.month = month;
        this.total = total;
        this.byCategory =byCategory;
    }
    public String getMonth() {
        return month;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Map<String, BigDecimal> getByCategory() {
        return byCategory;
    }


}
