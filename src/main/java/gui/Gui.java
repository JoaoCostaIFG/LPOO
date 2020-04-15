package gui;

import room.Room;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Gui {
    public enum Event {
        Bury,
        MoveLeft,
        MoveRight,
        MoveUp,
        MoveDown,
        MoveStop,
        NullEvent,
        QuitGame,
        RestartGame
    };

    private Room room;
    private Screen screen;
    private GraphicsDrawer drawer;
    private TerminalResizeHandler resize_handler;
    private Event event;

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
        this.drawer = new Drawer(screen.newTextGraphics());

        this.event = Event.NullEvent;
        startInputHandler();
    }

    private void startInputHandler() {
        Thread input_handler = new Thread(() -> {
            while (true) {
                try {
                    processKey(screen.readInput());
                    // processKey(screen.pollInput());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        input_handler.setDaemon(true);
        input_handler.start();
    }


    public void releaseKeys() {
        this.event = Event.NullEvent;
    }

    private void processKey(KeyStroke key) {
        if (key == null)
            return;

        if (key.getKeyType() == KeyType.Character) {
            switch (key.getCharacter()) {
                case 'a':
                case 'A':
                    this.event = Event.MoveLeft;
                    break;
                case 'd':
                case 'D':
                    this.event = Event.MoveRight;
                    break;
                case 'w':
                case 'W':
                    this.event = Event.MoveUp;
                    break;
                case 's':
                case 'S':
                    this.event = Event.MoveDown;
                    break;
                case 'r':
                case 'R':
                    this.event = Event.RestartGame;
                    break;
                case 'q':
                case 'Q':
                    this.event = Event.QuitGame;
                    break;
                case ' ':
                    this.event = Event.Bury;
                default:
                    break;
            }
        }

        switch (key.getKeyType()) {
            case ArrowLeft:
                this.event = Event.MoveLeft;
                break;
            case ArrowRight:
                this.event = Event.MoveRight;
                break;
            case ArrowUp:
                this.event = Event.MoveUp;
                break;
            case ArrowDown:
                this.event = Event.MoveDown;
                break;
            case Escape:
            case EOF:
                this.event = Event.QuitGame;
                break;
            default:
                break;
        }
    }

    public void setRoom(Room a) {
        this.room = a;
    }

    public Event getEvent() {
        return this.event;
    }

    public void close() throws IOException {
        this.screen.close();
    }

    public void draw() throws IOException {
        if (resize_handler.hasResized()) {
            room.setSize(resize_handler.getLastKnownSize());
            screen.doResizeIfNecessary();
        }

        screen.clear();
        this.drawer.drawMap(room);
        screen.refresh();
    }
}
