package com.example.nonodeluxe.model;

public class HistoryItem {
    private String date;
    private int change;
    private int stock;
    private int type;   // input or output

    public HistoryItem(){

    }

    public HistoryItem(String date, int change, int stock, int type){
        this.date = date;
        this.change = change;
        this.stock = stock;
        this.type = type;
    }

    public String getDate() {
        return date;
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
