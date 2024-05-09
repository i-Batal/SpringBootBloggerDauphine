package com.dauphine.blogger.services;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface PostService {
    @GetMapping("/")
    public List<String> getAllPostsByCreationDate(@RequestParam LocalDateTime creationDate);

    @GetMapping("/{categoryId}")
    public List<String> getPostById(@PathVariable int categoryId);

    @PostMapping("/")
    public void createPost(@RequestBody ElementRequest elementRequest);

    @PatchMapping("/")
    public void updatePostName(@RequestParam int id, @RequestParam String name);

    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable int id);
}