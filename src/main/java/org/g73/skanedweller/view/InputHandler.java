package org.g73.skanedweller.view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class InputHandler implements Runnable {
    private Thread t = null;
    private Screen screen;
    private boolean isDaemon = false;
    private volatile KeyStroke lastKey = null;
    private volatile boolean halt = false;

    InputHandler(Screen screen) {
        this.screen = screen;
    }

    public KeyStroke getLastKey() {
        KeyStroke ret = this.lastKey;
        this.lastKey = null;
        return ret;
    }

    public void setDaemon(boolean state) {
        isDaemon = state;
    }

    public boolean isDaemon() {
        return isDaemon;
    }

    public void run() {
        while (!halt) {
            try {
                synchronized (this) {
                    lastKey = screen.readInput();
                }
            } catch (IOException | RuntimeException e) {
                halt = true;
            }
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.setDaemon(isDaemon);
            t.start();
        }
    }

    public void stop() {
        halt = true;
        // TODO null t
    }
}
