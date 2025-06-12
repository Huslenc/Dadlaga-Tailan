package com.example;

import org.junit.jupiter.api.*;
import java.util.*;

class ExtenTest {

    User user;

    @BeforeEach
    void setUp() {
        Exten.users.clear();
        Exten.products.clear();
        Exten.coupons.clear();
        Exten.signUp("testuser", "password", "user");
        user = Exten.users.get(0);
        Exten.addBalance(user, 100000);
    }

    @Test
    void testAddProducts() {
        Exten.addOrMergeProduct("Notebook", "Stationery", "NB001", 5000, 20);
        Exten.addOrMergeProduct("Pen", "Stationery", "PEN01", 1000, 50);
        Assertions.assertEquals(2, Exten.products.size());
    }

    @Test
    void testAddCoupon() {
        Exten.addCoupon("SALE10", 10);
        Assertions.assertEquals(1, Exten.coupons.size());
        Assertions.assertEquals("SALE10", Exten.coupons.get(0).getCode());
    }

    @Test
    void testMakeOrderSuccess() {
        Exten.addOrMergeProduct("Notebook", "Stationery", "NB001", 5000, 20);
        Exten.addOrMergeProduct("Pen", "Stationery", "PEN01", 1000, 50);
        Exten.addCoupon("SALE10", 10);
        List<Integer> productIds = Arrays.asList(Exten.products.get(0).getId(), Exten.products.get(1).getId());
        List<Integer> quantities = Arrays.asList(2, 5);
        Assertions.assertDoesNotThrow(() -> Exten.makeOrder(user, productIds, quantities, "SALE10"));
    }

    @Test
    void testDeleteProduct() {
        Exten.addOrMergeProduct("Notebook", "Stationery", "NB001", 5000, 20);
        Exten.addOrMergeProduct("Pen", "Stationery", "PEN01", 1000, 50);
        int penId = Exten.products.get(1).getId();
        Exten.deleteProduct(penId);
        Assertions.assertEquals(1, Exten.products.size());
    }

    @Test
    void testAddProductWithEmptyNameThrows() {
        Exception ex = Assertions.assertThrows(Exception.class, () -> {
            Exten.addOrMergeProduct("", "Stationery", "NB002", 4000, 10);
        });
        Assertions.assertNotNull(ex);
    }

    @Test
    void testOrderWithInsufficientStockThrows() {
        Exten.addOrMergeProduct("Notebook", "Stationery", "NB001", 5000, 20);
        Exception ex = Assertions.assertThrows(Exception.class, () -> {
            Exten.makeOrder(user, Arrays.asList(Exten.products.get(0).getId()), Arrays.asList(1000), null);
        });
        Assertions.assertNotNull(ex);
    }

    @Test
    void testOrderWithInvalidCouponThrows() {
        Exten.addOrMergeProduct("Notebook", "Stationery", "NB001", 5000, 20);
        Exception ex = Assertions.assertThrows(Exception.class, () -> {
            Exten.makeOrder(user, Arrays.asList(Exten.products.get(0).getId()), Arrays.asList(1), "BADCODE");
        });
        Assertions.assertNotNull(ex);
    }
}