package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;

public class Exten {
    public static final Logger logger = LogManager.getLogger(Exten.class);
    public static List<User> users = new ArrayList<>();
    public static List<Product> products = new ArrayList<>();
    public static Map<String, Double> coupons = new HashMap<>();

    /**
     * Add or merge a product into the product list.
     */
    public static void addOrMergeProduct(String name, String category, String code, double price, int stock) {
        try {
            if (name == null || name.trim().isEmpty())
                throw new IllegalArgumentException("Product name cannot be empty.");
            if (category == null || category.trim().isEmpty())
                throw new IllegalArgumentException("Category cannot be empty.");
            if (code == null || code.trim().isEmpty())
                throw new IllegalArgumentException("Code cannot be empty.");
            if (price < 0)
                throw new IllegalArgumentException("Price cannot be negative.");
            if (stock < 0)
                throw new IllegalArgumentException("Stock cannot be negative.");
            for (Product p : products)
                if (p.getCode().equalsIgnoreCase(code))
                    throw new IllegalArgumentException("A product with this code already exists!");
            products.add(new Product(name, category, price, code, stock));
            logger.info("Product added: {}", name);
        } catch (IllegalArgumentException e) {
            logger.error("Error adding product: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Delete a product by its ID.
     */
    public static void deleteProduct(int id) {
        try {
            Product toRemove = null;
            for (Product p : products) if (p.getId() == id) toRemove = p;
            if (toRemove == null) throw new IllegalArgumentException("Product not found.");
            products.remove(toRemove);
            logger.info("Product deleted: id={}", id);
        } catch (IllegalArgumentException e) {
            logger.error("Error deleting product: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Register a new user.
     */
    public static void signUp(String username, String password, String role) {
        try {
            for (User u : users)
                if (u.getUsername().equals(username))
                    throw new IllegalArgumentException("A user with this username already exists!");
            users.add(new User(username, password, "admin".equals(role) ? Role.ADMIN : Role.USER));
            logger.info("New user registered: {}", username);
        } catch (IllegalArgumentException e) {
            logger.error("Error registering user: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Add balance to a user's account.
     */
    public static void addBalance(User user, double amt) {
        try {
            if (amt < 0) throw new IllegalArgumentException("Cannot add negative balance!");
            user.addBalance(amt);
            logger.info("User {} added {} to their account.", user.getUsername(), amt);
        } catch (IllegalArgumentException e) {
            logger.error("Error adding balance: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Add a coupon to the system.
     */
    public static void addCoupon(String code, double percent) {
        try {
            if (code == null || code.trim().isEmpty())
                throw new IllegalArgumentException("Coupon code cannot be empty.");
            if (coupons.containsKey(code))
                throw new IllegalArgumentException("This coupon code already exists!");
            if (percent <= 0 || percent > 100)
                throw new IllegalArgumentException("Discount percentage must be between 0 and 100.");
            coupons.put(code, percent);
            logger.info("Coupon added: {}, discount: {}", code, percent);
        } catch (IllegalArgumentException e) {
            logger.error("Error adding coupon: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Make an order for a user.
     */
    
}