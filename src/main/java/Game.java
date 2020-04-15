import Creator.ArenaCreator;
import arena.Map;
import arena.ArenaObserver;
import com.googlecode.lanterna.TerminalSize;
import gui.Gui;

import java.io.IOException;

public class Game implements ArenaObserver {
    private Map arena;
    private Gui gui;
    private final int DELAY = 25; // time between frames (in ms)

    public static void main(String[] args) throws IOException {
        new Game().start();
    }

    private void start() throws IOException {
        ArenaCreator creator = new ArenaCreator();
        arena = creator.createArena(new TerminalSize(80, 40));
        arena.addObserver(this);

        gui = new Gui(arena);

        // TODO fix game not being abkle to end after restart (arena reference
        //  in this method is not updated). Should get rid of arena.done and finish
        //  to use notifications instead.

        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        while (!arena.isArenaFinished()) {
            gui.getCmd().execute();
            arena.skaneBreath();
            gui.releaseKeys();
            gui.draw();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;
            if (sleep < 0)
                sleep = 0;

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: %s" + e.getMessage());
            }
            beforeTime = System.currentTimeMillis();
        }
    }

    @Override
    public void arenaStateChange() {
        try {
            gui.draw();
        } catch (IOException e) { // TODO failed drawing?
            e.printStackTrace();
        }
    }
}
