package org.g73.skanedweller.controller.creator;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.fail;

public class MapReaderTest {
    @Test
    public void walls() {
        MapReader mr = new MapReader("firstmap");
        try {
            mr.generateMap();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

        System.out.println(mr.getWalls().size());
    }
}
