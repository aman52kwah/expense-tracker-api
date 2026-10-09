package com.example.expensetracker.repository;


import com.example.expensetracker.model.DefaultCategory;

import org.springframework.data.jpa.repository.JpaRepository;



public interface DefaultCategoryRepository  extends JpaRepository<DefaultCategory,Long> {

}
