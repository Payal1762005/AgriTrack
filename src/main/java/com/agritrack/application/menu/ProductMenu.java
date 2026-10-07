package com.agritrack.application.menu;

import com.agritrack.application.cli.ConsoleReader;
import com.agritrack.exception.InsufficientStockException;
import com.agritrack.exception.ProductNotFoundException;
import com.agritrack.model.CropProtectionProduct;
import com.agritrack.model.FarmProduct;
import com.agritrack.model.Fertilizer;
import com.agritrack.model.Seed;
import com.agritrack.service.ProductService;

import java.util.List;

public class ProductMenu {

    private final ConsoleReader consoleReader;
    private final ProductService productService;

    public ProductMenu(ConsoleReader consoleReader, ProductService productService) {
        this.consoleReader = consoleReader;
        this.productService = productService;
    }

    public void start() {
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = consoleReader.readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1 -> addProduct();
                    case 4 -> addStock();
                    case 5 -> removeStock();
                    case 2 -> viewAllProducts();
                    case 3 -> searchProducts();
                    case 6 -> showLowStockProducts();
                    case 7 -> running = false;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (ProductNotFoundException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void addProduct() {
        System.out.println("=================================");
        System.out.println("          ADD PRODUCT");
        System.out.println("=================================");
        System.out.println("1. Seed");
        System.out.println("2. Fertilizer");
        System.out.println("3. Crop Protection Product");
        System.out.println("4. Back");

        int productType = consoleReader.readInt("Enter product type: ");

        try {
            FarmProduct product;
            switch (productType) {
                case 1 -> product = createSeed();
                case 2 -> product = createFertilizer();
                case 3 -> product = createCropProtectionProduct();
                case 4 -> {
                    return;
                }
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    return;
                }
            }

            productService.addProduct(product);
            System.out.println("Product added successfully.");
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private Seed createSeed() {
        String productId = consoleReader.readString("Product ID: ");
        String productName = consoleReader.readString("Product Name: ");
        int quantity = consoleReader.readInt("Quantity: ");
        double price = consoleReader.readDouble("Price: ");
        boolean active = readActiveStatus();
        String cropType = consoleReader.readString("Crop Type: ");
        double germinationRate = consoleReader.readDouble("Germination Rate: ");

        return new Seed(productId, productName, quantity, price, active,
                cropType, germinationRate);
    }

    private Fertilizer createFertilizer() {
        String productId = consoleReader.readString("Product ID: ");
        String productName = consoleReader.readString("Product Name: ");
        int quantity = consoleReader.readInt("Quantity: ");
        double price = consoleReader.readDouble("Price: ");
        boolean active = readActiveStatus();
        String fertilizerType = consoleReader.readString("Fertilizer Type: ");
        String npkRatio = consoleReader.readString("NPK Ratio: ");

        return new Fertilizer(productId, productName, quantity, price, active,
                fertilizerType, npkRatio);
    }

    private CropProtectionProduct createCropProtectionProduct() {
        String productId = consoleReader.readString("Product ID: ");
        String productName = consoleReader.readString("Product Name: ");
        int quantity = consoleReader.readInt("Quantity: ");
        double price = consoleReader.readDouble("Price: ");
        boolean active = readActiveStatus();
        String productCategory = consoleReader.readString("Product Category: ");
        int safetyIntervalDays = consoleReader.readInt("Safety Interval Days: ");

        return new CropProtectionProduct(productId, productName, quantity, price, active,
                productCategory, safetyIntervalDays);
    }

    private boolean readActiveStatus() {
        int activeStatus = consoleReader.readInt("Active? (1 = Yes, 0 = No): ");
        if (activeStatus != 0 && activeStatus != 1) {
            throw new IllegalArgumentException("Active status must be 1 or 0");
        }
        return activeStatus == 1;
    }

    private void addStock() {
        try {
            String productId = consoleReader.readString("Product ID: ");
            int quantity = consoleReader.readInt("Quantity to add: ");

            productService.addStock(productId, quantity);
            System.out.println("Stock added successfully.");

            FarmProduct product = productService.getProductById(productId);
            System.out.println("Updated quantity: " + product.getQuantity());
        } catch (ProductNotFoundException | IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void removeStock() {
        try {
            String productId = consoleReader.readString("Product ID: ");
            int quantity = consoleReader.readInt("Quantity to remove: ");

            productService.removeStock(productId, quantity);
            System.out.println("Stock removed successfully.");

            FarmProduct product = productService.getProductById(productId);
            System.out.println("Updated quantity: " + product.getQuantity());
        } catch (ProductNotFoundException | InsufficientStockException
                 | IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void displayMenu() {
        System.out.println("=================================");
        System.out.println("       PRODUCT MANAGEMENT");
        System.out.println("=================================");
        System.out.println("1. Add Product");
        System.out.println("2. View All Products");
        System.out.println("3. Search Product");
        System.out.println("4. Add Stock");
        System.out.println("5. Remove Stock");
        System.out.println("6. Low Stock Report");
        System.out.println("7. Back");
    }

    private void viewAllProducts() {
        List<FarmProduct> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        products.forEach(System.out::println);
    }

    private void searchProducts() {
        String keyword = consoleReader.readString("Enter product name: ");
        List<FarmProduct> products = productService.searchByName(keyword);
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        products.forEach(System.out::println);
    }

    private void showLowStockProducts() {
        List<FarmProduct> products = productService.getLowStockProducts();
        if (products.isEmpty()) {
            System.out.println("No low-stock products found.");
            return;
        }

        products.forEach(System.out::println);
    }
}
