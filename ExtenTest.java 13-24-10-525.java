package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class ExtenTest {

    @BeforeEach
    void setUp() {
        Exten.users.clear();
        Exten.products.clear();
        Exten.coupons.clear();
        Exten.signUp("testuser", "password", "user");
        Exten.addBalance(Exten.users.get(0), 100000);
    }

    @Test
    void testAddOrMergeProduct() {
        Exten.addOrMergeProduct("Notebook", "Stationery", "NB001", 5000, 20);
        Exten.addOrMergeProduct("Pen", "Stationery", "PEN001", 1000, 50);
        assertEquals(2, Exten.products.size());
        assertEquals("Notebook", Exten.products.get(0).getName());
    }

    @Test
    void testDeleteProduct() {
        Exten.addOrMergeProduct("Notebook", "Stationery", "NB001", 5000, 20);
        int productId = Exten.products.get(0).getId();
        Exten.deleteProduct(productId);
        assertEquals(0, Exten.products.size());
    }

    @Test
    void testSignUpAndAddBalance() {
        Exten.signUp("admin", "adminpass", "admin");
        assertEquals(2, Exten.users.size());
        User admin = Exten.users.get(1);
        assertEquals("admin", admin.getUsername());
        assertEquals(Role.ADMIN, admin.getRole());
        Exten.addBalance(admin, 5000);
        assertEquals(5000, admin.getBalance());
    }

    @Test
    void testAddCoupon() {
        Exten.addCoupon("SALE10", 10);
        assertEquals(1, Exten.coupons.size());
        assertTrue(Exten.coupons.containsKey("SALE10"));
    }

    @Test
    void testMakeOrderSuccess() {
        User user = Exten.users.get(0);
        Exten.addOrMergeProduct("Notebook", "Stationery", "NB001", 5000, 20);
        Exten.addOrMergeProduct("Pen", "Stationery", "PEN001", 1000, 50);
        Exten.addCoupon("SALE10", 10);

        List<Integer> productIds = Arrays.asList(
            Exten.products.get(0).getId(),
            Exten.products.get(1).getId()
        );
        List<Integer> quantities = Arrays.asList(2, 5);

        assertDoesNotThrow(() -> Exten.makeOrder(user, productIds, quantities, "SALE10"));
        assertEquals(1, user.getOrders().size());
        assertEquals(OrderStatus.PAID, user.getOrders().get(0).getStatus());
    }

    @Test
void testMakeOrderInvalidCoupon() {
    User user = Exten.users.get(0);
    Exten.addOrMergeProduct("Notebook", "Stationery", "NB001", 5000, 20);

    List<Integer> productIds = Collections.singletonList(Exten.products.get(0).getId());
    List<Integer> quantities = Collections.singletonList(1);

    Exception ex = assertThrows(IllegalArgumentException.class, () ->
        Exten.makeOrder(user, productIds, quantities, "INVALIDCODE")
    );
    assertTrue(ex.getMessage().contains("Invalid coupon code"));
}

@Test
void testMakeOrderInsufficientStock() {
    User user = Exten.users.get(0);
    Exten.addOrMergeProduct("Notebook", "Stationery", "NB001", 5000, 2);

    List<Integer> productIds = Collections.singletonList(Exten.products.get(0).getId());
    List<Integer> quantities = Collections.singletonList(10);

    Exception ex = assertThrows(IllegalArgumentException.class, () ->
        Exten.makeOrder(user, productIds, quantities, null)
    );
    assertTrue(ex.getMessage().contains("Insufficient stock"));
}
}