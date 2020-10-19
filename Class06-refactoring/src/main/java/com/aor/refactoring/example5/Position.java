package com.aor.refactoring.example5;

public class Position {
    private int x;
    private int y;
    private char direction;

    public Position(int x, int y, char direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public Position rotateLeft() {
        switch (direction) {
            case 'N':
                return new Position(x, y, 'W');
            case 'W':
                return new Position(x, y, 'S');
            case 'S':
                return new Position(x, y, 'E');
            case 'E':
                return new Position(x, y, 'N');
            default:
                return new Position(x, y, direction);
        }
    }

    public Position rotateRight() {
        switch (direction) {
            case 'N':
                return new Position(x, y, 'E');
            case 'W':
                return new Position(x, y, 'N');
            case 'S':
                return new Position(x, y, 'W');
            case 'E':
                return new Position(x, y, 'S');
            default:
                return new Position(x, y, direction);
        }
    }

    public Position moveForward() {
        switch (direction) {
            case 'N':
                return new Position(x, y - 1, direction);
            case 'W':
                return new Position(x - 1, y, 'S');
            case 'S':
                return new Position(x, y + 1, 'E');
            case 'E':
                return new Position(x + 1, y, 'N');
            default:
                return new Position(x, y, direction);
        }
    }
}
