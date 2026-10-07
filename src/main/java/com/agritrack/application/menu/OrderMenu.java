package com.agritrack.application.menu;

import com.agritrack.application.cli.ConsoleReader;
import com.agritrack.exception.FarmerNotFoundException;
import com.agritrack.exception.InsufficientStockException;
import com.agritrack.exception.ProductNotFoundException;
import com.agritrack.model.Order;
import com.agritrack.service.OrderService;

import java.util.List;

public class OrderMenu {

    private final ConsoleReader consoleReader;
    private final OrderService orderService;

    public OrderMenu(ConsoleReader consoleReader, OrderService orderService) {
        this.consoleReader = consoleReader;
        this.orderService = orderService;
    }

    public void start() {
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = consoleReader.readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1 -> createOrder();
                    case 2 -> viewAllOrders();
                    case 3 -> findOrderById();
                    case 4 -> viewOrdersByFarmer();
                    case 5 -> viewOrdersByProduct();
                    case 6 -> running = false;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (FarmerNotFoundException
                     | ProductNotFoundException
                     | InsufficientStockException
                     | IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void displayMenu() {
        System.out.println("=================================");
        System.out.println("        ORDER MANAGEMENT");
        System.out.println("=================================");
        System.out.println("1. Create Order");
        System.out.println("2. View All Orders");
        System.out.println("3. Find Order by ID");
        System.out.println("4. View Orders by Farmer");
        System.out.println("5. View Orders by Product");
        System.out.println("6. Back");
    }

    private void createOrder() {
        String orderId = consoleReader.readString("Order ID: ");
        String farmerId = consoleReader.readString("Farmer ID: ");
        String productId = consoleReader.readString("Product ID: ");
        int quantity = consoleReader.readInt("Quantity: ");

        Order order = orderService.createOrder(orderId, farmerId, productId, quantity);
        System.out.println("Order created successfully.");
        System.out.println(order);
    }

    private void viewAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        displayOrders(orders);
    }

    private void findOrderById() {
        String orderId = consoleReader.readString("Order ID: ");
        Order order = orderService.getOrderById(orderId);
        System.out.println(order);
    }

    private void viewOrdersByFarmer() {
        String farmerId = consoleReader.readString("Farmer ID: ");
        List<Order> orders = orderService.getOrdersByFarmer(farmerId);
        displayOrders(orders);
    }

    private void viewOrdersByProduct() {
        String productId = consoleReader.readString("Product ID: ");
        List<Order> orders = orderService.getOrdersByProduct(productId);
        displayOrders(orders);
    }

    private void displayOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        orders.forEach(System.out::println);
    }
}
