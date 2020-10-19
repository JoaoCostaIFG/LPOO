package com.aor.refactoring.example5;

public class MoveForward implements Command {
    @Override
    public Position execute(Position pos) {
        return pos.moveForward();
    }
}
