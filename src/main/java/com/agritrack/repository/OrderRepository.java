package com.agritrack.repository;

import com.agritrack.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(String orderId);

    List<Order> findAll();

    boolean deleteById(String orderId);

    boolean existsById(String orderId);
}
