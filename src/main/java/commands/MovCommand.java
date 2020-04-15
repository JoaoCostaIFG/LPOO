package commands;

import arena.Map;

public abstract class MovCommand extends Command {
    protected final Map a;
    protected final Integer s;

    public MovCommand(Map a, Integer s) {
        this.a = a;
        this.s = s;
    }
}
