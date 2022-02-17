package com.techelevator;

public class Candy extends Item{
    public Candy(String slot, String name, int price) {
        super(slot, name, price);
    }
    @Override
    public String getSound(){
        return " Munch Munch, Yum!";
    }
}
