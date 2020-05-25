package org.g73.skanedweller.controller;

import org.g73.skanedweller.controller.creator.MapReader;
import org.g73.skanedweller.controller.creator.RoomCreator;
import org.g73.skanedweller.controller.creator.spawners_creator.*;
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
    private MapReader mapReader;
    private static final String map_name = "firstmap";
    private static final int DELAY = 30; // time between frames (in ms)

    public static void main(String[] args) throws IOException {
        new GameController().start();
    }

    public GameController(Room room, Gui gui, MapReader mr, SkaneController skaCtr) {
        this.room = room;
        this.gui = gui;
        this.state = GAMEST.RUNNING;
        this.controllers = new ArrayList<>();
        controllers.add(new EnemyController());
        controllers.add(skaCtr);
        this.playerController = skaCtr;
        this.mapReader = mr;

        this.spawners = createSpawners();
        for (Spawner s: spawners)
            room.addObserver(s);
    }

    public GameController(Room room, MapReader mr) throws IOException {
        this(room, new Gui(room), mr,
                new SkaneController(room.getSkane(), 50));
    }
    
    public GameController(MapReader mr) throws IOException {
        this(new RoomCreator().createRoom(mr), mr);
    }

    public GameController() throws IOException {
        this(new MapReader(map_name));
    }

    private List<Spawner> createSpawners() {
        List<Spawner> spawners = new ArrayList<>();
        SpawnerCreator civSpCreator = new CivSpawnerCreator();
        for (Position p: this.mapReader.getCivSpawners())
            spawners.add(civSpCreator.create(p));
        
        SpawnerCreator meleeSpCreator = new MeleeSpawnerCreator();
        for (Position p: this.mapReader.getMelSpawners())
            spawners.add(meleeSpCreator.create(p));
        
        SpawnerCreator rangedSpCreator = new RangedSpawnerCreator();
        for (Position p: this.mapReader.getRanSpawners())
            spawners.add(rangedSpCreator.create(p));
        
        return spawners;
    }

    public List<Spawner> getSpawners() {
        return this.spawners;
    }

    public void setSpawners(List<Spawner> spawners) {
        this.spawners = spawners;
    }

    private void handleEvent(EVENT event) {
        if (event == EVENT.NullEvent) return;
        else if (event == EVENT.QuitGame) this.end();
        else if (event == EVENT.RestartGame) this.state = GAMEST.RESTART;
        else playerController.setEvent(event); // Player Event
    }

    @Override
    public void update(Room room) {
        handleEvent(gui.getEvent());
        gui.releaseKeys();

        // TODO
        if (!room.getSkane().isAlive())
            this.state = GAMEST.RESTART;
        System.out.println(room.getSkane().getHp());

        for (Controller c : this.controllers)
            c.update(room);

        for (Spawner s: this.spawners)
            s.update(room);
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
                // System.out.println("Thread interrupted: %s" + e.getMessage());
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
        this.room = new RoomCreator().createRoom(mapReader);

        this.gui.stopInputHandler();
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
