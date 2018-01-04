package com.checkout.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"id\":" + id);
        stringBuilder.append(",\"items\":{");
        items.entrySet().forEach((item) -> stringBuilder.append("\"" + item.getKey() + "\"" + ":" + item.getValue() + ","));
        if(stringBuilder.toString().endsWith(",")) stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }
}
