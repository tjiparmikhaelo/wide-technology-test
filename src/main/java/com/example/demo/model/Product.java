package com.example.demo.model;

import com.example.demo.enums.ProductType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Product extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @Enumerated(EnumType.STRING)
  private ProductType type;

  private String description;
  private BigDecimal price;
}
