package com.agritrack.application.menu;

import com.agritrack.application.cli.ConsoleReader;
import com.agritrack.model.FarmProduct;
import com.agritrack.service.ReportService;

import java.util.List;
import java.util.Map;

public class ReportMenu {

    private final ConsoleReader consoleReader;
    private final ReportService reportService;

    public ReportMenu(ConsoleReader consoleReader, ReportService reportService) {
        this.consoleReader = consoleReader;
        this.reportService = reportService;
    }

    public void start() {
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = consoleReader.readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> showLowStockReport();
                case 2 -> showTotalInventoryValue();
                case 3 -> showTotalOrderCount();
                case 4 -> showTotalOrderValue();
                case 5 -> showOrderCountByProduct();
                case 6 -> running = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("=================================");
        System.out.println("           REPORTS");
        System.out.println("=================================");
        System.out.println("1. Low Stock Report");
        System.out.println("2. Total Inventory Value");
        System.out.println("3. Total Order Count");
        System.out.println("4. Total Order Value");
        System.out.println("5. Order Count by Product");
        System.out.println("6. Back");
    }

    private void showLowStockReport() {
        List<FarmProduct> products = reportService.getLowStockProducts();
        if (products.isEmpty()) {
            System.out.println("No low-stock products found.");
            return;
        }

        products.forEach(System.out::println);
    }

    private void showTotalInventoryValue() {
        double totalValue = reportService.calculateTotalInventoryValue();
        System.out.println("Total Inventory Value: " + totalValue);
    }

    private void showTotalOrderCount() {
        long totalOrders = reportService.getTotalOrderCount();
        System.out.println("Total Orders: " + totalOrders);
    }

    private void showTotalOrderValue() {
        double totalValue = reportService.calculateTotalOrderValue();
        System.out.println("Total Order Value: " + totalValue);
    }

    private void showOrderCountByProduct() {
        Map<String, Long> orderCounts = reportService.getOrderCountByProduct();
        if (orderCounts.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        orderCounts.forEach((productName, count) ->
                System.out.println(productName + ": " + count));
    }
}
