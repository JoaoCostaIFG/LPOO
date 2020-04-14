package commands;

import arena.Arena;

public class MovDown extends MovCommand {
    public MovDown(Arena a) {
        super(a, 1);
    }

    public MovDown(Arena a, Integer s) {
        super(a, s);
    }

    @Override
    public void execute() {
        a.moveSkane(a.getSkane().moveDown(s));
    }
}
