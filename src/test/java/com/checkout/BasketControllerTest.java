package com.checkout;

import com.checkout.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith (SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasketControllerTest {
   	@Autowired
    private TestRestTemplate restTemplate;
   	@Autowired
    private BasketFactory basketFactory;
    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private ReceiptFactory receiptFactory;

    @BeforeClass
    public static void setUpProperties() {
        Properties.createProperties("application");
    }

    @Test
    public void openBasketWithCorrectParams() throws Exception{
        ResponseEntity<String> response = this.restTemplate.getForEntity("/open?id=100", String.class);

    	assertThat(response.getBody()).isEqualTo(basketFactory.createIfNotExistBasket(100).toString());
    	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void openBasketWithIncorrectParams() {
        ResponseEntity<String> response = this.restTemplate.getForEntity("/open", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void scanOneProductWithCorrectParams() {
        ResponseEntity<String> openResponse = this.restTemplate.getForEntity("/open?id=101", String.class);

        assertThat(openResponse.getBody()).isEqualTo(basketFactory.createIfNotExistBasket(101).toString());
        assertThat(openResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> scanResponse = this.restTemplate.getForEntity("/scan?id=101&name=B&quantity=4", String.class);

        basketFactory
                .getBasketIfExists(101)
                .ifPresent(basket -> basket.updateBasket(storageRepository.getItem("B"), 4));

        assertThat(scanResponse.getBody()).isEqualTo(basketFactory.getBasketIfExists(101).orElse(null).toString());
        assertThat(scanResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void scanTwoProductWithCorrectParams() {
        ResponseEntity<String> openResponse = this.restTemplate.getForEntity("/open?id=102", String.class);

        assertThat(openResponse.getBody()).isEqualTo(basketFactory.createIfNotExistBasket(102).toString());
        assertThat(openResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> scanResponse1 = this.restTemplate.getForEntity("/scan?id=102&name=B&quantity=4", String.class);

        basketFactory
                .getBasketIfExists(102)
                .ifPresent(basket -> basket.updateBasket(storageRepository.getItem("B"), 4));

        assertThat(scanResponse1.getBody()).isEqualTo(basketFactory.getBasketIfExists(102).orElse(null).toString());
        assertThat(scanResponse1.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> scanResponse2 = this.restTemplate.getForEntity("/scan?id=102&name=A&quantity=3", String.class);

        basketFactory
                .getBasketIfExists(102)
                .ifPresent(basket -> basket.updateBasket(storageRepository.getItem("A"), 3));

        assertThat(scanResponse2.getBody()).isEqualTo(basketFactory.getBasketIfExists(102).orElse(null).toString());
        assertThat(scanResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void scanProductAndUpdateQuantityWithCorrectParams() {
        ResponseEntity<String> openResponse = this.restTemplate.getForEntity("/open?id=103", String.class);

        assertThat(openResponse.getBody()).isEqualTo(basketFactory.createIfNotExistBasket(103).toString());
        assertThat(openResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> scanResponse1 = this.restTemplate.getForEntity("/scan?id=103&name=B&quantity=4", String.class);

        basketFactory
                .getBasketIfExists(103)
                .ifPresent(basket -> basket.updateBasket(storageRepository.getItem("B"), 4));

        assertThat(scanResponse1.getBody()).isEqualTo(basketFactory.getBasketIfExists(103).orElse(null).toString());
        assertThat(scanResponse1.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> scanResponse2 = this.restTemplate.getForEntity("/scan?id=103&name=B&quantity=4", String.class);

        basketFactory
                .getBasketIfExists(103)
                .ifPresent(basket -> basket.updateBasket(storageRepository.getItem("B"), 4));

        assertThat(scanResponse2.getBody()).isEqualTo(basketFactory.getBasketIfExists(103).orElse(null).toString());
        assertThat(scanResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void scanOneProductWithIncorrectParams() {
        ResponseEntity<String> openResponse = this.restTemplate.getForEntity("/open?id=104", String.class);

        assertThat(openResponse.getBody()).isEqualTo(basketFactory.createIfNotExistBasket(104).toString());
        assertThat(openResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> scanResponse = this.restTemplate.getForEntity("/scan?", String.class);

        assertThat(scanResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void closeEmptyBasketWithCorrectParams() throws Exception{
        ResponseEntity<String> openResponse = this.restTemplate.getForEntity("/open?id=105", String.class);

        assertThat(openResponse.getBody()).isEqualTo(basketFactory.createIfNotExistBasket(105).toString());
        assertThat(openResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> closeResponse = this.restTemplate.getForEntity("/close?id=105", String.class);

        assertThat(closeResponse.getBody()).isEqualTo(receiptFactory.createReceipt(basketFactory.getBasketIfExists(105)).toString());
        assertThat(closeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void closeWithOneProductWithCorrectParams() {
        ResponseEntity<String> openResponse = this.restTemplate.getForEntity("/open?id=101", String.class);

        assertThat(openResponse.getBody()).isEqualTo(basketFactory.createIfNotExistBasket(101).toString());
        assertThat(openResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> scanResponse = this.restTemplate.getForEntity("/scan?id=101&name=B&quantity=4", String.class);

        basketFactory
                .getBasketIfExists(101)
                .ifPresent(basket -> basket.updateBasket(storageRepository.getItem("B"), 4));

        assertThat(scanResponse.getBody()).isEqualTo(basketFactory.getBasketIfExists(101).orElse(null).toString());
        assertThat(scanResponse.getStatusCode()).isEqualTo(HttpStatus.OK);


    }

    @Test
    public void closeWithTwoProductWithCorrectParams() {
        ResponseEntity<String> openResponse = this.restTemplate.getForEntity("/open?id=102", String.class);

        assertThat(openResponse.getBody()).isEqualTo(basketFactory.createIfNotExistBasket(102).toString());
        assertThat(openResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> scanResponse1 = this.restTemplate.getForEntity("/scan?id=102&name=B&quantity=4", String.class);

        basketFactory
                .getBasketIfExists(102)
                .ifPresent(basket -> basket.updateBasket(storageRepository.getItem("B"), 4));

        assertThat(scanResponse1.getBody()).isEqualTo(basketFactory.getBasketIfExists(102).orElse(null).toString());
        assertThat(scanResponse1.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> scanResponse2 = this.restTemplate.getForEntity("/scan?id=102&name=A&quantity=3", String.class);

        basketFactory
                .getBasketIfExists(102)
                .ifPresent(basket -> basket.updateBasket(storageRepository.getItem("A"), 3));

        assertThat(scanResponse2.getBody()).isEqualTo(basketFactory.getBasketIfExists(102).orElse(null).toString());
        assertThat(scanResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


}
