package commands;

import Creator.ArenaCreator;
import arena.Arena;
import com.googlecode.lanterna.screen.Screen;
import gui.Gui;

public class RestartCommand extends Command {
    private Arena a;
    private Gui g;
    private final Screen scrn;

    public RestartCommand(Arena a, Gui g, Screen scrn) {
        this.a = a;
        this.g = g;
        this.scrn = scrn;
    }

    @Override
    public void execute() {
        ArenaCreator creator = new ArenaCreator();
        Arena new_arena = creator.createArena(scrn.getTerminalSize());
        new_arena.addObservers(this.a.getObservers());

        this.g.setArena(new_arena);
    }
}
