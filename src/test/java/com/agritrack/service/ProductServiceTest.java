package com.agritrack.service;

import com.agritrack.exception.InsufficientStockException;
import com.agritrack.model.FarmProduct;
import com.agritrack.model.Seed;
import com.agritrack.repository.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        productService = new ProductService(productRepository);

        Seed wheatSeed = new Seed(
                "S001",
                "Wheat Seed",
                100,
                500,
                true,
                "Wheat",
                95
        );
        productService.addProduct(wheatSeed);
    }

    @Test
    void addProductAndGetProductById() {
        FarmProduct product = productService.getProductById("S001");

        assertEquals("S001", product.getProductId());
        assertEquals("Wheat Seed", product.getProductName());
        assertEquals(100, product.getQuantity());
    }

    @Test
    void removeStockReducesQuantity() {
        productService.removeStock("S001", 30);

        assertEquals(70, productService.getProductById("S001").getQuantity());
    }

    @Test
    void removeStockWithInsufficientQuantityThrowsException() {
        assertThrows(
                InsufficientStockException.class,
                () -> productService.removeStock("S001", 150)
        );
    }

    @Test
    void searchByNameIsCaseInsensitive() {
        List<FarmProduct> products = productService.searchByName("wHEAT");

        assertEquals(1, products.size());
        assertEquals("S001", products.get(0).getProductId());
    }

    @Test
    void lowStockProductIsReturned() {
        Seed lowStockSeed = new Seed(
                "S002",
                "Corn Seed",
                10,
                450,
                true,
                "Corn",
                90
        );
        productService.addProduct(lowStockSeed);

        List<FarmProduct> lowStockProducts = productService.getLowStockProducts();

        assertTrue(lowStockProducts.stream()
                .anyMatch(product -> product.getProductId().equals("S002")));
    }
}
