package com.checkout.models;

import org.springframework.stereotype.Service;

@Service
public class ReceiptFactory {

    public Receipt createReceipt(int basketId) {
        return new Receipt(basketId);
    }
}
