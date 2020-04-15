package commands;

import arena.Map;

public abstract class MovCommand extends Command {
    protected final Map m;
    protected final Integer s;

    public MovCommand(Map m, Integer s) {
        this.m = m;
        this.s = s;
    }
}
