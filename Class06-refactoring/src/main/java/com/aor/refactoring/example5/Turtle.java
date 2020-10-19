package com.aor.refactoring.example5;

public class Turtle {
    private Position pos;

    public Turtle(Position pos) {
        this.pos = pos;
    }

    public int getRow() {
        return pos.getY();
    }

    public int getColumn() {
        return pos.getX();
    }

    public char getDirection() {
        return pos.getDirection();
    }

    public void execute(Command cmd) {
        Position new_pos = cmd.execute(pos);
        // collision checking, etc...
        this.pos = new_pos;
    }
}
