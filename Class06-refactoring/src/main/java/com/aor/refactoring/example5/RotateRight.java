package com.aor.refactoring.example5;

public class RotateRight implements Command {
    @Override
    public Position execute(Position pos) {
        return pos.rotateRight();
    }
}
