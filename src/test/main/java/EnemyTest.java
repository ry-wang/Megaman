package main.java;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnemyTest {

    static Enemy testEnemy;

    @BeforeAll
    static void initAll() {
        testEnemy = new Enemy(1);
    }

    @Test
    void testMoveLeft() {
        //Testing that x pos is greater than before
        testEnemy.setState("Walk Right");
        int oldX = testEnemy.getX();
        testEnemy.move();
        int newX = testEnemy.getX();
        assertTrue(newX > oldX, "New X (" + newX + ") should be greater than old X (" + oldX + ")");
    }

    @Test
    void testMoveRight() {
        testEnemy.setState("Walk Left");
        int oldX = testEnemy.getX();
        testEnemy.move();
        int newX = testEnemy.getX();
        assertTrue(newX < oldX, "New X (" + newX + ") should be less than old X (" + oldX + ")");
    }

    @Test
    void testImageReset() {
        testEnemy.setImageNum(8);
        testEnemy.updateEnemyState();
        assertEquals(1, testEnemy.getImageNum());
    }

    @Test
    void testEnemyDestroy() {
        testEnemy.setDestroyed();
        testEnemy.setImageNum(10);
        testEnemy.updateEnemyState();
        assertTrue(testEnemy.deathAnimationComplete());
    }

    @Test
    void testSpawnLocation() {
        List<Integer> validYLocations = Arrays.asList(300, 334, 160);
        assertTrue(validYLocations.contains(testEnemy.getY()));

        testEnemy = new Enemy(2);
        validYLocations = Arrays.asList(300, 334, 235);
        assertTrue(validYLocations.contains(testEnemy.getY()));

        testEnemy = new Enemy(3);
        validYLocations = Arrays.asList(300, 334, 160);
        assertTrue(validYLocations.contains(testEnemy.getY()));
    }

}