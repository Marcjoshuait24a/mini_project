package model;

public class EquipmentProduct extends Product {
    private String equipmentType;
    private int warrantyMonths;

    public EquipmentProduct(String productId, String name, double price, int stockQuantity, String equipmentType, int warrantyMonths) {
        super(productId, name, price, stockQuantity);
        this.equipmentType = equipmentType;
        this.warrantyMonths = warrantyMonths;
    }

    public String getEquipmentType() { return equipmentType; }
    public void setEquipmentType(String equipmentType) { this.equipmentType = equipmentType; }

    public int getWarrantyMonths() { return warrantyMonths; }
    public void setWarrantyMonths(int warrantyMonths) { this.warrantyMonths = warrantyMonths; }

    @Override
    public String getCategoryDetails() {
        return "Equipment [Type: " + equipmentType + ", Warranty: " + warrantyMonths + " mos]";
    }
}
