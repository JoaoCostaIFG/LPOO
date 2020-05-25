package org.g73.skanedweller.controller.creator;

import org.junit.Before;

import java.io.IOException;

import static org.junit.Assert.fail;

public class MapReaderTest {
    MapReader mr;
    
    @Before
    public void setUp() {
        try {
            mr = new MapReader("firstmap");
        } catch (IOException e) {
//            e.printStackTrace();
            fail();
        }
    }
}
