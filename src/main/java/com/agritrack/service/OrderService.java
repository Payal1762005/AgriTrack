package com.agritrack.service;

import com.agritrack.exception.FarmerNotFoundException;
import com.agritrack.exception.InsufficientStockException;
import com.agritrack.exception.ProductNotFoundException;
import com.agritrack.model.FarmProduct;
import com.agritrack.model.Farmer;
import com.agritrack.model.Order;
import com.agritrack.repository.FarmerRepository;
import com.agritrack.repository.OrderRepository;
import com.agritrack.repository.ProductRepository;

import java.time.LocalDate;
import java.util.List;

public class OrderService {

    private final OrderRepository orderRepository;
    private final FarmerRepository farmerRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        FarmerRepository farmerRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.farmerRepository = farmerRepository;
        this.productRepository = productRepository;
    }

    public Order createOrder(String orderId, String farmerId, String productId, int quantity) {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID must not be null or blank");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Order quantity must be greater than zero");
        }

        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new FarmerNotFoundException(
                        "Farmer not found with ID: " + farmerId));
        FarmProduct product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product not found with ID: " + productId));

        if (product.getQuantity() < quantity) {
            throw new InsufficientStockException(
                    "Insufficient stock for product ID: " + productId);
        }

        product.removeStock(quantity);

        Order order = new Order(orderId, farmer, product, quantity, LocalDate.now());
        return orderRepository.save(order);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Order not found with ID: " + orderId));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByFarmer(String farmerId) {
        validateIdentifier(farmerId, "Farmer ID");
        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new FarmerNotFoundException(
                        "Farmer not found with ID: " + farmerId));

        return orderRepository.findAll().stream()
                .filter(order -> order.getFarmer().getFarmerId().equals(farmer.getFarmerId()))
                .toList();
    }

    public List<Order> getOrdersByProduct(String productId) {
        validateIdentifier(productId, "Product ID");
        FarmProduct product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product not found with ID: " + productId));

        return orderRepository.findAll().stream()
                .filter(order -> order.getProduct().getProductId().equals(product.getProductId()))
                .toList();
    }

    private void validateIdentifier(String identifier, String fieldName) {
        if (identifier == null || identifier.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be null or blank");
        }
    }
}
