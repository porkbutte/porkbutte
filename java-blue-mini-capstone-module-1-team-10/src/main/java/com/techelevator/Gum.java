package com.techelevator;

public class Gum extends Item{
    public Gum(String slot, String name, int price) {
        super(slot, name, price);
    }
    @Override
    public String getSound(){
        return " Chew Chew, Yum!";
    }
}
