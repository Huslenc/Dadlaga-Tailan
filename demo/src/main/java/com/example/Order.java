package com.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int nextOrderId = 1;
    private int orderId;
    private List<Product> products = new ArrayList<>();
    private List<Integer> quantities = new ArrayList<>();
    private double total;
    private OrderStatus status;
    private String paymentId;
    private LocalDateTime date;

    public Order() {
        this.orderId = nextOrderId++;
        this.status = OrderStatus.PENDING;
        this.date = LocalDateTime.now();
    }

    public void addProduct(Product product, int quantity) {
        products.add(product);
        quantities.add(quantity);
        total += product.getPrice() * quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getTotal() {
        return total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }
}