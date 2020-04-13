import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {
    private Screen screen;
    private Arena arena;
    private TerminalResizeHandler resize_handler;
    private final int DELAY = 17; // time between frames (in ms)

    enum GameState {
        END,
        LOSE,
        NORMAL,
        RESTART,
        WIN
    }


    public Game() {
        try {
            /* 300x100 should be big enough for fullscreen 1080p */
            TerminalSize init_size = new TerminalSize(300, 100);
            Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(init_size).createTerminal();
            resize_handler = new TerminalResizeHandler(init_size);
            terminal.addResizeListener(resize_handler);

            this.screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null); // we don't need a cursor
            screen.startScreen();           // screens must be started
            screen.doResizeIfNecessary();   // resize screen if necessary
        } catch (IOException e) {
            e.printStackTrace();
        }

        arena = new Arena(new TerminalSize(300, 100));
    }

    private void processKey(KeyStroke key) {
        arena.processKey(key);
    }

    private void drawEndScreen() {
        if (arena.getGameState() == GameState.LOSE) {   // lost
            TextGraphics gra = screen.newTextGraphics();
            gra.putString(new TerminalPosition(60, 10), "YOU DIED");
        } else if (arena.getGameState() == GameState.WIN) {   // won
            TextGraphics gra = screen.newTextGraphics();
            gra.putString(new TerminalPosition(60, 10), "YOU WON");
        }
    }

    private void draw() throws IOException {
        screen.clear();
        arena.draw(screen.newTextGraphics());
        this.drawEndScreen(); // end screen
        screen.refresh();
    }

    private void new_frame() {
        try {
            this.draw();
            this.processKey(screen.readInput());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();

        while (arena.getGameState() != GameState.END) {
            if (arena.getGameState() == GameState.RESTART) {
                this.arena = new Arena(new TerminalSize(300, 100));
            }
            new_frame();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;
            if (sleep < 0)
                sleep = 2;

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
