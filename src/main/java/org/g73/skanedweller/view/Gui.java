package org.g73.skanedweller.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.g73.skanedweller.model.Room;

import java.io.IOException;

public class Gui {
    private Room room;
    private Screen screen;
    private TerminalResizeHandler resizeHandler;
    private RoomDrawer drawer;

    private volatile EVENT event;
    private Thread inputHandler;

    private final int DFLT_WIDTH = 80; // default board width
    private final int DFLT_HEIGHT = 40; // default board height

    public Gui(Room room, Screen newScreen, TerminalResizeHandler resizeHandler) throws IOException {
        setUpScreen(newScreen);
        this.screen = newScreen;
        this.resizeHandler = resizeHandler;

        this.room = room;
        this.drawer = new Drawer(screen.newTextGraphics());
        this.event = EVENT.NullEvent;
    }

    public Gui(Room room) throws IOException {
        TerminalSize init_size = new TerminalSize(DFLT_WIDTH, DFLT_HEIGHT);
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(init_size).createTerminal();
        resizeHandler = new TerminalResizeHandler(init_size);
        terminal.addResizeListener(resizeHandler);

        this.screen = new TerminalScreen(terminal);
        setUpScreen(this.screen);

        this.room = room;
        this.drawer = new Drawer(screen.newTextGraphics());
        this.event = EVENT.NullEvent;
    }

    public void setUpScreen(Screen screen) throws IOException {
        screen.doResizeIfNecessary();
        screen.setCursorPosition(null); // we don't need a cursor
        screen.startScreen();
    }

    public Gui(Room room, Screen screen, TerminalResizeHandler resizeHandler, RoomDrawer drawer) {
        this.room = room;
        this.screen = screen;
        this.resizeHandler = resizeHandler;
        this.drawer = drawer;
        this.event = EVENT.NullEvent;
    }

    public void stopInputHandler() {
        inputHandler.interrupt();
    }

    public void startInputHandler() {
        inputHandler = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        synchronized (this) {
                            processKey(screen.readInput());
                        }
                    } catch (IOException | RuntimeException e) {
                        break;
                    }
                }
            }
        };

        startInputHandler(inputHandler);
    }

    public void startInputHandler(Thread inputHandler) {
        this.inputHandler = inputHandler;
        inputHandler.setDaemon(true);
        inputHandler.start();
    }

    public void releaseKeys() {
        synchronized (this.inputHandler) {
            this.event = EVENT.NullEvent;
        }
    }

    private void processKey(KeyStroke key) {
        if (key == null)
            return;

        EVENT newEvent = EVENT.NullEvent;

        if (key.getKeyType() == KeyType.Character) {
            switch (key.getCharacter()) {
                case 'a':
                case 'A':
                    newEvent = EVENT.MoveLeft;
                    break;
                case 'd':
                case 'D':
                    newEvent = EVENT.MoveRight;
                    break;
                case 'w':
                case 'W':
                    newEvent = EVENT.MoveUp;
                    break;
                case 's':
                case 'S':
                    newEvent = EVENT.MoveDown;
                    break;
                case 'r':
                case 'R':
                    newEvent = EVENT.RestartGame;
                    break;
                case 'q':
                case 'Q':
                    newEvent = EVENT.QuitGame;
                    break;
                case ' ':
                    newEvent = EVENT.Bury;
                default:
                    break;
            }
        }

        switch (key.getKeyType()) {
            case ArrowLeft:
                newEvent = EVENT.MoveLeft;
                break;
            case ArrowRight:
                newEvent = EVENT.MoveRight;
                break;
            case ArrowUp:
                newEvent = EVENT.MoveUp;
                break;
            case ArrowDown:
                newEvent = EVENT.MoveDown;
                break;
            case Escape:
            case EOF:
                newEvent = EVENT.QuitGame;
                break;
            default:
                break;
        }

        synchronized (this.inputHandler) {
            if (newEvent != EVENT.NullEvent)
                this.event = newEvent;
        }
    }

    public EVENT getEvent() {
        synchronized (this.inputHandler) {
            return this.event;
        }
    }

    public void setRoom(Room r) {
        this.room = r;
    }

    public TerminalSize getTermSize() {
        return this.resizeHandler.getLastKnownSize();
    }

    public void close() throws IOException {
        this.screen.close();
    }

    public void draw() throws IOException {
        if (resizeHandler.hasResized()) {
            TerminalSize newsize = resizeHandler.getLastKnownSize();
            room.setSize(newsize.getColumns(), newsize.getRows());
            screen.doResizeIfNecessary();
        }

        screen.clear();
        drawer.draw(room);
        screen.refresh();
    }
}
