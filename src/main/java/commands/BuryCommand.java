package commands;

import arena.Map;

public class BuryCommand extends Command {
    private final Map m;

    public BuryCommand(Map m) {
        this.m = m;
    }

    @Override
    public void execute() {
        m.skaneBury(!m.isSkaneBury());
    }
}
