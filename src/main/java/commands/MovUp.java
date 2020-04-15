package commands;

import arena.Map;

public class MovUp extends MovCommand {
    public MovUp(Map a) {
        super(a, 1);
    }

    public MovUp(Map a, Integer s) {
        super(a, s);
    }

    @Override
    public void execute() {
        a.moveSkane(a.getSkane().moveUp(s));
    }
}
