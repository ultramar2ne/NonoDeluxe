package com.example.nonodeluxe.model;

public class ItemMenu {
    private int imageResource;
    private String text;

    public ItemMenu(int imageResource, String text){
        this.imageResource = imageResource;
        this.text = text;
    }

    public int getImageResource() {return imageResource;}

    public String getText() {return text;}
}
