package commands;

import arena.Map;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class QuitCommand extends Command {
    private final Map a;
    private final Screen scrn;

    public QuitCommand(Map a, Screen scrn) {
        this.a = a;
        this.scrn = scrn;
    }

    @Override
    public void execute() {
        a.finishArena();
        try {
            scrn.close();
        } catch (IOException e) { // TODO
            e.printStackTrace();
        }
    }
}
