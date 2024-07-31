package com.example.demo.service;

import com.example.demo.enums.OrderStatus;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderService {
  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UserRepository userRepository;

  public Order getOrderById(Long id) {
    return orderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
  }

  public Order addProductToCart(Long productId, int quantity, String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new ResourceNotFoundException("User not found");
    }

    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    Order cart = orderRepository.findByUserAndStatus(user, OrderStatus.CART)
        .orElseGet(() -> {
          Order newCart = new Order();
          newCart.setUser(user);
          newCart.setStatus(OrderStatus.CART);
          newCart.setOrderDate(LocalDateTime.now());

          return newCart;
        });

    OrderItem existingItem = cart.getItems().stream()
        .filter(item -> item.getProduct().getId().equals(productId))
        .findFirst()
        .orElse(null);

    if (existingItem != null) {
      existingItem.setQuantity(existingItem.getQuantity() + quantity);
    } else {
      OrderItem newItem = new OrderItem();
      newItem.setProduct(product);
      newItem.setQuantity(quantity);
      newItem.setOrder(cart);
      cart.getItems().add(newItem);
    }

    cart.setTotalAmount(calculateTotalAmount(cart));

    orderRepository.save(cart);

    return cart;
  }

  public Order placeOrder(Long orderId) {
    Order order = getOrderById(orderId);

    return orderRepository.save(order);
  }

  private BigDecimal calculateTotalAmount(Order order) {
    return order.getItems().stream()
        .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
