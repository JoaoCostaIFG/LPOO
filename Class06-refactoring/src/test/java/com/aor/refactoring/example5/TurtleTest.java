package com.aor.refactoring.example5;

import org.junit.Test;

import static org.junit.Assert.*;

public class TurtleTest {

    @Test
    public void testRotateLeft() {
        Turtle turtle = new Turtle(new Position(5, 5, 'N'));
        turtle.execute(new RotateLeft()); assertEquals('W', turtle.getDirection());
        turtle.execute(new RotateLeft()); assertEquals('S', turtle.getDirection());
        turtle.execute(new RotateLeft()); assertEquals('E', turtle.getDirection());
        turtle.execute(new RotateLeft()); assertEquals('N', turtle.getDirection());
        assertEquals(5, turtle.getRow());
        assertEquals(5, turtle.getColumn());
    }

    @Test
    public void testRotateRight() {
        Turtle turtle = new Turtle(new Position(5, 5, 'N'));
        turtle.execute(new RotateRight()); assertEquals('E', turtle.getDirection());
        turtle.execute(new RotateRight()); assertEquals('S', turtle.getDirection());
        turtle.execute(new RotateRight()); assertEquals('W', turtle.getDirection());
        turtle.execute(new RotateRight()); assertEquals('N', turtle.getDirection());
        assertEquals(5, turtle.getRow());
        assertEquals(5, turtle.getColumn());
    }

    @Test
    public void testForward() {
        Turtle turtleN = new Turtle(new Position(5, 5, 'N'));

        turtleN.execute(new MoveForward());
        assertEquals(4, turtleN.getRow());
        assertEquals(5, turtleN.getColumn());

        Turtle turtleW = new Turtle(new Position(5, 5, 'W'));

        turtleW.execute(new MoveForward());
        assertEquals(5, turtleW.getRow());
        assertEquals(4, turtleW.getColumn());

        Turtle turtleS = new Turtle(new Position(5, 5, 'S'));

        turtleS.execute(new MoveForward());
        assertEquals(6, turtleS.getRow());
        assertEquals(5, turtleS.getColumn());

        Turtle turtleE = new Turtle(new Position(5, 5, 'E'));

        turtleE.execute(new MoveForward());
        assertEquals(5, turtleE.getRow());
        assertEquals(6, turtleE.getColumn());
    }

}