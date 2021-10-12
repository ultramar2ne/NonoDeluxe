package com.example.nonodeluxe.model;

public class InventoryItemVerYang extends MyItem {
    private String barcode;
    private String empCode;
    private String name;
    private String date;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int change;
    private int stock;
    private int type;   // input or output

    public InventoryItemVerYang(){

    }

    public InventoryItemVerYang(String date, int change, int stock, int type) {
        this.change = change;
        this.stock = stock;
        this.type = type;
    }

    public int getChange() {
        return change;
    }

    public int getStock() {
        return stock;
    }

    public int getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setChange(int change) {
        this.change = change;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setType(int type) {
        this.type = type;
    }
}
