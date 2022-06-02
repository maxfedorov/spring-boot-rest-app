package com.example.springbootrestapp.controller;

import com.example.springbootrestapp.entity.User;
import com.example.springbootrestapp.errors.UserNotFoundException;
import com.example.springbootrestapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
public class UsersController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("users")
    public Iterable<User> getAllUser() {
        return userRepository.findAll();
    }

    @GetMapping("users/{id}")
    public User getUser(@PathVariable long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping(value = "users", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping(value = "users/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return userRepository.save(user);
    }

    @DeleteMapping("users/{id}")
    public void deleteUser(@PathVariable Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

}
