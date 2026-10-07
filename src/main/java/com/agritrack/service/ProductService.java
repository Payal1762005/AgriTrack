package com.agritrack.service;

import com.agritrack.exception.InsufficientStockException;
import com.agritrack.exception.ProductNotFoundException;
import com.agritrack.model.FarmProduct;
import com.agritrack.repository.ProductRepository;

import java.util.List;
import java.util.Locale;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public FarmProduct addProduct(FarmProduct product) {
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }
        return productRepository.save(product);
    }

    public FarmProduct getProductById(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product not found with ID: " + productId));
    }

    public List<FarmProduct> getAllProducts() {
        return productRepository.findAll();
    }

    public void addStock(String productId, int quantity) {
        FarmProduct product = getProductById(productId);
        product.addStock(quantity);
    }

    public void removeStock(String productId, int quantity) {
        FarmProduct product = getProductById(productId);
        if (quantity > product.getQuantity()) {
            throw new InsufficientStockException(
                    "Insufficient stock for product ID: " + productId);
        }
        product.removeStock(quantity);
    }

    public List<FarmProduct> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Search keyword must not be null or blank");
        }

        String searchKeyword = keyword.toLowerCase(Locale.ROOT);
        return productRepository.findAll().stream()
                .filter(product -> product.getProductName().toLowerCase(Locale.ROOT)
                        .contains(searchKeyword))
                .toList();
    }

    public List<FarmProduct> getLowStockProducts() {
        return productRepository.findAll().stream()
                .filter(FarmProduct::isLowStock)
                .toList();
    }

    public List<FarmProduct> getProductsByType(String productType) {
        if (productType == null || productType.isBlank()) {
            throw new IllegalArgumentException("Product type must not be null or blank");
        }

        return productRepository.findAll().stream()
                .filter(product -> product.getProductType().equalsIgnoreCase(productType))
                .toList();
    }
}
