package commands;

import arena.Arena;

public class MovRight extends MovCommand {
    public MovRight(Arena a) {
        super(a, 1);
    }

    public MovRight(Arena a, Integer s) {
        super(a, s);
    }

    @Override
    public void execute() {
        a.moveSkane(a.getSkane().moveRight(s));
    }
}
