package com.checkout.models;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 30.12.17.
 */
public class Basket {
    private final int id;
    private final Map<Item, Integer> items;

    public Basket(int id) {
        this.id = id;
        this.items = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void updateBasket(Item item, int amount) {
        items.put(item, amount);
    }
}
