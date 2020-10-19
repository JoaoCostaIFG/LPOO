package com.aor.refactoring.example3;

public class DiscountFixed implements DiscountType {
    private int amount;

    public DiscountFixed(int amount) {
        this.amount = amount;
    }

    public double applyDiscount(double price) {
        return amount > price ? 0 : price - amount;
    }
}
