package main.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TurretShotTest {

    private TurretShot testTurret;
    private int oldX;
    private int newX;
    private int oldY;
    private int newY;

    @Test
    void moveShot() {
        testTurret = new TurretShot(100, 100, 4, "Right");
        oldX = testTurret.getX();
        testTurret.moveShot();
        newX = testTurret.getX();
        assertTrue(newX > oldX, "If shot is moving right, newX " + newX + " should be greater than oldX " + oldX);

        testTurret = new TurretShot(100, 100, 4, "Left");
        oldX = testTurret.getX();
        testTurret.moveShot();
        newX = testTurret.getX();
        assertTrue(newX < oldX, "If shot is moving left, newX " + newX + " should be less than oldX " + oldX);

        testTurret = new TurretShot(100, 100, 4, "Up");
        oldY = testTurret.getY();
        testTurret.moveShot();
        newY = testTurret.getY();
        assertTrue(newY < oldY, "If shot is moving up, newY " + newY + " should be less than oldY " + oldY);
    }

}