package com.example.expensetracker.service;

import com.example.expensetracker.dto.CategoryRequest;
import com.example.expensetracker.model.Category;
import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.CategoryRepository;
import com.example.expensetracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public  CategoryService(CategoryRepository categoryRepository, UserRepository userRepository){
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    // list all category by user
    public List<Category> getCategoriesForUser(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return  categoryRepository.findByUser(user);
    }
    // create category by user
    public Category createCategory(String email, CategoryRequest request){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Category category = new Category(request.getName(),user);
        return categoryRepository.save(category);
    }


    // update category
    public Category updateCategory(Long id, String email, CategoryRequest request){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Category not found"));

        if (!category.getUser().getEmail().equals(email)){
            throw new RuntimeException("Not authorized to update this category");
        }
        category.setName(request.getName());
        return categoryRepository.save(category);
    }

    // Delete category service
    public void deleteCategory(Long id, String email){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Category not found"));

        if(!category.getUser().getEmail().equals(email)){
            throw new RuntimeException("Not authorized to delete this category");
        }
        categoryRepository.delete(category);
    }
}
