package com.aor.refactoring.example2;

public abstract class Shape implements Drawable, Is2D {
    protected double x;
    protected double y;

    public Shape(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public abstract void draw(GraphicFramework gf);
}
