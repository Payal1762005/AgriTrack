package com.agritrack.repository;

import com.agritrack.model.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryOrderRepository implements OrderRepository {

    private final Map<String, Order> orders;

    public InMemoryOrderRepository() {
        orders = new HashMap<>();
    }

    @Override
    public Order save(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order must not be null");
        }

        orders.put(order.getOrderId(), order);
        return order;
    }

    @Override
    public Optional<Order> findById(String orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public boolean deleteById(String orderId) {
        return orders.remove(orderId) != null;
    }

    @Override
    public boolean existsById(String orderId) {
        return orders.containsKey(orderId);
    }
}
