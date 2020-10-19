package com.aor.refactoring.example3;

public class NoDiscount implements DiscountType {
    public NoDiscount() {
    }

    public double applyDiscount(double price) {
        return price;
    }
}
