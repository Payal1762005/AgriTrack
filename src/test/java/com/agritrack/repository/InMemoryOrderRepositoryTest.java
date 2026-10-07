package com.agritrack.repository;

import com.agritrack.model.Farmer;
import com.agritrack.model.Order;
import com.agritrack.model.Seed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryOrderRepositoryTest {

    private InMemoryOrderRepository orderRepository;
    private Order order;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();

        Farmer farmer = new Farmer("F001", "Raj", "Pune", "9876543210");
        Seed product = new Seed("S001", "Wheat Seed", 100, 500, true, "Wheat", 95);
        order = new Order("O001", farmer, product, 20, LocalDate.of(2026, 1, 10));
    }

    @Test
    void saveAndFindById() {
        orderRepository.save(order);

        Optional<Order> foundOrder = orderRepository.findById("O001");

        assertTrue(foundOrder.isPresent());
        assertEquals("O001", foundOrder.orElseThrow().getOrderId());
    }

    @Test
    void findAllReturnsSavedOrders() {
        orderRepository.save(order);
        orderRepository.save(createSecondOrder());

        assertEquals(2, orderRepository.findAll().size());
    }

    @Test
    void existsByIdReturnsCorrectResult() {
        orderRepository.save(order);

        assertTrue(orderRepository.existsById("O001"));
        assertFalse(orderRepository.existsById("UNKNOWN"));
    }

    @Test
    void deleteByIdRemovesOrder() {
        orderRepository.save(order);

        assertTrue(orderRepository.deleteById("O001"));
        assertFalse(orderRepository.existsById("O001"));
    }

    @Test
    void findByUnknownIdReturnsEmpty() {
        assertTrue(orderRepository.findById("UNKNOWN").isEmpty());
    }

    private Order createSecondOrder() {
        Farmer farmer = new Farmer("F002", "Asha", "Nashik", "9876543211");
        Seed product = new Seed("S002", "Corn Seed", 80, 450, true, "Corn", 90);
        return new Order("O002", farmer, product, 10, LocalDate.of(2026, 1, 11));
    }
}
