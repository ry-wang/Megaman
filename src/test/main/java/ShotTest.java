package main.java;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ShotTest {

    private Shot testShot;
    private int oldX;
    private int newX;

    @Test
    void testMoveRight() {
        testShot = new Shot(100, 100, 4, "Right");
        oldX = testShot.getX();
        testShot.moveShot();
        newX = testShot.getX();
        assertTrue(newX > oldX, "If shot is moving right, newX " + newX + " should be greater than oldX " + oldX);
    }

    @Test
    void testMoveLeft() {
        testShot = new Shot(100, 100, 4, "Left");
        oldX = testShot.getX();
        testShot.moveShot();
        newX = testShot.getX();
        assertTrue(newX < oldX, "If shot is moving left, newX " + newX + " should be less than oldX " + oldX);
    }

}