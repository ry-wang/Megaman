package main.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ShotTest {

    @Test
    void moveShot() {
        Shot testShot;
        int oldX;
        int newX;

        testShot = new Shot(100, 100, 4, "Right");
        oldX = testShot.getX();
        testShot.moveShot();
        newX = testShot.getX();
        assertTrue(newX > oldX, "If shot is moving right, newX " + newX + " should be greater than oldX " + oldX);

        testShot = new Shot(100, 100, 4, "Left");
        oldX = testShot.getX();
        testShot.moveShot();
        newX = testShot.getX();
        assertTrue(newX < oldX, "If shot is moving left, newX " + newX + " should be less than oldX " + oldX);
    }

}