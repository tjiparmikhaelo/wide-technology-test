package com.example.demo.repository;

import com.example.demo.enums.OrderStatus;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  Optional<Order> findByStatus(OrderStatus status);

  Optional<Order> findByUserAndStatus(User user, OrderStatus status);
}
