package com.agritrack.model;

public abstract class FarmProduct {

    private final String productId;
    private String productName;
    private int quantity;
    private double price;
    private boolean active;

    protected FarmProduct(String productId, String productName, int quantity,
                          double price, boolean active) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.active = active;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract String getProductType();

    public abstract int getReorderLevel();

    public boolean isLowStock() {
        return quantity <= getReorderLevel();
    }

    public void addStock(int quantity) {
        validateStockQuantity(quantity);
        this.quantity += quantity;
    }

    public void removeStock(int quantity) {
        validateStockQuantity(quantity);
        if (quantity > this.quantity) {
            throw new IllegalArgumentException("Cannot remove more stock than available");
        }
        this.quantity -= quantity;
    }

    private void validateStockQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Stock quantity must be greater than zero");
        }
    }

    @Override
    public String toString() {
        return "FarmProduct{"
                + "productId='" + productId + '\''
                + ", productName='" + productName + '\''
                + ", quantity=" + quantity
                + ", price=" + price
                + ", active=" + active
                + ", productType='" + getProductType() + '\''
                + '}';
    }
}
