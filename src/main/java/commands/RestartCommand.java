package commands;

import Creator.ArenaCreator;
import arena.Map;
import com.googlecode.lanterna.screen.Screen;
import gui.Gui;

public class RestartCommand extends Command {
    private Map m;
    private Gui g;
    private final Screen scrn;

    public RestartCommand(Map m, Gui g, Screen scrn) {
        this.m = m;
        this.g = g;
        this.scrn = scrn;
    }

    @Override
    public void execute() {
        ArenaCreator creator = new ArenaCreator();
        Map new_arena = creator.createArena(scrn.getTerminalSize());
        new_arena.addObservers(this.m.getObservers());

        this.g.setMap(new_arena);
    }
}
