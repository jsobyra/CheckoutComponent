package com.checkout;

import com.checkout.models.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by user on 04.01.18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BasketTest {
    @Autowired
    private BasketFactory basketFactory;

    @BeforeClass
    public static void setUpProperties() {
        Properties.createProperties("application");
    }

    @Test
    public void createEmptyBasket() throws Exception{
        int basketId = 200;
        Basket basket = basketFactory.createIfNotExistBasket(basketId);

        assertThat(basket.getId()).isEqualTo(basketId);
        assertThat(basket.getItems()).isEmpty();
    }

    @Test
    public void getBasketOnlyIfExists() throws Exception{
        int basketId1 = 201;
        int basketId2 = 202;

        Basket basket = basketFactory.createIfNotExistBasket(basketId1);

        assertThat(basket.getId()).isEqualTo(basketId1);
        assertThat(basket.getItems()).isEmpty();

        Optional<Basket> basket1 = basketFactory.getBasketIfExists(basketId1);
        basket1.ifPresent(b -> {
            assertThat(b.getId()).isEqualTo(basketId1);
            assertThat(b.getItems()).isEmpty();
        });

        Optional<Basket> basket2 = basketFactory.getBasketIfExists(basketId2);
        assertThat(basket2).isEqualTo(Optional.empty());
    }

}
