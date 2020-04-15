package commands;

import arena.Map;

public class MovRight extends MovCommand {
    public MovRight(Map m) {
        super(m, 1);
    }

    public MovRight(Map m, Integer s) {
        super(m, s);
    }

    @Override
    public void execute() {
        m.moveSkane(m.getSkane().moveRight(s));
    }
}
