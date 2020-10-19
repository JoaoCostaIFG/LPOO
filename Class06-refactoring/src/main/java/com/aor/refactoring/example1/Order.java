package com.aor.refactoring.example1;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<OrderLine> lines;

    public Order() {
        lines = new ArrayList<>();
    }

    public Order(List<OrderLine> lines) {
        this.lines = lines;
    }

    public void add(OrderLine ol) {
        lines.add(ol);
    }

    public double getTotal() {
        double total = 0;
        for (OrderLine line : lines)
            total += line.getTotal();

        return total;
    }

    public boolean isElegibleForFreeDelivery() {
        return (getTotal() > 100);
    }

    public String printOrder() {
        StringBuilder printBuffer = new StringBuilder();

        for (OrderLine line : lines)
            printBuffer.append(line.toString() + "\n");

        printBuffer.append("Total: " + getTotal());
        return printBuffer.toString();
    }
}