package com.checkout.models;

import com.checkout.models.discounts.Discount;
import com.checkout.models.discounts.NYDiscount;
import java.util.Arrays;
import java.util.Map;

public class Receipt {
    private final int basketId;
    private final Map<Item, Integer> boughtItems;
    private final int sum;

    public Receipt(Basket basket) {
        this.basketId = basket.getId();
        this.boughtItems = basket.getItems();
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

    public Map<Item, Integer> getBoughtItems() {
        return boughtItems;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"basketId\":" + basketId);
        stringBuilder.append(",\"boughtItems\":{");
        boughtItems.entrySet().forEach((item) -> stringBuilder.append("\"" + item.getKey() + "\"" + ":" + item.getValue() + ","));
        if(stringBuilder.toString().endsWith(",")) stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.append("}");
        stringBuilder.append(",\"sum\":" + sum);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
