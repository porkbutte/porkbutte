package com.techelevator;

public class Inventory {
    private String slot;
    private String name;
    private int price;
    private int stock;
    private int sales;

    public Inventory(String slot, String name, int price) {
        this.slot = slot;
        this.name = name;
        this.price = price;
        stock = 5;
        sales = 0;
    }
    public String getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void buy(){
        if (stock > 0) {
            stock--;
        }
    }
}
