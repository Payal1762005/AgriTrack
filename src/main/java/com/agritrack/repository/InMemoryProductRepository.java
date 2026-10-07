package com.agritrack.repository;

import com.agritrack.model.FarmProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, FarmProduct> products;

    public InMemoryProductRepository() {
        products = new HashMap<>();
    }

    @Override
    public FarmProduct save(FarmProduct product) {
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }

        products.put(product.getProductId(), product);
        return product;
    }

    @Override
    public Optional<FarmProduct> findById(String productId) {
        return Optional.ofNullable(products.get(productId));
    }

    @Override
    public List<FarmProduct> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public boolean deleteById(String productId) {
        return products.remove(productId) != null;
    }

    @Override
    public boolean existsById(String productId) {
        return products.containsKey(productId);
    }
}
