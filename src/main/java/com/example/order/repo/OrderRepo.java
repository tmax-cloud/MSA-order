package com.example.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.order.model.OrderInfo;

public interface OrderRepo extends JpaRepository<OrderInfo, Integer> {
}

