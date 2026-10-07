package com.agritrack.application.menu;

import com.agritrack.application.cli.ConsoleReader;
import com.agritrack.service.FarmerService;
import com.agritrack.service.OrderService;
import com.agritrack.service.ProductService;

public class MainMenu {

    private final ConsoleReader consoleReader;
    private final ProductService productService;
    private final FarmerService farmerService;
    private final OrderService orderService;
    private final ProductMenu productMenu;
    private final FarmerMenu farmerMenu;
    private final OrderMenu orderMenu;
    private final ReportMenu reportMenu;

    public MainMenu(ConsoleReader consoleReader,
                    ProductService productService,
                    FarmerService farmerService,
                    OrderService orderService) {
        this(consoleReader, productService, farmerService, orderService,
                new FarmerMenu(consoleReader, farmerService),
                new OrderMenu(consoleReader, orderService),
                null);
    }

    public MainMenu(ConsoleReader consoleReader,
                    ProductService productService,
                    FarmerService farmerService,
                    OrderService orderService,
                    FarmerMenu farmerMenu,
                    OrderMenu orderMenu,
                    ReportMenu reportMenu) {
        this.consoleReader = consoleReader;
        this.productService = productService;
        this.farmerService = farmerService;
        this.orderService = orderService;
        this.productMenu = new ProductMenu(consoleReader, productService);
        this.farmerMenu = farmerMenu;
        this.orderMenu = orderMenu;
        this.reportMenu = reportMenu;
    }

    public void start() {
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = consoleReader.readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> productMenu.start();
                case 2 -> farmerMenu.start();
                case 3 -> orderMenu.start();
                case 4 -> reportMenu.start();
                case 5 -> {
                    System.out.println("Thank you for using AgriTrack.");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("=================================");
        System.out.println("        AGRITRACK SYSTEM");
        System.out.println("=================================");
        System.out.println("1. Product Management");
        System.out.println("2. Farmer Management");
        System.out.println("3. Order Management");
        System.out.println("4. Reports");
        System.out.println("5. Exit");
        System.out.println();
    }
}
