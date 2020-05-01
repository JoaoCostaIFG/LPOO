package view;

import model.Room;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Gui {
    private Room room;
    private Screen screen;
    private TerminalResizeHandler resize_handler;
    private EVENT event;
    private Thread input_handler;

    private final int DFLT_WIDTH = 80; // default board width
    private final int DFLT_HEIGHT = 40; // default board height

    public Gui(Room map) throws IOException {
        TerminalSize init_size = new TerminalSize(DFLT_WIDTH, DFLT_HEIGHT);
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(init_size).createTerminal();
        resize_handler = new TerminalResizeHandler(init_size);
        terminal.addResizeListener(resize_handler);

        this.screen = new TerminalScreen(terminal);
        screen.doResizeIfNecessary();
        screen.setCursorPosition(null); // we don't need a cursor
        screen.startScreen();

        this.room = map;

        this.event = EVENT.NullEvent;
    }

    public void stopInputHandler() {
        // TODO dunno if it works
        input_handler.interrupt();
    }

    public void startInputHandler() {
        input_handler = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        processKey(screen.readInput());
                        // processKey(screen.pollInput());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        input_handler.setDaemon(true);
        input_handler.start();
    }

    public void releaseKeys() {
        this.event = EVENT.NullEvent;
    }

    private void processKey(KeyStroke key) {
        if (key == null)
            return;

        if (key.getKeyType() == KeyType.Character) {
            switch (key.getCharacter()) {
                case 'a':
                case 'A':
                    this.event = EVENT.MoveLeft;
                    break;
                case 'd':
                case 'D':
                    this.event = EVENT.MoveRight;
                    break;
                case 'w':
                case 'W':
                    this.event = EVENT.MoveUp;
                    break;
                case 's':
                case 'S':
                    this.event = EVENT.MoveDown;
                    break;
                case 'r':
                case 'R':
                    this.event = EVENT.RestartGame;
                    break;
                case 'q':
                case 'Q':
                    this.event = EVENT.QuitGame;
                    break;
                case ' ':
                    this.event = EVENT.Bury;
                default:
                    break;
            }
        }

        switch (key.getKeyType()) {
            case ArrowLeft:
                this.event = EVENT.MoveLeft;
                break;
            case ArrowRight:
                this.event = EVENT.MoveRight;
                break;
            case ArrowUp:
                this.event = EVENT.MoveUp;
                break;
            case ArrowDown:
                this.event = EVENT.MoveDown;
                break;
            case Escape:
            case EOF:
                this.event = EVENT.QuitGame;
                break;
            default:
                break;
        }
    }

    public void setRoom(Room r) {
        this.room = r;
    }

    public EVENT getEvent() {
        return this.event;
    }

    public TerminalSize getTermSize() {
        return this.resize_handler.getLastKnownSize();
    }

    public void close() throws IOException {
        this.screen.close();
    }

    public void draw() throws IOException {
        if (resize_handler.hasResized()) {
            TerminalSize newsize = resize_handler.getLastKnownSize();
            room.setSize(newsize.getColumns(), newsize.getRows());
            screen.doResizeIfNecessary();
        }

        screen.clear();
        screen.refresh();
    }
}
