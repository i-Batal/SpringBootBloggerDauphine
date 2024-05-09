package com.dauphine.blogger.services;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface CategoryService {

    @GetMapping("/")
    public List<String> getAllCategories();

    @GetMapping("/{id}")
    public String getCategoryById(@PathVariable int id);
    
    @PostMapping("/")
    public void createCategory(@RequestBody ElementRequest elementRequest);

    @PatchMapping("/")
    public void updateCategoryName(@RequestParam int id, @RequestParam String name);

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable int id);
}
