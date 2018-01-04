package com.checkout.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class ReceiptFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptFactory.class);

    public Receipt createReceipt(Optional<Basket> basket) {
        try {
            return new Receipt(basket.get());
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }
}
