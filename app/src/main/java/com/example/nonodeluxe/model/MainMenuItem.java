package com.example.nonodeluxe.model;

public class MainMenuItem {
    private int imageResource;
    private String text;

    public MainMenuItem(int imageResource, String text){
        this.imageResource = imageResource;
        this.text = text;
    }

    public int getImageResource() {return imageResource;}

    public String getText() {return text;}
}
