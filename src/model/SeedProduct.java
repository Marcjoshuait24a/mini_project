package model;

public class SeedProduct extends Product {
    private String cropType;
    private double germinationRate; // e.g., 95.5%

    public SeedProduct(String productId, String name, double price, int stockQuantity, String cropType, double germinationRate) {
        super(productId, name, price, stockQuantity);
        this.cropType = cropType;
        this.germinationRate = germinationRate;
    }

    public String getCropType() { return cropType; }
    public void setCropType(String cropType) { this.cropType = cropType; }

    public double getGerminationRate() { return germinationRate; }
    public void setGerminationRate(double germinationRate) { this.germinationRate = germinationRate; }

    @Override
    public String getCategoryDetails() {
        return "Seed [Crop: " + cropType + ", Germination: " + germinationRate + "%]";
    }
}
