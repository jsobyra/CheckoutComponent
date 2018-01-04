package com.checkout.models.discounts;

import com.checkout.models.Item;
import java.util.Map;

public class NYDiscount implements Discount {
    private final Map<Item, Integer> items;

    public NYDiscount(Map<Item, Integer> items) {
        this.items = items;
    }

    @Override
    public int count() {
        return items.entrySet().stream()
                .map((itemAmount) -> countDiscountPerItem(itemAmount.getKey(), itemAmount.getValue()))
                .reduce(0, Integer::sum);
    }

    private int countDiscountPerItem(Item item, int amount) {
        return amount/item.getUnit() * (item.getPrice() * item.getUnit() - item.getSpecialPrice());
    }
}
