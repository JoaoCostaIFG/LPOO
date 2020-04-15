package commands;

import arena.Map;

public class MovLeft extends MovCommand {
    public MovLeft(Map a) {
        super(a, 1);
    }

    public MovLeft(Map a, Integer s) {
        super(a, s);
    }

    @Override
    public void execute() {
        a.moveSkane(a.getSkane().moveLeft(s));
    }
}
