package com.example.nonodeluxe.model;

public class HistoryItem {
    private int date;
    private int change;
    private int stock;
    private boolean type;   // input or output

    public HistoryItem(){

    }

    public HistoryItem(int date, int change, int stock, boolean type){
        this.date = date;
        this.change = change;
        this.stock = stock;
        this.type = type;
    }

    public int getDate() {
        return date;
    }

    public int getChange() {
        return change;
    }

    public int getStock() {
        return stock;
    }

    public boolean isType() {
        return type;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setChange(int change) {
        this.change = change;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
