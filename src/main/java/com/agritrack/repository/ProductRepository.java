package com.agritrack.repository;

import com.agritrack.model.FarmProduct;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    FarmProduct save(FarmProduct product);

    Optional<FarmProduct> findById(String productId);

    List<FarmProduct> findAll();

    boolean deleteById(String productId);

    boolean existsById(String productId);
}
