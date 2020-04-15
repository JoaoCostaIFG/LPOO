package commands;

import arena.Map;

public class MovLeft extends MovCommand {
    public MovLeft(Map m) {
        super(m, 1);
    }

    public MovLeft(Map m, Integer s) {
        super(m, s);
    }

    @Override
    public void execute() {
        m.moveSkane(m.getSkane().moveLeft(s));
    }
}
