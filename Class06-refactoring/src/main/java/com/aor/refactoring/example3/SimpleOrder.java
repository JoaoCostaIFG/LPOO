package com.aor.refactoring.example3;

public class SimpleOrder {
    private DiscountType discount;
    private double price;

    public SimpleOrder(double price) {
        this.discount = new NoDiscount();
        this.price = price;
    }

    public void setDiscount(DiscountType discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return discount.applyDiscount(price);
    }
}
