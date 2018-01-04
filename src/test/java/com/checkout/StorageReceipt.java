package com.checkout;

import com.checkout.models.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by user on 04.01.18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StorageReceipt {
    @Autowired
    private StorageRepository storageRepository;

    @BeforeClass
    public static void setUpProperties() {
        Properties.createProperties("application");
    }

    @Test
    public void getExistingItem() throws Exception{
        String productName = "A";
        int price = 40;
        int unit = 3;
        int specialPrice = 70;

        Item item = storageRepository.getItem(productName);

        assertThat(item.getName()).isEqualTo(productName);
        assertThat(item.getPrice()).isEqualTo(price);
        assertThat(item.getUnit()).isEqualTo(unit);
        assertThat(item.getSpecialPrice()).isEqualTo(specialPrice);
    }

    @Test
    public void getBasketOnlyIfExists() throws Exception{
        String productName = "NotExist";

        Item item = storageRepository.getItem(productName);

        assertThat(item).isEqualTo(null);

    }

}
