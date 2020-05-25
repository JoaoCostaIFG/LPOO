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
    
    public MapReader(String map_name) throws IOException {
        this();
        InputStream is = getClass().getClassLoader().getResourceAsStream(map_name);
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

        int height=0, length;

        String line = br.readLine();
        length = line.length();
        do {
            if (line.length() != length)
                throw new InputMismatchException();
            for (int i=0; i<line.length(); ++i) {
                handleChar(line.charAt(i), i, height);
            }
            line = br.readLine();
            ++height;
        } while (line != null);

        if (height == 0 || length == 0)
            throw new InputMismatchException();
        
        this.height = height;
        this.length = length;
    }

    private void handleChar(Character c, Integer length, Integer height) {
        Position p = new Position(length, height);
        if (c == 'W')
            walls.add(p);
        else if (c == 'r')
            rangedEnem.add(p);
        else if (c == 'm')
            meleeEnem.add(p);
        else if (c == 'c')
            civilians.add(p);
        else if (c == 'S') {
            if (skanePos == null)
                skanePos = p;
            else
                throw new InputMismatchException();
        }
        else if (c == 'C')
            civSpawners.add(p);
        else if (c == 'M')
            melSpawners.add(p);
        else if (c == 'R')
            ranSpawners.add(p);
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
