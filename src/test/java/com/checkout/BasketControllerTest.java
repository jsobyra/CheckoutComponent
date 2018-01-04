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
    public void openBasket() throws Exception{
        int basketId = 100;
        ResponseEntity<String> openResponse = openBasketAPI(basketId);
        Basket openedBasket = openBasket(basketId);

    	assertThat(openResponse.getBody()).isEqualTo(openedBasket.toString());
    	assertThat(openResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void scanOneProduct() {
        int basketId = 101;
        String productName = "B";
        int quantity = 4;

        ResponseEntity<String> openResponse = openBasketAPI(basketId);
        Basket openedBasket = openBasket(basketId);

        assertThat(openResponse.getBody()).isEqualTo(openedBasket.toString());
        assertThat(openResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> scanResponse = scanBasketAPI(basketId, productName, quantity);
        Basket scannedBasket = scanBasket(basketId, productName, quantity);

        assertThat(scanResponse.getBody()).isEqualTo(scannedBasket.toString());
        assertThat(scanResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void scanTwoProducts() {
        int basketId = 102;
        String productName1 = "B";
        int quantity1 = 4;
        String productName2 = "A";
        int quantity2 = 2;

        ResponseEntity<String> openResponse = openBasketAPI(basketId);
        Basket openedBasket = openBasket(basketId);

        assertThat(openResponse.getBody()).isEqualTo(openedBasket.toString());
        assertThat(openResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> scanResponse1 = scanBasketAPI(basketId, productName1, quantity1);
        Basket scannedBasket1 = scanBasket(basketId, productName1, quantity1);

        assertThat(scanResponse1.getBody()).isEqualTo(scannedBasket1.toString());
        assertThat(scanResponse1.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> scanResponse2 = scanBasketAPI(basketId, productName2, quantity2);
        Basket scannedBasket2 = scanBasket(basketId, productName2, quantity2);

        assertThat(scanResponse2.getBody()).isEqualTo(scannedBasket2.toString());
        assertThat(scanResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void scanProductAndUpdateQuantity() {
        int basketId = 103;
        String productName = "B";
        int quantity1 = 4;
        int quantity2 = 2;

        ResponseEntity<String> openResponse = openBasketAPI(basketId);
        Basket openedBasket = openBasket(basketId);

        assertThat(openResponse.getBody()).isEqualTo(openedBasket.toString());
        assertThat(openResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> scanResponse1 = scanBasketAPI(basketId, productName, quantity1);
        Basket scannedBasket1 = scanBasket(basketId, productName, quantity1);

        assertThat(scanResponse1.getBody()).isEqualTo(scannedBasket1.toString());
        assertThat(scanResponse1.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> scanResponse2 = scanBasketAPI(basketId, productName, quantity2);
        Basket scannedBasket2 = scanBasket(basketId, productName, quantity2);

        assertThat(scanResponse2.getBody()).isEqualTo(scannedBasket2.toString());
        assertThat(scanResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void closeEmptyBasket() throws Exception{
        int basketId = 104;
        ResponseEntity<String> openResponse = openBasketAPI(basketId);
        Basket openedBasket = openBasket(basketId);

        assertThat(openResponse.getBody()).isEqualTo(openedBasket.toString());
        assertThat(openResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> closeResponse = closeBasketAPI(basketId);
        Receipt closedBasket = closeBasket(basketId);

        assertThat(closeResponse.getBody()).isEqualTo(closedBasket.toString());
        assertThat(closeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void closeBasketWithOneProduct() {
        int basketId = 105;
        String productName = "B";
        int quantity = 4;

        ResponseEntity<String> openResponse = openBasketAPI(basketId);
        Basket openedBasket = openBasket(basketId);

        assertThat(openResponse.getBody()).isEqualTo(openedBasket.toString());
        assertThat(openResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> scanResponse = scanBasketAPI(basketId, productName, quantity);
        Basket scannedBasket = scanBasket(basketId, productName, quantity);

        assertThat(scanResponse.getBody()).isEqualTo(scannedBasket.toString());
        assertThat(scanResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> closeResponse = closeBasketAPI(basketId);
        Receipt closedBasket = closeBasket(basketId);

        assertThat(closeResponse.getBody()).isEqualTo(closedBasket.toString());
        assertThat(closeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void closeWithTwoProduct() {
        int basketId = 106;
        String productName1 = "B";
        int quantity1 = 4;
        String productName2 = "A";
        int quantity2 = 2;

        ResponseEntity<String> openResponse = openBasketAPI(basketId);
        Basket openedBasket = openBasket(basketId);

        assertThat(openResponse.getBody()).isEqualTo(openedBasket.toString());
        assertThat(openResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> scanResponse1 = scanBasketAPI(basketId, productName1, quantity1);
        Basket scannedBasket1 = scanBasket(basketId, productName1, quantity1);

        assertThat(scanResponse1.getBody()).isEqualTo(scannedBasket1.toString());
        assertThat(scanResponse1.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> scanResponse2 = scanBasketAPI(basketId, productName2, quantity2);
        Basket scannedBasket2 = scanBasket(basketId, productName2, quantity2);

        assertThat(scanResponse2.getBody()).isEqualTo(scannedBasket2.toString());
        assertThat(scanResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> closeResponse = closeBasketAPI(basketId);
        Receipt closedBasket = closeBasket(basketId);

        assertThat(closeResponse.getBody()).isEqualTo(closedBasket.toString());
        assertThat(closeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private ResponseEntity<String> openBasketAPI(int basketId) {
        return this.restTemplate.getForEntity("/open?id=" + basketId, String.class);
    }

    private ResponseEntity<String> scanBasketAPI(int basketId, String productName, int quantity) {
        return this.restTemplate.postForEntity("/scan?id=" + basketId + "&name=" + productName + "&quantity=" +quantity, null, String.class);
    }

    private ResponseEntity<String> closeBasketAPI(int basketId) {
        return this.restTemplate.getForEntity("/close?id=" + basketId, String.class);
    }

    private Basket openBasket(int basketId) {
        return basketFactory.createIfNotExistBasket(basketId);
    }

    private Basket scanBasket(int basketId, String productName, int quantity) {
        basketFactory
                .getBasketIfExists(basketId)
                .ifPresent(basket -> basket.updateBasket(storageRepository.getItem(productName), quantity));
        return basketFactory.getBasketIfExists(basketId).orElse(null);
    }

    private Receipt closeBasket(int basketId) {
        return receiptFactory.createReceipt(basketFactory.getBasketIfExists(basketId));
    }
}
