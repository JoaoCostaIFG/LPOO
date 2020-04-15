package commands;

import arena.Map;

public class MovUp extends MovCommand {
    public MovUp(Map m) {
        super(m, 1);
    }

    public MovUp(Map m, Integer s) {
        super(m, s);
    }

    @Override
    public void execute() {
        m.moveSkane(m.getSkane().moveUp(s));
    }
}
