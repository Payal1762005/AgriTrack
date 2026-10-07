package com.agritrack.model;

public class Farmer {

    private final String farmerId;
    private final String name;
    private final String village;
    private final String contactNumber;

    public Farmer(String farmerId, String name, String village, String contactNumber) {
        if (farmerId == null || farmerId.isBlank()) {
            throw new IllegalArgumentException("Farmer ID must not be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be null or blank");
        }
        if (village == null || village.isBlank()) {
            throw new IllegalArgumentException("Village must not be null or blank");
        }
        if (contactNumber == null || contactNumber.isBlank()) {
            throw new IllegalArgumentException("Contact number must not be null or blank");
        }

        this.farmerId = farmerId;
        this.name = name;
        this.village = village;
        this.contactNumber = contactNumber;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public String getName() {
        return name;
    }

    public String getVillage() {
        return village;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    @Override
    public String toString() {
        return "Farmer{"
                + "farmerId='" + farmerId + '\''
                + ", name='" + name + '\''
                + ", village='" + village + '\''
                + ", contactNumber='" + contactNumber + '\''
                + '}';
    }
}
