package com.example.senagist.controllers;

import com.example.senagist.models.User;
import com.example.senagist.models.UserDTO;
import com.example.senagist.models.UserLogin;
import com.example.senagist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<User> user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<?> getByRole(@PathVariable String role){
        List<User> user = userService.getByRole(role);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin) {
        User user = userService.getByEmail(userLogin.getEmail());
        return ResponseEntity.ok(Map.of("message", user));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        userService.create(user);
        return ResponseEntity.ok(Map.of("message", "User created", "data", user));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserDTO user) {
        userService.update(user);
        return ResponseEntity.ok(Map.of("message", "User updated", "data", user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(Map.of("message", "User deleted"));
    }
}
