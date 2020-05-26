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
    private KeyHandler keyHandler;

    private EVENT event = EVENT.NullEvent;
    private InputHandler inputHandler = null;

    public Gui(Room room) throws IOException {
        TerminalSize init_size = new TerminalSize(room.getWidth(), room.getHeight());
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(init_size).createTerminal();
        resizeHandler = new TerminalResizeHandler(init_size);
        terminal.addResizeListener(resizeHandler);

        this.screen = new TerminalScreen(terminal);
        setUpScreen(this.screen);

        this.room = room;
        this.drawer = new Drawer(screen.newTextGraphics());
        this.keyHandler = new KeyHandler();
    }

    public Gui(Room room, Screen newScreen, TerminalResizeHandler resizeHandler) throws IOException {
        this(room, newScreen, resizeHandler, new Drawer(newScreen.newTextGraphics()), new KeyHandler());
    }

    public Gui(Room room, Screen newScreen, TerminalResizeHandler resizeHandler, RoomDrawer drawer, KeyHandler kh) throws IOException {
        setUpScreen(newScreen);
        this.screen = newScreen;
        this.resizeHandler = resizeHandler;

        this.room = room;
        this.drawer = drawer;
        this.keyHandler = kh;
    }

    public void setUpScreen(Screen screen) throws IOException {
        screen.doResizeIfNecessary();
        screen.setCursorPosition(null); // we don't need a cursor
        screen.startScreen();
    }

    public InputHandler getInputHandler() {
        return this.inputHandler;
    }

    public void stopInputHandler() throws NullPointerException {
        inputHandler.stop();
    }

    public void startInputHandler() {
        startInputHandler(new InputHandler(this.screen));
    }

    public void startInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        inputHandler.setDaemon(true);
        inputHandler.start();
    }

    public void releaseKeys() {
        this.event = EVENT.NullEvent;
    }

    public EVENT getEvent() throws NullPointerException {
        KeyStroke ks;
        ks = inputHandler.getLastKey();
        this.event = this.keyHandler.processKey(ks);

        return this.event;
    }

    public void setRoom(Room r) {
        this.room = r;
    }

    public Room getRoom() {
        return this.room;
    }

    public TerminalSize getTermSize() {
        return this.resizeHandler.getLastKnownSize();
    }

    public void close() throws IOException {
        this.screen.close();
    }

    public void draw() throws IOException {
        TerminalSize newsize = resizeHandler.getLastKnownSize();
        if (resizeHandler.hasResized() || 
                newsize.getColumns() != room.getWidth() || newsize.getRows() != room.getHeight()) {
            room.setSize(newsize.getColumns(), newsize.getRows());
            screen.doResizeIfNecessary();
        }

        screen.clear();
        drawer.draw(room);
        screen.refresh();
    }
}
