package com.example.demo.controller;

import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDto registrationDto) {
    User user = userService.registerUser(registrationDto.getUsername(), registrationDto.getPassword());

    return ResponseEntity.ok(user);
  }

  @PutMapping("/user/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
    return ResponseEntity.ok(userService.updateUser(id, userDetails));
  }
}
