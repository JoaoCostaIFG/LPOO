import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {
    private Screen screen;
    private Arena arena;
    private TerminalResizeHandler resize_handler;
    /* 300x100 should be enough to 1080p fullscreen */
    private final int DFLT_WIDTH = 80; // default board width
    private final int DFLT_HEIGHT = 40; // default board height
    private final int DELAY = 25; // time between frames (in ms)

    enum GameState {
        END,
        LOSE,
        NORMAL,
        RESTART,
        WIN
    }


    public Game() {
        try {
            TerminalSize init_size = new TerminalSize(DFLT_WIDTH, DFLT_HEIGHT);
            Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(init_size).createTerminal();
            resize_handler = new TerminalResizeHandler(init_size);
            terminal.addResizeListener(resize_handler);

            this.screen = new TerminalScreen(terminal);
            screen.doResizeIfNecessary();
            screen.setCursorPosition(null); // we don't need a cursor
            screen.startScreen();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        arena = new Arena(resize_handler.getLastKnownSize());
    }

    private void draw() throws IOException {
        screen.clear();
        arena.draw(screen.newTextGraphics());
        screen.refresh();
    }

    private void new_frame() {
        if (arena.getGameState() == GameState.RESTART)
            this.arena = new Arena(resize_handler.getLastKnownSize());

        if (resize_handler.hasResized()) {
            screen.doResizeIfNecessary();
            this.arena.resizeBoard(resize_handler.getLastKnownSize());
        }

        arena.update();
        try {
            this.draw();
            // this.arena.processKey(screen.readInput());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Thread input_handler = new Thread(() -> {
            while (true) {
                try {
                    arena.processKey(screen.readInput());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        input_handler.setDaemon(true);
        input_handler.start();

        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        while (arena.getGameState() != GameState.END) {
            new_frame();

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

        // close game
        try {
            screen.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
