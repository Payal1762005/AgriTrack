package com.agritrack.repository;

import com.agritrack.model.Farmer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryFarmerRepository implements FarmerRepository {

    private final Map<String, Farmer> farmers;

    public InMemoryFarmerRepository() {
        farmers = new HashMap<>();
    }

    @Override
    public Farmer save(Farmer farmer) {
        if (farmer == null) {
            throw new IllegalArgumentException("Farmer must not be null");
        }

        farmers.put(farmer.getFarmerId(), farmer);
        return farmer;
    }

    @Override
    public Optional<Farmer> findById(String farmerId) {
        return Optional.ofNullable(farmers.get(farmerId));
    }

    @Override
    public List<Farmer> findAll() {
        return new ArrayList<>(farmers.values());
    }

    @Override
    public boolean deleteById(String farmerId) {
        return farmers.remove(farmerId) != null;
    }

    @Override
    public boolean existsById(String farmerId) {
        return farmers.containsKey(farmerId);
    }
}
