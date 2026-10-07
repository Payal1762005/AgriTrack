package com.agritrack.repository;

import com.agritrack.model.FarmProduct;
import com.agritrack.model.Seed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryProductRepositoryTest {

    private InMemoryProductRepository productRepository;
    private Seed product;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        product = new Seed("S001", "Wheat Seed", 100, 500, true, "Wheat", 95);
    }

    @Test
    void saveAndFindById() {
        productRepository.save(product);

        Optional<FarmProduct> foundProduct = productRepository.findById("S001");

        assertTrue(foundProduct.isPresent());
        assertEquals("S001", foundProduct.orElseThrow().getProductId());
    }

    @Test
    void findAllReturnsSavedProducts() {
        productRepository.save(product);
        productRepository.save(new Seed("S002", "Corn Seed", 50, 450, true, "Corn", 90));

        assertEquals(2, productRepository.findAll().size());
    }

    @Test
    void existsByIdReturnsCorrectResult() {
        productRepository.save(product);

        assertTrue(productRepository.existsById("S001"));
        assertFalse(productRepository.existsById("UNKNOWN"));
    }

    @Test
    void deleteByIdRemovesProduct() {
        productRepository.save(product);

        assertTrue(productRepository.deleteById("S001"));
        assertFalse(productRepository.existsById("S001"));
    }

    @Test
    void findByUnknownIdReturnsEmpty() {
        assertTrue(productRepository.findById("UNKNOWN").isEmpty());
    }
}
