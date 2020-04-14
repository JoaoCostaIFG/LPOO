package gui;

import arena.Arena;
import arena.element.DrawableElement;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import commands.*;

import java.io.IOException;

public class Gui {
    private Arena arena;
    private Screen screen;
    private TerminalResizeHandler resize_handler;
    private Command cmd;

    private final int DFLT_WIDTH = 80; // default board width
    private final int DFLT_HEIGHT = 40; // default board height

    public Gui(Arena arena) throws IOException {
        TerminalSize init_size = new TerminalSize(DFLT_WIDTH, DFLT_HEIGHT);
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(init_size).createTerminal();
        resize_handler = new TerminalResizeHandler(init_size);
        terminal.addResizeListener(resize_handler);

        this.screen = new TerminalScreen(terminal);
        screen.doResizeIfNecessary();
        screen.setCursorPosition(null); // we don't need a cursor
        screen.startScreen();

        this.arena = arena;

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

    private void drawArena(TextGraphics gra) {
        gra.setBackgroundColor(TextColor.Factory.fromString("#313742"));
        gra.fillRectangle(new TerminalPosition(0, 0),
                new TerminalSize(arena.getWidth(), arena.getHeight()), ' ');
    }

    public void draw() throws IOException {
        if (resize_handler.hasResized()) {
            arena.setSize(resize_handler.getLastKnownSize());
            screen.doResizeIfNecessary();
        }

        screen.clear();
        TextGraphics gra = screen.newTextGraphics();

        drawArena(gra);
        for (DrawableElement d : arena.getAllDrawable())
            d.draw(gra);

        screen.refresh();
    }

    public void releaseKeys() {
        this.cmd = new NullCommand();
    }

    private void processKey(KeyStroke key) throws IOException {
        if (key.getKeyType() == KeyType.Character) {
            switch (key.getCharacter()) {
                case 'a':
                case 'A':
                    this.cmd = new MovLeft(this.arena);
                    break;
                case 'd':
                case 'D':
                    this.cmd = new MovRight(this.arena);
                    break;
                case 'w':
                case 'W':
                    this.cmd = new MovUp(this.arena);
                    break;
                case 's':
                case 'S':
                    this.cmd = new MovDown(this.arena);
                    break;
                case 'r':
                case 'R':
                    this.cmd = new RestartCommand(arena, this, screen);
                    break;
                default:
                    break;
            }
        }

        switch (key.getKeyType()) {
            case ArrowLeft:
                this.cmd = new MovLeft(arena);
                break;
            case ArrowRight:
                this.cmd = new MovRight(arena);
                break;
            case ArrowUp:
                this.cmd = new MovUp(arena);
                break;
            case ArrowDown:
                this.cmd = new MovDown(arena);
                break;
            case Escape:
            case EOF:
                this.cmd = new QuitCommand(arena, screen);
                break;
            default:
                break;
        }
    }

    public void setArena(Arena a) {
        this.arena = a;
    }

    public Command getCmd() {
        return this.cmd;
    }
}
