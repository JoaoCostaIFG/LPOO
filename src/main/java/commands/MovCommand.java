package commands;

import arena.Arena;

public abstract class MovCommand extends Command {
    protected final Arena a;
    protected final Integer s;

    public MovCommand(Arena a, Integer s) {
        this.a = a;
        this.s = s;
    }
}
