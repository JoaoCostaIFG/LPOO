package commands;

import arena.Map;

public class MovRight extends MovCommand {
    public MovRight(Map a) {
        super(a, 1);
    }

    public MovRight(Map a, Integer s) {
        super(a, s);
    }

    @Override
    public void execute() {
        a.moveSkane(a.getSkane().moveRight(s));
    }
}
