package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  @Autowired
  private OrderService orderService;

  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    return ResponseEntity.ok(orderService.getOrderById(id));
  }

  @PostMapping("/cart/add")
  public ResponseEntity<Order> addProductToCart(@RequestParam Long productId, @RequestParam int quantity, Principal principal) {
    Order cart = orderService.addProductToCart(productId, quantity, principal.getName());

    return ResponseEntity.status(HttpStatus.CREATED).body(cart);
  }

  @PostMapping("/{id}/place")
  public ResponseEntity<Order> placeOrder(@PathVariable Long id) {
    return ResponseEntity.ok(orderService.placeOrder(id));
  }
}
