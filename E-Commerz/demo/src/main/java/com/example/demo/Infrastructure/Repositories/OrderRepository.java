package com.example.demo.Infrastructure.Repositories;

import com.example.demo.Domain.Orders;
import com.example.demo.Infrastructure.Controllers.DTOs.Components.Orders.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    public List<Orders> findAll();
    public Optional<Orders> findByOrderId(int orderId);
    public Optional<List<Orders>> findByStatus(Status status);
    public Orders save(Orders orders);
    public void deleteById(int id);
}
