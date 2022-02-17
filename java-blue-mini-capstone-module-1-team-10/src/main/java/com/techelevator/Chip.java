package com.techelevator;

public class Chip extends Item{
    public Chip(String slot, String name, int price) {
        super(slot, name, price);
    }
    @Override
    public String getSound(){
        return " Crunch Crunch, Yum!";
    }
}
