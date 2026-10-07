package com.agritrack.service;

import com.agritrack.model.FarmProduct;
import com.agritrack.model.Order;
import com.agritrack.repository.OrderRepository;
import com.agritrack.repository.ProductRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public ReportService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public List<FarmProduct> getLowStockProducts() {
        return productRepository.findAll().stream()
                .filter(FarmProduct::isLowStock)
                .toList();
    }

    public double calculateTotalInventoryValue() {
        return productRepository.findAll().stream()
                .mapToDouble(product -> product.getQuantity() * product.getPrice())
                .sum();
    }

    public long getTotalOrderCount() {
        return orderRepository.findAll().size();
    }

    public double calculateTotalOrderValue() {
        return orderRepository.findAll().stream()
                .mapToDouble(order -> order.getQuantity() * order.getProduct().getPrice())
                .sum();
    }

    public Map<String, Long> getOrderCountByProduct() {
        return orderRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        order -> order.getProduct().getProductName(),
                        Collectors.counting()
                ));
    }
}
