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

    public GameController(Room room, Gui gui, SkaneController skactr) {
        this.room = room;
        this.gui = gui;
        this.state = GAMEST.RUNNING;
        this.colHandler = new CollisionHandler(this.room);
        this.skaneController = skactr;
    }

    public GameController(Room room) throws IOException {
        this(room, new Gui(room),
                new SkaneController(room.getSkane(), 200));
    }

    public GameController() throws IOException {
        this(new RoomCreator().createRoom(80, 40));
    }

    public void start() throws IOException {
        this.state = GAMEST.RUNNING;
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        gui.startInputHandler();
        while (state == GAMEST.RUNNING) { // TODO make run method
            handleEvent(gui.getEvent());
            skaneController.inhale(); // TODO pass move cmd
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

    public void end() {
        this.state = GAMEST.STOPPPED;
    }

    public Room getRoom() {
        return room;
    }
}
