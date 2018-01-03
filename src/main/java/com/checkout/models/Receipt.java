package com.checkout.models;

import com.checkout.models.discounts.Discount;
import com.checkout.models.discounts.NYDiscount;

import java.util.Arrays;
import java.util.HashMap;

public class Receipt {
    private final int basketId;
    private final HashMap<Item, Integer> boughtItems;
    private int sum;

    public Receipt(int basketId) {
        this.basketId = basketId;
        this.boughtItems = BasketFactory.getBasketIfExists(basketId).get().getItems();
        this.sum = count();
    }

    private int count() {
        return countSum() - countDiscounts();

    }

    private int countSum() {
        return boughtItems.entrySet().stream()
                .map(boughtItem -> boughtItem.getKey().getPrice() * boughtItem.getValue())
                .reduce(0, Integer::sum);
    }

    private int countDiscounts() {
        Discount[] discounts = {new NYDiscount(this.boughtItems)};
        return Arrays.stream(discounts)
                .map(Discount::count)
                .reduce(0, Integer::sum);
    }

    public int getBasketId() {
        return basketId;
    }

    public int getSum() {
        return sum;
    }

    public HashMap<Item, Integer> getBoughtItems() {
        return boughtItems;
    }
}
