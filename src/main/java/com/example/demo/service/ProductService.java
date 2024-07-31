package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  @Autowired
  private ProductRepository productRepository;

  public Page<Product> getAllProducts(Pageable pageable) {
    return productRepository.findAll(pageable);
  }

  public Product getProductById(Long id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not Found"));
  }

  public Product createProduct(Product product) {
    return productRepository.save(product);
  }

  public Product updateProduct(Long id, Product productDetails) {
    Product product = getProductById(id);

    product.setName(productDetails.getName());
    product.setType(productDetails.getType());
    product.setDescription(productDetails.getDescription());
    product.setPrice(productDetails.getPrice());

    return productRepository.save(product);
  }

  public void deleteProduct(Long id) {
    Product product = getProductById(id);
    productRepository.delete(product);
  }
}
