package com.checkout.models;

public class Item {
    private final String name;
    private final int price;
    private final int unit;
    private final int specialPrice;

    public Item(String name, int price, int unit, int specialPrice) {
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.specialPrice = specialPrice;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getUnit() {
        return unit;
    }

    public int getSpecialPrice() {
        return specialPrice;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Price: " + price + ", Unit: " + unit + ", Special Price: " + specialPrice;
    }
}
