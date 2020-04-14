package commands;

import arena.Arena;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class QuitCommand extends Command {
    private final Arena a;
    private final Screen scrn;

    public QuitCommand(Arena a, Screen scrn) {
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
