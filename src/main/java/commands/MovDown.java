package commands;

import arena.Map;

public class MovDown extends MovCommand {
    public MovDown(Map a) {
        super(a, 1);
    }

    public MovDown(Map a, Integer s) {
        super(a, s);
    }

    @Override
    public void execute() {
        a.moveSkane(a.getSkane().moveDown(s));
    }
}
