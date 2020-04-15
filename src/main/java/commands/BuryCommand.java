package commands;

import arena.Arena;

public class BuryCommand extends Command {
    private final Arena a;

    public BuryCommand(Arena a) {
        this.a = a;
    }

    @Override
    public void execute() {
        a.skaneBury(!a.isSkaneBury());
    }
}
