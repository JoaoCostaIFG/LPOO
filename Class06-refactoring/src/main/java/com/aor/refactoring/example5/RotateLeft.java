package com.aor.refactoring.example5;

public class RotateLeft implements Command {
    @Override
    public Position execute(Position pos) {
        return pos.rotateLeft();
    }
}
