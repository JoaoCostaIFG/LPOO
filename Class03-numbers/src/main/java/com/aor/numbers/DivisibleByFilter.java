package com.aor.numbers;

public class DivisibleByFilter implements IListFilter {
    private Integer n;
    public DivisibleByFilter(Integer number) {
        this.n = number;
    }

    public boolean accept(Integer number) {
        return number % n == 0;
    }
}
