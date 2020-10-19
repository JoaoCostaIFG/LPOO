package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.model.Position;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class MapReader {
    private BufferedReader br;
    private Integer length;
    private Integer height;
    private Position skanePos;
    private List<Position> walls;
    private List<Position> civilians;
    private List<Position> meleeEnem;
    private List<Position> rangedEnem;
    private List<Position> civSpawners;
    private List<Position> melSpawners;
    private List<Position> ranSpawners;

    public MapReader() {
        this.length = 0;
        this.height = 0;
        this.walls = new ArrayList<>();
        this.civilians = new ArrayList<>();
        this.meleeEnem = new ArrayList<>();
        this.rangedEnem = new ArrayList<>();
        this.civSpawners = new ArrayList<>();
        this.melSpawners = new ArrayList<>();
        this.ranSpawners = new ArrayList<>();
    }

    public MapReader(String mapName) throws IOException {
        this();
        InputStream is = getClass().getClassLoader().getResourceAsStream(mapName);
        if (is == null)
            throw new FileNotFoundException();
        br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        this.generateMap();
    }

    public MapReader(BufferedReader br) throws IOException {
        this();
        this.br = br;
        this.generateMap();
    }

    public void generateMap() throws IOException {
        String line = br.readLine();
        if (line == null)
            throw new InputMismatchException();

        int height = 0;
        int length = line.length();

        do {
            if (line.length() != length)
                throw new InputMismatchException();
            for (int i = 0; i < line.length(); ++i) {
                handleChar(line.charAt(i), i, height);
            }
            line = br.readLine();
            ++height;
        } while (line != null);

        this.height = height;
        this.length = length;
    }

    private void handleChar(Character c, Integer length, Integer height) {
        Position p = new Position(length, height);
        switch (c) {
            case 'c':
                civilians.add(p);
                break;
            case 'C':
                civSpawners.add(p);
                break;
            case 'm':
                meleeEnem.add(p);
                break;
            case 'M':
                melSpawners.add(p);
                break;
            case 'r':
                rangedEnem.add(p);
                break;
            case 'R':
                ranSpawners.add(p);
                break;
            case 'S':
                if (skanePos != null)
                    throw new InputMismatchException();
                skanePos = p;
                break;
            case 'W':
                walls.add(p);
                break;
            default:
                break;
        }
    }

    public List<Position> getWalls() {
        return walls;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getHeight() {
        return height;
    }

    public List<Position> getCivilians() {
        return civilians;
    }

    public List<Position> getMeleeEnem() {
        return meleeEnem;
    }

    public List<Position> getRangedEnem() {
        return rangedEnem;
    }

    public Position getSkanePos() {
        return skanePos;
    }

    public List<Position> getCivSpawners() {
        return civSpawners;
    }

    public List<Position> getMelSpawners() {
        return melSpawners;
    }

    public List<Position> getRanSpawners() {
        return ranSpawners;
    }
}
