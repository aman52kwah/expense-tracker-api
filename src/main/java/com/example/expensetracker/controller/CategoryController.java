package com.example.expensetracker.controller;

import com.example.expensetracker.dto.CategoryRequest;
import com.example.expensetracker.model.Category;
import com.example.expensetracker.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    //constructor injection
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }


    // GET CATEGORIES FOR USER
    @GetMapping
    public List<Category> getCategories(Authentication auth){
        String email = auth.getPrincipal().toString();
        return categoryService.getCategoriesForUser(email);
    }

    // CREATE USER CATEGORY
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody
                                                       CategoryRequest request, Authentication auth){
        String email = auth.getPrincipal().toString();
        Category category = categoryService.createCategory(email, request);
        return  ResponseEntity.status(201).body(category);
    }

    //UPDATE CATEGORY
    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody
    CategoryRequest request, Authentication auth){
        String email = auth.getPrincipal().toString();
        return categoryService.updateCategory(id,email,request);
    }

    // DELETE CATEGORY FOR SPECFIC  USER ONLY
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id, Authentication auth){
        String email = auth.getPrincipal().toString();
        categoryService.deleteCategory(id, email);
        return ResponseEntity.noContent().build();
    }



}
