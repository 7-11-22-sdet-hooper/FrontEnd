package com.ReviewSite.ReviewSite.controllers;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ReviewSite.ReviewSite.entity.User;
import com.ReviewSite.ReviewSite.repositories.UserRepository;

@Controller
public class UserController {
    private final UserRepository repository;
    UserController(UserRepository repository) {
        this.repository = repository;
    }
    @GetMapping("/users")
    List<User> all() {
        return (List<User>) repository.findAll();
      }
    public String currentUserName(User user) {
        return user.getName();
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
    return repository.save(newUser);
  }
  @GetMapping("/users/{id}")
  User one(@PathVariable Long id) {
    
    return repository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException());
  }
  @PutMapping("/users/{id}")
  User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
    
    return repository.findById(id)
      .map(user -> {
        user.setName(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        return repository.save(newUser(user));
      })
      .orElseGet(() -> {
        newUser.setId(id);
        return repository.save(newUser);
      });
    }
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
