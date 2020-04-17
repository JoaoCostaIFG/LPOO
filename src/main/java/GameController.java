import creator.RoomCreator;
import room.Position;
import gui.Gui;
import gui.EVENT;
import room.Room;

import java.io.IOException;

public class GameController {
    private Room room;
    private Gui gui;
    private GAMEST state;
    private SkaneController skaneController;
    private CollisionHandler colHandler;
    private final int DELAY = 25; // time between frames (in ms)

    public static void main(String[] args) throws IOException {
        new GameController().start();
    }

    private void handleEvent(EVENT event) {
        if (event == EVENT.NullEvent) return;
        else if (event == EVENT.QuitGame) this.state = GAMEST.STOPPPED;
        else if (event == EVENT.RestartGame) this.state = GAMEST.RESTART;
        else if (event == EVENT.Bury) skaneController.toggleBury();
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
        room = creator.createRoom(80, 40);
        skaneController = new SkaneController(room.getSkane(), 200);
        gui = new Gui(room);
        state = GAMEST.RUNNING;
        colHandler = new CollisionHandler(this);

        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        while (state == GAMEST.RUNNING) { // TODO make run method
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
