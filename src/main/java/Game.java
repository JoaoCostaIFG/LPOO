import Creator.ArenaCreator;
import arena.element.*;
import com.googlecode.lanterna.TerminalSize;
import gui.Gui;
import gui.Gui.Event;

import java.io.IOException;

import static arena.element.Room.*;

public class Game {
    enum State {
        RUNNING,
        STOPPPED,
        RESTART
    }

    private Room room;
    private Gui gui;
    private State state;
    private CollisionHandler colHandler;

    private final int DELAY = 25; // time between frames (in ms)

    public Room getRoom() {
        return room;
    }

    public static void main(String[] args) throws IOException {
        new Game().start();
    }

    private void handleEvent(Event event) {
        if (event == Event.NullEvent)
            return;
        else if (event == Event.QuitGame)
            this.state = State.STOPPPED;
        else if (event == Event.RestartGame)
            this.state = State.RESTART;
        else { // Movement Event
            Position new_pos = room.getSkane().getPos();
            switch (event) {
                case MoveLeft:
                    new_pos = room.getSkane().moveLeft();
                    break;
                case MoveRight:
                    new_pos = room.getSkane().moveRight();
                    break;
                case MoveUp:
                    new_pos = room.getSkane().moveUp();
                    break;
                case MoveDown:
                    new_pos = room.getSkane().moveDown();
                    break;
            }
            if (colHandler.canSkaneMove(new_pos))
                room.moveSkane(new_pos);
        }
    }

    private void start() throws IOException {
        ArenaCreator creator = new ArenaCreator();
        room = creator.createArena(new TerminalSize(80, 40));
        gui = new Gui(room);
        state = State.RUNNING;
        colHandler = new CollisionHandler(this);

        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        while (state == State.RUNNING) {
            handleEvent(gui.getEvent());
            gui.releaseKeys();
            gui.draw();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;
            if (sleep < 0)
                sleep = 0;

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: %s" + e.getMessage());
            }
            beforeTime = System.currentTimeMillis();
        }

        gui.close();
    }
}
