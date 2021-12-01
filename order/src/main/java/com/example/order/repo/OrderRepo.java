package com.example.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.order.model.Order;

public interface OrderRepo extends JpaRepository<Order, Integer> {
}

