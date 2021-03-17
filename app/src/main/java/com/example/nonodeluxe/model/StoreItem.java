package com.example.nonodeluxe.model;

public class StoreItem extends MyItem{
    private String store_name;
    private String store_addr;
    private int store_code;
    private int unit_code;

    public StoreItem(){

    }

    public StoreItem(String store_name, String store_addr, int store_code, int unit_code){
        this.store_name = store_name;
        this.store_addr = store_addr;
        this.store_code = store_code;
        this.unit_code = unit_code;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_addr() {
        return store_addr;
    }

    public void setStore_addr(String store_addr) {
        this.store_addr = store_addr;
    }

    public int getStore_code() {
        return store_code;
    }

    public void setStore_code(int store_code) {
        this.store_code = store_code;
    }

    public int getUnit_code() {
        return unit_code;
    }

    public void setUnit_code(int unit_code) {
        this.unit_code = unit_code;
    }
}
