package com.example;

public class Product {
    private static int nextId = 1;
    private int id;
    private String name;
    private String category;
    private double price;
    private String code;
    private int stock;

    public Product(String name, String category, double price, String code, int stock) {
        this.id = nextId++;
        this.name = name;
        this.category = category;
        this.price = price;
        this.code = code;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public String getCode() { return code; }
    public int getStock() { return stock; }
    public void addStock(int qty) { this.stock += qty; }
    public void reduceStock(int qty) { this.stock -= qty; }
}