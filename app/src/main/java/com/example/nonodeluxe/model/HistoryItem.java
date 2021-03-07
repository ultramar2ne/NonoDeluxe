package com.example.nonodeluxe.model;

public class HistoryItem {
    private int year;
    private int month;
    private int day;
//    private String dateTime;

    private int change;
    private int stock;
    private int type;   // input or output

    public HistoryItem(){

    }

    public HistoryItem(int year, int month, int day, int change, int stock, int type) {
        this.year = year;
        this.month = month;
        this.day = day;
//        this.dateTime = dateTime;
        this.change = change;
        this.stock = stock;
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

//    public String getDateTime() {
//        return dateTime;
//    }

    public int getChange() {
        return change;
    }

    public int getStock() {
        return stock;
    }

    public int getType() {
        return type;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

//    public void setDateTime(String dateTime) {
//        this.dateTime = dateTime;
//    }

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
