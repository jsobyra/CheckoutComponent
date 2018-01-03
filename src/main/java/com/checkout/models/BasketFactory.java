package com.checkout.models;

import java.util.HashMap;
import java.util.Optional;

/**
 * Created by user on 30.12.17.
 */
public class BasketFactory {
    private static final HashMap<Integer, Basket> basketMap = new HashMap<>();

    public static Basket createIfNotExistBasket(int basketId) {
        if(basketMap.containsKey(basketId))
            return basketMap.get(basketId);
        else {
            Basket basket = new Basket(basketId);
            basketMap.put(basketId, basket);
            return basket;
        }
    }

    public static Optional<Basket> getBasketIfExists(int basketId) {
        if(basketMap.containsKey(basketId)) return Optional.of(basketMap.get(basketId));
        else return Optional.empty();
    }
}
