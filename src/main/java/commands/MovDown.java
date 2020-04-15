package commands;

import arena.Map;

public class MovDown extends MovCommand {
    public MovDown(Map m) {
        super(m, 1);
    }

    public MovDown(Map m, Integer s) {
        super(m, s);
    }

    @Override
    public void execute() {
        m.moveSkane(m.getSkane().moveDown(s));
    }
}
