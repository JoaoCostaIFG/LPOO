package com.aor.refactoring.example2;

public class Circle extends Shape {
    private double radius;

    public Circle(double x, double y, double radius) {
        super(x, y);
        this.radius = radius;
    }

    public double getArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    public void draw() {
        System.out.println("Sou um círculo desenhado" + x + y + radius);
    }

    public void draw(GraphicFramework gf) {
        gf.drawCircle(x, y, radius);
    }
}
