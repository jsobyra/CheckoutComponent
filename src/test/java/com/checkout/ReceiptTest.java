package com.checkout;

import com.checkout.models.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by user on 04.01.18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ReceiptTest {
    @Autowired
    private BasketFactory basketFactory;
    @Autowired
    private ReceiptFactory receiptFactory;
    @Autowired
    private StorageRepository storageRepository;

    @BeforeClass
    public static void setUpProperties() {
        Properties.createProperties("application");
    }

    @Test
    public void createReceiptForEmptyBasket() throws Exception{
        int basketId = 300;
        Basket basket = basketFactory.createIfNotExistBasket(basketId);
        Receipt receipt = receiptFactory.createReceipt(Optional.of(basket));

        assertThat(receipt.getBasketId()).isEqualTo(basketId);
        assertThat(receipt.getSum()).isEqualTo(0);
        assertThat(receipt.getBoughtItems()).isEmpty();
    }

    @Test
    public void createReceiptWithoutDiscountForNotEmptyBasket() throws Exception{
        int basketId = 301;
        String productName = "A";
        int quantity = 1;
        Item item = storageRepository.getItem(productName);

        Basket basket = basketFactory.createIfNotExistBasket(basketId);
        basket.updateBasket(item, quantity);
        Receipt receipt = receiptFactory.createReceipt(Optional.of(basket));

        assertThat(receipt.getBasketId()).isEqualTo(basketId);
        assertThat(receipt.getSum()).isEqualTo(quantity * item.getPrice());
        assertThat(receipt.getBoughtItems().size()).isEqualTo(1);
    }

    @Test
    public void createReceiptWithDiscountForNotEmptyBasket() throws Exception{
        int basketId = 302;
        String productName = "A";
        int quantity = 3;
        int discountedPrice = 70;

        Item item = storageRepository.getItem(productName);

        Basket basket = basketFactory.createIfNotExistBasket(basketId);
        basket.updateBasket(item, quantity);
        Receipt receipt = receiptFactory.createReceipt(Optional.of(basket));

        assertThat(receipt.getBasketId()).isEqualTo(basketId);
        assertThat(receipt.getSum()).isEqualTo(discountedPrice);
        assertThat(receipt.getBoughtItems().size()).isEqualTo(1);
    }

    @Test
    public void notCreateReceiptForNotExistingBasket() throws Exception{
        int basketId = 303;
        Optional<Basket> basket = basketFactory.getBasketIfExists(basketId);
        Receipt receipt = receiptFactory.createReceipt(basket);

        assertThat(receipt).isEqualTo(null);
    }
}
