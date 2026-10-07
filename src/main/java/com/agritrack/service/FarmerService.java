package com.agritrack.service;

import com.agritrack.exception.FarmerNotFoundException;
import com.agritrack.model.Farmer;
import com.agritrack.repository.FarmerRepository;

import java.util.List;
import java.util.Locale;

public class FarmerService {

    private final FarmerRepository farmerRepository;

    public FarmerService(FarmerRepository farmerRepository) {
        this.farmerRepository = farmerRepository;
    }

    public Farmer addFarmer(Farmer farmer) {
        if (farmer == null) {
            throw new IllegalArgumentException("Farmer must not be null");
        }
        return farmerRepository.save(farmer);
    }

    public Farmer getFarmerById(String farmerId) {
        return farmerRepository.findById(farmerId)
                .orElseThrow(() -> new FarmerNotFoundException(
                        "Farmer not found with ID: " + farmerId));
    }

    public List<Farmer> getAllFarmers() {
        return farmerRepository.findAll();
    }

    public List<Farmer> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Search keyword must not be null or blank");
        }

        String searchKeyword = keyword.toLowerCase(Locale.ROOT);
        return farmerRepository.findAll().stream()
                .filter(farmer -> farmer.getName().toLowerCase(Locale.ROOT)
                        .contains(searchKeyword))
                .toList();
    }

    public List<Farmer> findByVillage(String village) {
        if (village == null || village.isBlank()) {
            throw new IllegalArgumentException("Village must not be null or blank");
        }

        String searchVillage = village.toLowerCase(Locale.ROOT);
        return farmerRepository.findAll().stream()
                .filter(farmer -> farmer.getVillage().toLowerCase(Locale.ROOT)
                        .contains(searchVillage))
                .toList();
    }
}
