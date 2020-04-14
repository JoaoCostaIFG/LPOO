package commands;

import arena.Arena;

public class MovLeft extends MovCommand {
    public MovLeft(Arena a) {
        super(a, 1);
    }

    public MovLeft(Arena a, Integer s) {
        super(a, s);
    }

    @Override
    public void execute() {
        a.moveSkane(a.getSkane().moveLeft(s));
    }
}
