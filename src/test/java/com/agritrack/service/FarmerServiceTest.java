package com.agritrack.service;

import com.agritrack.exception.FarmerNotFoundException;
import com.agritrack.model.Farmer;
import com.agritrack.repository.InMemoryFarmerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FarmerServiceTest {

    private FarmerService farmerService;

    @BeforeEach
    void setUp() {
        InMemoryFarmerRepository farmerRepository = new InMemoryFarmerRepository();
        farmerService = new FarmerService(farmerRepository);

        Farmer farmer = new Farmer("F001", "Raj", "Pune", "9876543210");
        farmerService.addFarmer(farmer);
    }

    @Test
    void addFarmerAndGetFarmerById() {
        Farmer farmer = farmerService.getFarmerById("F001");

        assertEquals("F001", farmer.getFarmerId());
        assertEquals("Raj", farmer.getName());
        assertEquals("Pune", farmer.getVillage());
    }

    @Test
    void getAllFarmersReturnsAddedFarmers() {
        assertEquals(1, farmerService.getAllFarmers().size());
    }

    @Test
    void searchByNameIsCaseInsensitive() {
        var farmers = farmerService.searchByName("rAj");

        assertEquals(1, farmers.size());
        assertEquals("F001", farmers.get(0).getFarmerId());
    }

    @Test
    void findByVillageIsCaseInsensitive() {
        var farmers = farmerService.findByVillage("pUnE");

        assertEquals(1, farmers.size());
        assertEquals("F001", farmers.get(0).getFarmerId());
    }

    @Test
    void getUnknownFarmerThrowsException() {
        assertThrows(
                FarmerNotFoundException.class,
                () -> farmerService.getFarmerById("UNKNOWN")
        );
    }
}
