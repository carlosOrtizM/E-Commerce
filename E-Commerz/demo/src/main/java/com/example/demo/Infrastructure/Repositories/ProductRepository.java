package com.example.demo.Infrastructure.Repositories;

import com.example.demo.Domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products,Integer> {
    public List<Products> findAll();
    public Optional<Products> findByProductId(int productId);
    public Optional<Products> findByProductName(String productName);
    public Products save(Products products);
    public void deleteById(int productId);

}
