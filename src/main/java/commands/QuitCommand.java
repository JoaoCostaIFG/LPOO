package commands;

import arena.Map;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class QuitCommand extends Command {
    private final Map m;
    private final Screen scrn;

    public QuitCommand(Map m, Screen scrn) {
        this.m = m;
        this.scrn = scrn;
    }

    @Override
    public void execute() {
        m.finishArena();
        try {
            scrn.close();
        } catch (IOException e) { // TODO
            e.printStackTrace();
        }
    }
}
