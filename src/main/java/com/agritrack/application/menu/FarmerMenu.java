package com.agritrack.application.menu;

import com.agritrack.application.cli.ConsoleReader;
import com.agritrack.exception.FarmerNotFoundException;
import com.agritrack.model.Farmer;
import com.agritrack.service.FarmerService;

import java.util.List;

public class FarmerMenu {

    private final ConsoleReader consoleReader;
    private final FarmerService farmerService;

    public FarmerMenu(ConsoleReader consoleReader, FarmerService farmerService) {
        this.consoleReader = consoleReader;
        this.farmerService = farmerService;
    }

    public void start() {
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = consoleReader.readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1 -> addFarmer();
                    case 2 -> viewAllFarmers();
                    case 3 -> searchFarmersByName();
                    case 4 -> findFarmersByVillage();
                    case 5 -> running = false;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (FarmerNotFoundException | IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void displayMenu() {
        System.out.println("=================================");
        System.out.println("       FARMER MANAGEMENT");
        System.out.println("=================================");
        System.out.println("1. Add Farmer");
        System.out.println("2. View All Farmers");
        System.out.println("3. Search Farmer by Name");
        System.out.println("4. Find Farmers by Village");
        System.out.println("5. Back");
    }

    private void addFarmer() {
        String farmerId = consoleReader.readString("Farmer ID: ");
        String name = consoleReader.readString("Name: ");
        String village = consoleReader.readString("Village: ");
        String contactNumber = consoleReader.readString("Contact Number: ");

        Farmer farmer = new Farmer(farmerId, name, village, contactNumber);
        farmerService.addFarmer(farmer);
        System.out.println("Farmer added successfully.");
    }

    private void viewAllFarmers() {
        List<Farmer> farmers = farmerService.getAllFarmers();
        if (farmers.isEmpty()) {
            System.out.println("No farmers found.");
            return;
        }

        farmers.forEach(System.out::println);
    }

    private void searchFarmersByName() {
        String keyword = consoleReader.readString("Name keyword: ");
        List<Farmer> farmers = farmerService.searchByName(keyword);
        if (farmers.isEmpty()) {
            System.out.println("No farmers found.");
            return;
        }

        farmers.forEach(System.out::println);
    }

    private void findFarmersByVillage() {
        String village = consoleReader.readString("Village: ");
        List<Farmer> farmers = farmerService.findByVillage(village);
        if (farmers.isEmpty()) {
            System.out.println("No farmers found.");
            return;
        }

        farmers.forEach(System.out::println);
    }
}
