package com.agritrack.model;

public class CropProtectionProduct extends FarmProduct {

    private final String productCategory;
    private final int safetyIntervalDays;

    public CropProtectionProduct(String productId, String productName, int quantity, double price,
                                 boolean active, String productCategory, int safetyIntervalDays) {
        super(productId, productName, quantity, price, active);

        if (productCategory == null || productCategory.isBlank()) {
            throw new IllegalArgumentException("Product category must not be null or blank");
        }
        if (safetyIntervalDays < 0) {
            throw new IllegalArgumentException("Safety interval days cannot be negative");
        }

        this.productCategory = productCategory;
        this.safetyIntervalDays = safetyIntervalDays;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public int getSafetyIntervalDays() {
        return safetyIntervalDays;
    }

    @Override
    public String getProductType() {
        return "Crop Protection Product";
    }

    @Override
    public int getReorderLevel() {
        return 5;
    }
}
