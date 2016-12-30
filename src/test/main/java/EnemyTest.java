package main.java;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyTest {

    static Enemy testEnemy;

    @BeforeAll
    static void initAll() {
        testEnemy = new Enemy(1);
    }

    @Test
    void moveEnemy() {
        //Testing that x pos is greater than before
        if (testEnemy.getState().equalsIgnoreCase("Walk Right")) {
            int oldX = testEnemy.getX();
            testEnemy.moveEnemy();
            int newX = testEnemy.getX();
            assertTrue(newX > oldX, "New X (" + newX + ") should be greater than old X (" + oldX + ")");
        }
        else {
            int oldX = testEnemy.getX();
            testEnemy.moveEnemy();
            int newX = testEnemy.getX();
            assertTrue(newX < oldX, "New X (" + newX + ") should be less than old X (" + oldX + ")");
        }
    }

    @Test
    void updateEnemyState() {
        testEnemy.setImageNum(8);
        testEnemy.updateEnemyState();
        assertEquals(1, testEnemy.getImageNum());

        testEnemy.setDestroyed();
        testEnemy.setImageNum(10);
        testEnemy.updateEnemyState();
        assertTrue(testEnemy.getDeathAnimationState());
    }

}