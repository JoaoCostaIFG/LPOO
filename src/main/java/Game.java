import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.w3c.dom.Text;

import java.io.IOException;

public class Game {
    private Screen screen;
    private Arena arena;

    public Game() {
        try {
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            this.screen = new TerminalScreen(terminal);

            screen.setCursorPosition(null); // we don't need a cursor
            screen.startScreen();           // screens must be started
            screen.doResizeIfNecessary();   // resize screen if necessary
        } catch (IOException e) {
            e.printStackTrace();
        }

        arena = new Arena(20, 60);
    }

    private void processKey(KeyStroke key) {
        arena.processKey(key);
    }

    private void drawEndScreen() {
        if (arena.getGameState() == 2) {   // lost
            TextGraphics gra = screen.newTextGraphics();
            gra.putString(new TerminalPosition(60, 10), "YOU DIED");
        } else if (arena.getGameState() == 3) {   // won
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

    public void run() {
        while (arena.getGameState() != 0) {
            if (arena.getGameState() == 4) {
                this.arena = new Arena(20, 60);
            }
            try {
                this.draw();
                this.processKey(screen.readInput());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            screen.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
