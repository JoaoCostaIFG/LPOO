package com.aor.refactoring.example2;

public class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double x, double y, double width, double height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    public double getArea() {
        return width * height;
    }

    public double getPerimeter() {
        return 2 * (width + height);
    }

    public void draw() {
        System.out.println("Sou um retângulo desenhado" + x + y + width + height);
    }

    public void draw(GraphicFramework gf) {
        gf.drawLine(x, y, x + width, y);
        gf.drawLine(x + width, y, x + width, y + height);
        gf.drawLine(x + width, y + height, x, y + height);
        gf.drawLine(x, y + height, x, y);
    }
}
