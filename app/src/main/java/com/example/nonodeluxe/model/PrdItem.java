package com.example.nonodeluxe.model;

public class PrdItem  extends MyItem{
    private String name;
    private String category;
    private int stock;
    private String standard;
    private String barcode;

    public PrdItem() {
    }

    public PrdItem(String name, String category, int stock, String standard, String barcode){
        this.name = name;
        this.category = category;
        this.stock = stock;
        this.standard = standard;
        this.barcode = barcode;
    }


    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public String getCategory() {
        return category;
    }

    public String getStandard() {
        return standard;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
