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
    private Room room;
    private Screen screen;
    private GraphicsDrawer drawer;
    private TerminalResizeHandler resize_handler;
    private EVENT event;

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

        this.event = EVENT.NullEvent;
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

    public void setRoom(Room a) {
        this.room = a;
    }

    public EVENT getEvent() {
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
        this.drawer.drawRoom(room);
        screen.refresh();
    }
}
