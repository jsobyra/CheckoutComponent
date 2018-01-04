package com.checkout.controllers;

import com.checkout.models.*;
import com.checkout.models.exceptions.NoSuchBasketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class BasketController {
    @Autowired
    private BasketFactory basketFactory;
    @Autowired
    private ReceiptFactory receiptFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(BasketController.class);

    @RequestMapping("/open")
    public Basket openBasket(@RequestParam(value="id") int basketId) {
        return basketFactory.createIfNotExistBasket(basketId);
    }

    @RequestMapping("/scan")
    public Basket scanItems(@RequestParam(value="id") int basketId,
                          @RequestParam(value = "name") String productName,
                          @RequestParam(value = "quantity", defaultValue = "0") int quantity) {
        try {
            if(basketFactory.getBasketIfExists(basketId).isPresent()) {
                basketFactory
                        .getBasketIfExists(basketId).get()
                        .updateBasket(StorageRepository.getStorage().getItem(productName), quantity);

                LOGGER.info("Scan of items completed succesfully for basket with id: " + basketId);
                return basketFactory.getBasketIfExists(basketId).get();
            }
            else throw new NoSuchBasketException("There is no such basket with id: " + basketId);
        } catch (NoSuchBasketException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @RequestMapping("/close")
    public Receipt closeBasket(@RequestParam(value = "id") int basketId) {
        return receiptFactory.createReceipt(basketId);
    }
}
