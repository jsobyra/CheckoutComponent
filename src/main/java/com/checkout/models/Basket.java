package com.checkout.models;

import java.util.HashMap;

/**
 * Created by user on 30.12.17.
 */
public class Basket {
    private final int id;
    private final HashMap<Item, Integer> items;

    public Basket(int id) {
        this.id = id;
        this.items = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public HashMap<Item, Integer> getItems() {
        return items;
    }

    public void updateBasket(Item item, int amount) {
        items.put(item, amount);
    }
}
