package com.agritrack.model;

import java.time.LocalDate;

public class Order {

    private final String orderId;
    private final Farmer farmer;
    private final FarmProduct product;
    private final int quantity;
    private final LocalDate orderDate;

    public Order(String orderId, Farmer farmer, FarmProduct product, int quantity,
                 LocalDate orderDate) {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID must not be null or blank");
        }
        if (farmer == null) {
            throw new IllegalArgumentException("Farmer must not be null");
        }
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Order quantity must be greater than zero");
        }
        if (orderDate == null) {
            throw new IllegalArgumentException("Order date must not be null");
        }

        this.orderId = orderId;
        this.farmer = farmer;
        this.product = product;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public Farmer getFarmer() {
        return farmer;
    }

    public FarmProduct getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    @Override
    public String toString() {
        return "Order{"
                + "orderId='" + orderId + '\''
                + ", farmer=" + farmer
                + ", product=" + product
                + ", quantity=" + quantity
                + ", orderDate=" + orderDate
                + '}';
    }
}
