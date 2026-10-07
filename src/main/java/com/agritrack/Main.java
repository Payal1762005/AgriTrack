package com.agritrack;

import com.agritrack.application.cli.ConsoleReader;
import com.agritrack.application.menu.FarmerMenu;
import com.agritrack.application.menu.MainMenu;
import com.agritrack.application.menu.OrderMenu;
import com.agritrack.application.menu.ReportMenu;
import com.agritrack.model.CropProtectionProduct;
import com.agritrack.model.Fertilizer;
import com.agritrack.model.Farmer;
import com.agritrack.model.Seed;
import com.agritrack.repository.FarmerRepository;
import com.agritrack.repository.InMemoryFarmerRepository;
import com.agritrack.repository.InMemoryOrderRepository;
import com.agritrack.repository.InMemoryProductRepository;
import com.agritrack.repository.OrderRepository;
import com.agritrack.repository.ProductRepository;
import com.agritrack.service.FarmerService;
import com.agritrack.service.OrderService;
import com.agritrack.service.ProductService;
import com.agritrack.service.ReportService;

public class Main {

    public static void main(String[] args) {
        ProductRepository productRepository = new InMemoryProductRepository();
        FarmerRepository farmerRepository = new InMemoryFarmerRepository();
        OrderRepository orderRepository = new InMemoryOrderRepository();

        ProductService productService = new ProductService(productRepository);
        FarmerService farmerService = new FarmerService(farmerRepository);
        OrderService orderService = new OrderService(
                orderRepository,
                farmerRepository,
                productRepository
        );

        loadSampleData(productService, farmerService);

        ConsoleReader consoleReader = new ConsoleReader();
        ReportService reportService = new ReportService(productRepository, orderRepository);
        ReportMenu reportMenu = new ReportMenu(consoleReader, reportService);
        MainMenu mainMenu = new MainMenu(
                consoleReader,
                productService,
                farmerService,
                orderService,
                new FarmerMenu(consoleReader, farmerService),
                new OrderMenu(consoleReader, orderService),
                reportMenu
        );

        mainMenu.start();
        consoleReader.close();
    }

    private static void loadSampleData(ProductService productService,
                                       FarmerService farmerService) {
        productService.addProduct(new Seed(
                "S001", "Wheat Seed", 100, 500, true, "Wheat", 95
        ));
        productService.addProduct(new Fertilizer(
                "F001", "NPK Fertilizer", 50, 800, true, "Granular", "10-26-26"
        ));
        productService.addProduct(new CropProtectionProduct(
                "P001", "Crop Shield", 25, 1200, true, "Fungicide", 14
        ));

        farmerService.addFarmer(new Farmer(
                "FR001", "Raj Patil", "Kolhapur", "9876543210"
        ));
    }
}
