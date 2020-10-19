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

    public boolean isDaemon() {
        return isDaemon;
    }

    public void setDaemon(boolean state) {
        isDaemon = state;
    }

    public boolean isAlive() {
        return t != null && t.isAlive();
    }

    public Thread getThread() {
        return t;
    }

    public void run() {
        KeyStroke newKeyStroke;
        while (!halt) {
            try {
                synchronized (this) {
                    newKeyStroke = screen.readInput();
                }
                if (newKeyStroke != null)
                    lastKey = newKeyStroke;
            } catch (IOException | RuntimeException e) {
                halt = true;
            }
        }
    }

    public void start() {
        start(new Thread(this));
    }

    public void start(Thread newT) {
        t = newT;
        t.setDaemon(isDaemon);
        t.start();
    }

    public void stop() {
        halt = true;
    }
}
