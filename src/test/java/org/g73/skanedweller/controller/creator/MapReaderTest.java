package org.g73.skanedweller.controller.creator;

import org.g73.skanedweller.model.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.*;

public class MapReaderTest {
    MapReader mr;

    @Test
    public void testReadFile() throws IOException {
        mr = new MapReader("firstmap");
        assertNotEquals(0, (int) mr.getLength());
        assertNotEquals(0, (int) mr.getHeight());
    }

    @Test
    public void testNoFile() throws IOException {
        Assertions.assertThrows(FileNotFoundException.class, () -> {
                    mr = new MapReader("notfoundmap");
                }
        );
    }

    @Test
    public void testSize() throws IOException {
        Assertions.assertThrows(InputMismatchException.class, () -> {
                    String imp = "-------\n-------\n------\n";
                    BufferedReader reader = new BufferedReader(new StringReader(imp));
                    mr = new MapReader(reader);
                }
        );
    }

    @Test
    public void testEmptyFile() throws IOException {
        Assertions.assertThrows(InputMismatchException.class, () -> {
            String imp = "";
            BufferedReader reader = new BufferedReader(new StringReader(imp));
            mr = new MapReader(reader);
        });
    }

    @Test
    public void testEmptyMap() throws IOException {
        String imp = "-------\n" +
                "--S----\n" +
                "-------\n";
        BufferedReader reader = new BufferedReader(new StringReader(imp));
        mr = new MapReader(reader);
        assertEquals(0, mr.getWalls().size());
        assertEquals(0, mr.getRanSpawners().size());
        assertEquals(0, mr.getMelSpawners().size());
        assertEquals(0, mr.getCivSpawners().size());
        assertEquals(0, mr.getMeleeEnem().size());
        assertEquals(0, mr.getCivilians().size());
        assertEquals(0, mr.getRangedEnem().size());
        assertEquals(3, (int) mr.getHeight());
        assertEquals(7, (int) mr.getLength());
        assertEquals(new Position(2, 1), mr.getSkanePos());
    }

    @Test
    public void testTwoSkanes() throws IOException {
        Assertions.assertThrows(InputMismatchException.class, () -> {
            String imp = "----S--\n" +
                    "--S----\n" +
                    "-------\n";
            BufferedReader reader = new BufferedReader(new StringReader(imp));
            mr = new MapReader(reader);
        });
    }

    @Test
    public void testFullMap() throws IOException {
        String imp = "R-W-c-r\n" +
                "-----C-\n" +
                "W--m--M\n" +
                "------W\n";
        BufferedReader reader = new BufferedReader(new StringReader(imp));
        mr = new MapReader(reader);
        assertEquals(3, mr.getWalls().size());
        assertEquals(1, mr.getRanSpawners().size());
        assertEquals(1, mr.getMelSpawners().size());
        assertEquals(1, mr.getCivSpawners().size());
        assertEquals(1, mr.getMeleeEnem().size());
        assertEquals(1, mr.getCivilians().size());
        assertEquals(1, mr.getRangedEnem().size());
        assertEquals(4, (int) mr.getHeight());
        assertEquals(7, (int) mr.getLength());
        assertNull(mr.getSkanePos());

        assertEquals(new Position(2, 0), mr.getWalls().get(0));
        assertEquals(new Position(0, 2), mr.getWalls().get(1));
        assertEquals(new Position(6, 3), mr.getWalls().get(2));
        assertEquals(new Position(0, 0), mr.getRanSpawners().get(0));
        assertEquals(new Position(6, 2), mr.getMelSpawners().get(0));
        assertEquals(new Position(5, 1), mr.getCivSpawners().get(0));
        assertEquals(new Position(4, 0), mr.getCivilians().get(0));
        assertEquals(new Position(3, 2), mr.getMeleeEnem().get(0));
        assertEquals(new Position(6, 0), mr.getRangedEnem().get(0));
    }
}
