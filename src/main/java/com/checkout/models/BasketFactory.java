package com.checkout.models;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by user on 30.12.17.
 */

@Component
public class BasketFactory {
    private static final HashMap<Integer, Basket> basketMap = new HashMap<>();

    public Basket createIfNotExistBasket(int basketId) {
        if(basketMap.containsKey(basketId))
            return basketMap.get(basketId);
        else {
            Basket basket = new Basket(basketId);
            basketMap.put(basketId, basket);
            return basket;
        }
    }

    public Optional<Basket> getBasketIfExists(int basketId) {
        return Optional.ofNullable(basketMap.get(basketId));
    }
}
