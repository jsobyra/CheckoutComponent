package com.checkout.controllers;

import com.checkout.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasketController {
    private BasketFactory basketFactory;
    private ReceiptFactory receiptFactory;
    private StorageRepository storageRepository;

    @Autowired
    public BasketController(BasketFactory basketFactory, ReceiptFactory receiptFactory, StorageRepository storageRepository) {
        this.basketFactory = basketFactory;
        this.receiptFactory = receiptFactory;
        this.storageRepository = storageRepository;
    }

    @RequestMapping("/open")
    public Basket openBasket(@RequestParam(value="id") int basketId) {
        return basketFactory.createIfNotExistBasket(basketId);
    }

    @RequestMapping("/scan")
    public Basket scanItems(@RequestParam(value="id") int basketId,
                                      @RequestParam(value = "name") String productName,
                                      @RequestParam(value = "quantity", defaultValue = "0") int quantity) {
        basketFactory
                .getBasketIfExists(basketId)
                .ifPresent(basket -> basket.updateBasket(storageRepository.getItem(productName), quantity));
        return basketFactory.getBasketIfExists(basketId).orElse(null);

    }

    @RequestMapping("/close")
    public Receipt closeBasket(@RequestParam(value = "id") int basketId) {
        return receiptFactory.createReceipt(basketFactory.getBasketIfExists(basketId));
    }
}
