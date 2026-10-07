package com.agritrack.service;

import com.agritrack.exception.FarmerNotFoundException;
import com.agritrack.exception.InsufficientStockException;
import com.agritrack.exception.ProductNotFoundException;
import com.agritrack.model.Farmer;
import com.agritrack.model.Order;
import com.agritrack.model.Seed;
import com.agritrack.repository.InMemoryFarmerRepository;
import com.agritrack.repository.InMemoryOrderRepository;
import com.agritrack.repository.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderServiceTest {

    private InMemoryOrderRepository orderRepository;
    private InMemoryFarmerRepository farmerRepository;
    private InMemoryProductRepository productRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();
        farmerRepository = new InMemoryFarmerRepository();
        productRepository = new InMemoryProductRepository();
        orderService = new OrderService(orderRepository, farmerRepository, productRepository);

        Farmer farmer = new Farmer("F001", "Raj", "Pune", "9876543210");
        farmerRepository.save(farmer);

        Seed wheatSeed = new Seed(
                "S001",
                "Wheat Seed",
                100,
                500,
                true,
                "Wheat",
                95
        );
        productRepository.save(wheatSeed);
    }

    @Test
    void createOrderSuccessfully() {
        Order order = orderService.createOrder("O001", "F001", "S001", 20);

        assertEquals("O001", order.getOrderId());
        assertEquals("F001", order.getFarmer().getFarmerId());
        assertEquals("S001", order.getProduct().getProductId());
        assertEquals(20, order.getQuantity());
    }

    @Test
    void creatingOrderReducesProductStock() {
        orderService.createOrder("O001", "F001", "S001", 30);

        int remainingQuantity = productRepository.findById("S001")
                .orElseThrow()
                .getQuantity();

        assertEquals(70, remainingQuantity);
    }

    @Test
    void createOrderWithInsufficientStockThrowsException() {
        assertThrows(
                InsufficientStockException.class,
                () -> orderService.createOrder("O001", "F001", "S001", 150)
        );
    }

    @Test
    void createOrderWithUnknownFarmerThrowsException() {
        assertThrows(
                FarmerNotFoundException.class,
                () -> orderService.createOrder("O001", "UNKNOWN", "S001", 10)
        );
    }

    @Test
    void createOrderWithUnknownProductThrowsException() {
        assertThrows(
                ProductNotFoundException.class,
                () -> orderService.createOrder("O001", "F001", "UNKNOWN", 10)
        );
    }
}
