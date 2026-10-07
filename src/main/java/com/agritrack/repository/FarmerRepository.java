package com.agritrack.repository;

import com.agritrack.model.Farmer;

import java.util.List;
import java.util.Optional;

public interface FarmerRepository {

    Farmer save(Farmer farmer);

    Optional<Farmer> findById(String farmerId);

    List<Farmer> findAll();

    boolean deleteById(String farmerId);

    boolean existsById(String farmerId);
}
