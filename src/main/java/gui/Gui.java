package gui;

import arena.Map;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import commands.*;

import java.io.IOException;

public class Gui {
    private Map map;
    private Screen screen;
    private GraphicsDrawer drawer;
    private TerminalResizeHandler resize_handler;
    private Command cmd;

    private final int DFLT_WIDTH = 80; // default board width
    private final int DFLT_HEIGHT = 40; // default board height

    public Gui(Map arena) throws IOException {
        TerminalSize init_size = new TerminalSize(DFLT_WIDTH, DFLT_HEIGHT);
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(init_size).createTerminal();
        resize_handler = new TerminalResizeHandler(init_size);
        terminal.addResizeListener(resize_handler);

        this.screen = new TerminalScreen(terminal);
        screen.doResizeIfNecessary();
        screen.setCursorPosition(null); // we don't need a cursor
        screen.startScreen();

        this.map = arena;
        this.drawer = new Drawer(screen.newTextGraphics());

        this.cmd = new NullCommand();
        startInputHandler();
    }

    private void startInputHandler() {
        Thread input_handler = new Thread(() -> {
            while (true) {
                try {
                    processKey(screen.readInput());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        input_handler.setDaemon(true);
        input_handler.start();
    }


    public void releaseKeys() {
        this.cmd = new NullCommand();
    }

    private void processKey(KeyStroke key) throws IOException {
        if (key.getKeyType() == KeyType.Character) {
            switch (key.getCharacter()) {
                case 'a':
                case 'A':
                    this.cmd = new MovLeft(this.map);
                    break;
                case 'd':
                case 'D':
                    this.cmd = new MovRight(this.map);
                    break;
                case 'w':
                case 'W':
                    this.cmd = new MovUp(this.map);
                    break;
                case 's':
                case 'S':
                    this.cmd = new MovDown(this.map);
                    break;
                case 'r':
                case 'R':
                    this.cmd = new RestartCommand(map, this, screen);
                    break;
                case ' ':
                    this.cmd = new BuryCommand(this.map);
                default:
                    break;
            }
        }

        switch (key.getKeyType()) {
            case ArrowLeft:
                this.cmd = new MovLeft(map);
                break;
            case ArrowRight:
                this.cmd = new MovRight(map);
                break;
            case ArrowUp:
                this.cmd = new MovUp(map);
                break;
            case ArrowDown:
                this.cmd = new MovDown(map);
                break;
            case Escape:
            case EOF:
                this.cmd = new QuitCommand(map, screen);
                break;
            default:
                break;
        }
    }

    public void setMap(Map a) {
        this.map = a;
    }

    public Command getCmd() {
        return this.cmd;
    }

    public void draw() throws IOException {
        if (resize_handler.hasResized()) {
            map.setSize(resize_handler.getLastKnownSize());
            screen.doResizeIfNecessary();
        }

        screen.clear();
        this.drawer.drawMap(map);
        screen.refresh();
    }
}
