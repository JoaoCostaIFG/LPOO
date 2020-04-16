import Creator.RoomCreator;
import room.Position;
import com.googlecode.lanterna.TerminalSize;
import gui.Gui;
import gui.Event;
import room.Room;

import java.io.IOException;

public class GameController {
    private Room room;
    private Gui gui;
    private GameState state;
    private SkaneController skaneController;
    private CollisionHandler colHandler;
    private final int DELAY = 25; // time between frames (in ms)

    public static void main(String[] args) throws IOException {
        new GameController().start();
    }

    private void handleEvent(Event event) {
        if (event == Event.NullEvent) return;
        else if (event == Event.QuitGame) this.state = GameState.STOPPPED;
        else if (event == Event.RestartGame) this.state = GameState.RESTART;
        else if (event == Event.Bury) skaneController.toggleBury();
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
        RoomCreator creator = new RoomCreator();
        room = creator.createRoom(new TerminalSize(80, 40));
        skaneController = new SkaneController(room.getSkane(), 200);
        gui = new Gui(room);
        state = GameState.RUNNING;
        colHandler = new CollisionHandler(this);

        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        while (state == GameState.RUNNING) { // TODO make run method
            handleEvent(gui.getEvent());
            skaneController.inhale();
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

    public Room getRoom() {
        return room;
    }
}
