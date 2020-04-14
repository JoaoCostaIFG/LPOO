package commands;

import arena.Arena;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class QuitCommand extends Command {
    private final Arena a;
    private final Screen s;

    public QuitCommand(Arena a, Screen s) {
        this.a = a;
        this.s = s;
    }

    @Override
    public void execute() {
        a.finishArena();
        try {
            s.close();
        } catch (IOException e) { // TODO
            e.printStackTrace();
        }
    }
}
