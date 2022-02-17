package com.techelevator;

public class Drink extends Item{
    public Drink(String slot, String name, int price) {
        super(slot, name, price);
    }
    @Override
    public String getSound(){
        return " Glug Glug, Yum!";
    }
}
