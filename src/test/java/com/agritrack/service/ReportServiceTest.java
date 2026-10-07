package com.agritrack.service;

import com.agritrack.model.Farmer;
import com.agritrack.model.Fertilizer;
import com.agritrack.model.Seed;
import com.agritrack.repository.InMemoryFarmerRepository;
import com.agritrack.repository.InMemoryOrderRepository;
import com.agritrack.repository.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportServiceTest {

    private InMemoryProductRepository productRepository;
    private InMemoryOrderRepository orderRepository;
    private InMemoryFarmerRepository farmerRepository;
    private ProductService productService;
    private OrderService orderService;
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        orderRepository = new InMemoryOrderRepository();
        farmerRepository = new InMemoryFarmerRepository();
        productService = new ProductService(productRepository);
        orderService = new OrderService(orderRepository, farmerRepository, productRepository);
        reportService = new ReportService(productRepository, orderRepository);

        farmerRepository.save(new Farmer("F001", "Raj", "Pune", "9876543210"));
    }

    @Test
    void getLowStockProductsReturnsLowStockProduct() {
        Seed lowStockSeed = new Seed("S001", "Wheat Seed", 10, 500, true, "Wheat", 95);
        productService.addProduct(lowStockSeed);

        List<com.agritrack.model.FarmProduct> lowStockProducts =
                reportService.getLowStockProducts();

        assertTrue(lowStockProducts.contains(lowStockSeed));
    }

    @Test
    void calculateTotalInventoryValueReturnsCorrectTotal() {
        productService.addProduct(new Seed("S001", "Wheat Seed", 10, 100, true, "Wheat", 95));
        productService.addProduct(new Fertilizer(
                "F001", "NPK Fertilizer", 5, 200, true, "Granular", "10-26-26"
        ));

        double totalValue = reportService.calculateTotalInventoryValue();

        assertEquals(2000.0, totalValue);
    }

    @Test
    void getTotalOrderCountReturnsCorrectCount() {
        productService.addProduct(new Seed("S001", "Wheat Seed", 100, 500, true, "Wheat", 95));
        orderService.createOrder("O001", "F001", "S001", 10);
        orderService.createOrder("O002", "F001", "S001", 20);

        assertEquals(2, reportService.getTotalOrderCount());
    }

    @Test
    void calculateTotalOrderValueReturnsCorrectTotal() {
        productService.addProduct(new Seed("S001", "Wheat Seed", 100, 500, true, "Wheat", 95));
        productService.addProduct(new Fertilizer(
                "F001", "NPK Fertilizer", 50, 800, true, "Granular", "10-26-26"
        ));

        orderService.createOrder("O001", "F001", "S001", 2);
        orderService.createOrder("O002", "F001", "F001", 3);

        assertEquals(3400.0, reportService.calculateTotalOrderValue());
    }

    @Test
    void getOrderCountByProductReturnsCorrectCounts() {
        productService.addProduct(new Seed("S001", "Wheat Seed", 100, 500, true, "Wheat", 95));
        productService.addProduct(new Fertilizer(
                "F001", "NPK Fertilizer", 50, 800, true, "Granular", "10-26-26"
        ));

        orderService.createOrder("O001", "F001", "S001", 10);
        orderService.createOrder("O002", "F001", "S001", 20);
        orderService.createOrder("O003", "F001", "F001", 5);

        Map<String, Long> orderCounts = reportService.getOrderCountByProduct();

        assertEquals(2L, orderCounts.get("Wheat Seed"));
        assertEquals(1L, orderCounts.get("NPK Fertilizer"));
        assertTrue(orderCounts.containsKey("Wheat Seed"));
        assertTrue(orderCounts.containsKey("NPK Fertilizer"));
    }
}
