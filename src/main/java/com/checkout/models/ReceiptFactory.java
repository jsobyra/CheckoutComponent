package com.checkout.models;

public class ReceiptFactory {

    public static Receipt createReceipt(int basketId) {
        return new Receipt(basketId);
    }
}
