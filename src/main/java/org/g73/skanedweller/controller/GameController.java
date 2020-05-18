package org.g73.skanedweller.controller;

import com.googlecode.lanterna.TerminalSize;
import org.g73.skanedweller.controller.creator.MeleeCreator;
import org.g73.skanedweller.controller.creator.RoomCreator;
import org.g73.skanedweller.model.Position;
import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.view.EVENT;
import org.g73.skanedweller.view.Gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController implements Controller {
    private Room room;
    private Gui gui;
    private GAMEST state;
    private List<Controller> controllers;
    private PlayerController playerController;
    private List<Spawner> spawners;
    private final int DELAY = 30; // time between frames (in ms)

    public static void main(String[] args) throws IOException {
        new GameController().start();
    }

    private void handleEvent(EVENT event) {
        if (event == EVENT.NullEvent) return;
        else if (event == EVENT.QuitGame) this.end();
        else if (event == EVENT.RestartGame) this.state = GAMEST.RESTART;
        else playerController.setEvent(event); // Player Event
    }

    public GameController(Room room, Gui gui, SkaneController skaCtr) {
        this.room = room;
        this.gui = gui;
        this.state = GAMEST.RUNNING;
        this.controllers = new ArrayList<Controller>();
        controllers.add(new EnemyController());
        controllers.add(skaCtr);
        this.playerController = skaCtr;
        this.spawners = createSpawners();

        for (Spawner s: spawners)
            room.addObserver(s);
    }

    public GameController(Room room) throws IOException {
        this(room, new Gui(room),
                new SkaneController(room.getSkane(), 50));
    }

    public GameController() throws IOException {
        this(new RoomCreator().createRoom(80, 40));
    }

    private static List<Spawner> createSpawners() {
        List<Spawner> spawners = new ArrayList<>();
        final Integer maxMelee = 3;
        final Integer meleeDelay = 30 * 10; // 10 seconds
        Spawner meleeSpawner =
                new Spawner(maxMelee, meleeDelay, new MeleeCreator(), new Position(3, 3));

        spawners.add(meleeSpawner);
        return spawners;
    }

    @Override
    public void update(Room room) {
        handleEvent(gui.getEvent());
        gui.releaseKeys();

        for (Controller c : this.controllers)
            c.update(room);

        for (Spawner s: this.spawners)
            s.update(room);
        // TODO
        // cleanup dead enemies
        //for (Element e : room.getEnemies()) {
        // TODO spawn dead body (if n papado)
        //}
    }

    private void run() throws IOException {
        long beforeTime, timeDiff;
        beforeTime = System.currentTimeMillis();

        gui.startInputHandler();
        while (state == GAMEST.RUNNING) {
            gui.draw();
            update(room);

            timeDiff = System.currentTimeMillis() - beforeTime;
            try {
                if (DELAY - timeDiff > 0)
                    Thread.sleep(DELAY - timeDiff);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: %s" + e.getMessage());
            }
            beforeTime = System.currentTimeMillis();
        }
    }

    public void start() throws IOException {
        this.state = GAMEST.RUNNING;

        while (this.state != GAMEST.STOPPED) {
            this.run();
            if (this.state == GAMEST.RESTART)
                this.restart();
        }

        gui.close();
    }

    public void restart() {
        this.state = GAMEST.RUNNING;
        TerminalSize ts = gui.getTermSize();
        this.room = new RoomCreator().createRoom(ts.getColumns(), ts.getRows());

        //this.gui.stopInputHandler();
        this.gui.setRoom(this.room);
        controllers.remove(playerController);
        this.playerController = new SkaneController(room.getSkane(), 200);
        controllers.add(playerController);
        spawners = createSpawners();
    }

    public void end() {
        this.state = GAMEST.STOPPED;
    }

    public Room getRoom() {
        return room;
    }
}
