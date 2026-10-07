package com.agritrack.repository;

import com.agritrack.model.Farmer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryFarmerRepositoryTest {

    private InMemoryFarmerRepository farmerRepository;
    private Farmer farmer;

    @BeforeEach
    void setUp() {
        farmerRepository = new InMemoryFarmerRepository();
        farmer = new Farmer("F001", "Raj", "Pune", "9876543210");
    }

    @Test
    void saveAndFindById() {
        farmerRepository.save(farmer);

        Optional<Farmer> foundFarmer = farmerRepository.findById("F001");

        assertTrue(foundFarmer.isPresent());
        assertEquals("F001", foundFarmer.orElseThrow().getFarmerId());
    }

    @Test
    void findAllReturnsSavedFarmers() {
        farmerRepository.save(farmer);
        farmerRepository.save(new Farmer("F002", "Asha", "Nashik", "9876543211"));

        assertEquals(2, farmerRepository.findAll().size());
    }

    @Test
    void existsByIdReturnsCorrectResult() {
        farmerRepository.save(farmer);

        assertTrue(farmerRepository.existsById("F001"));
        assertFalse(farmerRepository.existsById("UNKNOWN"));
    }

    @Test
    void deleteByIdRemovesFarmer() {
        farmerRepository.save(farmer);

        assertTrue(farmerRepository.deleteById("F001"));
        assertFalse(farmerRepository.existsById("F001"));
    }

    @Test
    void findByUnknownIdReturnsEmpty() {
        assertTrue(farmerRepository.findById("UNKNOWN").isEmpty());
    }
}
