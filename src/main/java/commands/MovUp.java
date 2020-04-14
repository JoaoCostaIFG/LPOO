package commands;

import arena.Arena;
import arena.element.MovableElement;
import arena.Position;

public class MovUp extends MovCommand {
    public MovUp(Arena a) {
        super(a, 1);
    }

    public MovUp(Arena a, Integer s) {
        super(a, s);
    }

    @Override
    public void execute() {
        a.moveSkane(a.getSkane().moveUp(s));
    }
}
