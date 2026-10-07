package com.agritrack.model;

public class Seed extends FarmProduct {

    private final String cropType;
    private final double germinationRate;

    public Seed(String productId, String productName, int quantity, double price,
                boolean active, String cropType, double germinationRate) {
        super(productId, productName, quantity, price, active);

        if (germinationRate < 0 || germinationRate > 100) {
            throw new IllegalArgumentException("Germination rate must be between 0 and 100");
        }

        this.cropType = cropType;
        this.germinationRate = germinationRate;
    }

    public String getCropType() {
        return cropType;
    }

    public double getGerminationRate() {
        return germinationRate;
    }

    @Override
    public String getProductType() {
        return "Seed";
    }

    @Override
    public int getReorderLevel() {
        return 20;
    }
}
