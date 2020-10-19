package com.aor.refactoring.example3;

public class DiscountPercentage implements DiscountType {
    private double amount;

    public DiscountPercentage(double amount) {
        this.amount = amount;
    }

    public double applyDiscount(double price) {
        return price * (1 - amount);
    }
}
