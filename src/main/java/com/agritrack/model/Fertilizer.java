package com.agritrack.model;

public class Fertilizer extends FarmProduct {

    private final String fertilizerType;
    private final String npkRatio;

    public Fertilizer(String productId, String productName, int quantity, double price,
                      boolean active, String fertilizerType, String npkRatio) {
        super(productId, productName, quantity, price, active);

        if (npkRatio == null || npkRatio.isBlank()) {
            throw new IllegalArgumentException("NPK ratio must not be null or blank");
        }

        this.fertilizerType = fertilizerType;
        this.npkRatio = npkRatio;
    }

    public String getFertilizerType() {
        return fertilizerType;
    }

    public String getNpkRatio() {
        return npkRatio;
    }

    @Override
    public String getProductType() {
        return "Fertilizer";
    }

    @Override
    public int getReorderLevel() {
        return 10;
    }
}
