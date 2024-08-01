package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public User registerUser(String username, String password) {
    if (userRepository.findByUsername(username) != null) {
      throw new UsernameNotFoundException("Username already exists!!");
    }

    User user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));

    return userRepository.save(user);
  }

  public User getUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  public User updateUser(Long id, User userDetails) {
    User user = getUserById(id);

    user.setAddress(userDetails.getAddress());

    return userRepository.save(user);
  }
}
